package com.sva.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sva.common.ConvertUtil;
import com.sva.common.MyLog;
import com.sva.common.conf.GlobalConf;
import com.sva.common.conf.Params;
import com.sva.common.email.SimpleMail;
import com.sva.common.email.SimpleMailSender;
import com.sva.dao.AmqpDao;
import com.sva.dao.CommonDao;
import com.sva.dao.SvaDao;
import com.sva.model.SvaModel;
import com.sva.service.core.HttpsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * @ClassName: ValidateSVAService 
 * @Description: sva订阅(全量)验证与纠错服务类 
 * @author gl 
 * @date 2017年1月25日 下午3:35:09 
 *  
 */
@Service("validateSVAService")
public class ValidateSVAService extends HttpsService {

    /**
     * @Fields log ： 输出日志
     */
    private static final Logger LOG = Logger.getLogger(SubscriptionService.class);

    private static final MyLog mylog = MyLog.getInstance();

    private static final String FLAG1 = "1";

    /**
     * @Fields amqpDao : amqp对接入库dao
     */
    @Autowired
    private AmqpDao amqpDao;

    @Autowired
    private CommonDao dao;

    /**
     * @Fields svaDao : SvaDao接口类
     */
    @Autowired
    private SvaDao svaDao;

    /**
     * @Fields svaSSLVersion : SVA使用的SSL版本
     */
    @Value("${sva.sslVersion}")
    private String svaSSLVersion;

    /**
     * @Fields hasIdType : 向sva订阅时是否要加idType
     */
    @Value("${sva.hasIdType}")
    private String hasIdType;

    /**
     * @Fields secondBefore : 查询location提前秒数
     */
    @Value("${sva.validSecondBefore}")
    private int secondBefore;

    /** 
     * @Title: validateActiveSVA 
     * @Description: 验证启用的所有sva  
     * @author gl
     */
 public void validateActiveSVA() {
        List<SvaModel> activeSvaList = svaDao.getEnabled();
        for (SvaModel activeSva : activeSvaList) {
            this.validateSVA(Integer.parseInt(activeSva.getId()));
//            Map<String, Object> retMap = this.validateSVA(Integer.parseInt(activeSva.getId()));
//            boolean isSendEmail = false;
//            SimpleMail simpleMail = new SimpleMail();
//            String emailTitle = "";
//            if(!(Boolean)retMap.get("status")){
//                // 未修复成功
//                emailTitle = "sva获取location数据失败";
//                isSendEmail = true;
//            }else{
//                // 发送邮件（修复成功）
//                if(retMap.get("isRepaired")!= null && (Boolean)retMap.get("isRepaired")){
//                    // 修复成功
//                    emailTitle = "sva获取location数据修复正常";
//                    isSendEmail = true;
//                }
//            }
//            String emailContent = (String) retMap.get("msg");
//            simpleMail.setSubject(emailTitle);
//            simpleMail.setContent(emailContent);
//            simpleMail.setToList(Arrays.asList(activeSva.getManagerEmail()));
//            SimpleMailSender simpleMailSender = new SimpleMailSender(simpleMail);
//            try {
//                if(isSendEmail){
//                    simpleMailSender.send(simpleMail);                    
//                    LOG.info("--sva验证--:邮件发送成功（自动），收件人" + simpleMail.getToList().get(0));
//                }
//            } catch (AddressException e) {
//                LOG.error("邮件发送失败", e);
//            } catch (MessagingException e) {
//                LOG.error("邮件发送失败", e);
//            }
        }
    }

    /** 
     * @Title: validateSVA 
     * @Description: 验证指定的sva
     * @param svaid
     * @return 格式为：{svaid: svaid, status: boleanVal, statusCode: statusCod, msg:
     *         msgInfo, isRepaired: booleanVal}，status=true：正常（或者修复后正常），status=false：非正常，msg：验证结果说明，isRepaired：是否
     *         修复过
     * @author gl
     */
    public Map<String, Object> validateSVA(int svaid) {
        Map<String, Object> result = new HashMap<>();
        result.put("svaid", svaid);
        result.put("isRepaired", false); // 初始化为未修复过
        String msg = "";
        SvaModel sva = svaDao.getSvaById(svaid);
        if (sva == null) {
            result.put("status", false);
            msg = "--sav验证--：svaid=" + svaid + "的sva不存在";
            result.put("msg", msg);
            LOG.error(msg);
            return result;
        } else {
            LOG.info("--sva验证--：secondBefore = " + secondBefore);
            boolean hasData = hasData(svaid, secondBefore); 
            if (!hasData) {
                /**
                 * 无location数据
                 */
                msg = "svaid=" + svaid + "的sva未产生location数据";
                LOG.debug(msg);
                /**
                 * 尝试修复 流程见《sva接口变更修改设计书》的“增加sva自动纠错机制”流程
                 */
                // 获取token
                String token = getToken(sva);
                // token获取失败
                if (token == null) {
                    result.put("status", false);
                    result.put("statusCode", Params.SVA_GET_TOKEN_FAILED);
                    msg = "--sav验证--：svaid=" + svaid + "的sva获取token失败";
                    result.put("msg", msg);
                    LOG.error(msg);
                    // 修改sva状态码
                    svaDao.udpateSvaStatusCode(String.valueOf(svaid), Params.SVA_GET_TOKEN_FAILED);
                    return result;
                } else {
                    // token获取成功
                    sva.setToken(token);
                    // 发起取消订阅(取消订阅没有响应内容，暂时通过此方法判断，注该方法为SubscriptionService的unsubscribe，只是通过添加返回值判断是否取消订阅成功)
                    boolean isUnSubSuccess = this.unsubscribe(sva);
                    // 取消订阅成功
                    if (isUnSubSuccess) {
                        // 尝试重新发起订阅请求
                        Map<String, Object> reSubRetMap = subscribeSva(sva);
                        boolean reSubSuccess = (boolean) reSubRetMap.get("status");
                        // 重新订阅成功
                        if (reSubSuccess) {
                            // 等待数据上报
                            try {
                                Thread.sleep(Params.SECOND_WAIT_FOR_DATA * 1000);
                            } catch (InterruptedException e) {
                                LOG.error("--sva验证--：等待数据上报被打断", e);
                                result.put("status", false);
                                msg = "等待数据上报时发生InterruptedException";
                                result.put("msg", msg);
                                return result;
                            }
                            // 检查数据上报是否成功
                            boolean reHasDataSucess = hasData(svaid, secondBefore);
                            // 重新订阅后，数据上报成功
                            if (reHasDataSucess) {
                                result.put("status", true);
                                result.put("isRepaired", true);
                                result.put("statusCode", Params.SVA_VALID_SUCESS);
                                msg = "location数据订阅异常，已修复成功，svaid = " + sva.getId();
                                LOG.info(msg);
                                result.put("msg", msg);
                                // 修改sva状态码
                                svaDao.udpateSvaStatusCode(String.valueOf(svaid), Params.SVA_VALID_SUCESS);
                                return result;
                            } else {
                                // 重新订阅后，数据上报失败
                                result.put("status", false);
                                result.put("statusCode", Params.SUBSCRIB_DATA_GENERATE_FAILED);
                                msg = "--sva验证--：重新订阅后，数据上报失败" + sva.getId();
                                LOG.info(msg);
                                result.put("msg", msg);
                                // 修改sva状态码
                                svaDao.udpateSvaStatusCode(String.valueOf(svaid), Params.SUBSCRIB_DATA_GENERATE_FAILED);
                                return result;
                            }
                        } else {
                            // 重新订阅失败
                            result.put("status", false);
                            result.put("statusCode", Params.SVA_SUBSCRIB_FAILED);
                            msg = (String) reSubRetMap.get("msg");
                            result.put("msg", msg);
                            // 修改sva状态码
                            svaDao.udpateSvaStatusCode(String.valueOf(svaid), Params.SVA_SUBSCRIB_FAILED);
                            return result;
                        }
                    } else {
                        // 取消订阅失败
                        result.put("status", false);
                        result.put("statusCode", Params.SVA_UNSUBSCRIB_FAILED);
                        msg = "--sva验证--：取消订阅方法抛出异常，svaid=" + sva.getId();
                        LOG.error(msg);
                        result.put("msg", msg);
                        // 修改sva状态码
                        svaDao.udpateSvaStatusCode(String.valueOf(svaid), Params.SVA_UNSUBSCRIB_FAILED);
                        return result;
                    }
                }
            } else {
                /**
                 * 有location数据，数据接收正常
                 */
                result.put("status", true);
                
                result.put("statusCode", Params.SVA_VALID_SUCESS);
                msg = "svaid=" + svaid + "的sva正常接收location数据";
                result.put("msg", msg);
                LOG.info(msg);
                // 修改sva状态码
                svaDao.udpateSvaStatusCode(String.valueOf(svaid), Params.SVA_VALID_SUCESS);
                return result;
            }
        }
    }

    /** 
     * @Title: hasData 
     * @Description: 指定svaid从现在往前secondBefore秒到现在是否有数据记录
     * @param svaid
     * @param secondBefore 从现在往前secondBefore秒
     * @return true: 有数据（至少一条）, false: 无数据
     * @author gl
     */
    public boolean hasData(int svaid, int secondBefore) {
        // 今日表
        String tableName = Params.LOCATION + ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD);
        LOG.info("--sva验证--：今日表是" + tableName);
        // 当前时间戳
        long timeLocal = System.currentTimeMillis();
        long begin = timeLocal - (long) (secondBefore * 1000);
        int dataNum = dao.countLocationDataNum(tableName, begin, timeLocal, svaid);
        // 若location数据不存在
        if (dataNum <= 0) {
            LOG.error("--sav验证--：location数据未产生");
            return false;
        } else {
            return true;
        }
    }

    /** 
     * @Title: getToken 
     * @Description: 获取token
     * @param sva
     * @return 若返回null表示token获取失败
     * @author gl
     */
    private String getToken(SvaModel sva) {
        // 获取token地址ַ
        String url = "https://" + sva.getIp() + ":" + sva.getTokenPort() + "/v3/auth/tokens";
        // 获取token参数
        String content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                + sva.getUsername() 
                + "\",\"password\": \"" 
                + sva.getPassword() + "\"}}}}}";
        String charset = "UTF-8";
        // 获取token值ֵ
        Map<String, String> tokenResult = null;
        String token = null;
        try {
            tokenResult = this.httpsPost(url, content, charset, "POST", null, svaSSLVersion);
            token = tokenResult.get("token");
        } catch (KeyManagementException e) {
            LOG.error("KeyManagementException", e);
        } catch (NoSuchAlgorithmException e) {
            LOG.error("NoSuchAlgorithmException", e);
        } catch (IOException e) {
            LOG.error("IOException", e);
        }
        if (StringUtils.isEmpty(token)) {
            return null;
        } else {
            return token;
        }
    }

    /** 
     * @Title: getApiList 
     * @Description: 获取api列表(暂未使用) 
     * @param sva
     * @return {status: booleanVal, apiSet: apiset}，status=false表示获取api列表失败
     * @author gl
     */
    private Map<String, Object> getApiList(SvaModel sva) {
        Map<String, Object> retMap = new HashMap<>();
        Map<String, String> resultMap = null;

        String apiUrl = "https://" + sva.getIp() + ':' + sva.getTokenPort() + "/enabler/apilist/json/v1.0";
        String charset = "UTF-8";
        try {
            resultMap = this.httpsPost(apiUrl, null, charset, "GET", sva.getToken(), svaSSLVersion);
            JSONObject resultJson = JSONObject.fromObject(resultMap);
            String error_code = resultJson.getJSONObject("result").getString("error_code");
            if (!error_code.equals("0")) {
                retMap.put("status", false);
                return retMap;
            } else {
                retMap.put("status", true);
                JSONObject apiJson = resultJson.getJSONArray("API List").getJSONObject(0);
                @SuppressWarnings("unchecked")
                Iterator<String> apiKeyIter = apiJson.keys();
                Set<String> apiListSet = new HashSet<>();
                while (apiKeyIter.hasNext()) {
                    String key = apiKeyIter.next();
                    String value = apiJson.getString(key);
                    apiListSet.add(value);
                }
                retMap.put("apiSet", apiListSet);
            }
        } catch (KeyManagementException e) {
            LOG.error("KeyManagementException", e);
        } catch (NoSuchAlgorithmException e) {
            LOG.error("NoSuchAlgorithmException", e);
        } catch (IOException e) {
            LOG.error("IOException", e);
        }
        return retMap;
    }

    /** 
     * @Title: unsubscribe 
     * @Description: 取消订阅 @return: false失败，true成功 @throws 
     * @param sva
     * @return false取消失败，true取消成功
     * @author gl
     */
    private boolean unsubscribe(SvaModel sva) {
        LOG.debug("unsubcribe started!");
        String url = "";
        String content = "";

        try {
            // 是否需要在订阅参数中加idType
            String idTypeString = "";
            if (FLAG1.equals(hasIdType)) {
                idTypeString = ",\"idType\":\"" + sva.getIdType() + "\"";
            }
            String charset = "UTF-8";
            // 非匿名化取消订阅
            if (sva.getType() == 0) {
                url = "https://" + sva.getIp() + ":" + sva.getTokenPort()
                        + "/enabler/catalog/locationstreamunreg/json/v1.0";
                content = "{\"APPID\":\"" + sva.getUsername() + "\"" + idTypeString + "}";
                Map<String, String> subResult = this.httpsPost(url, content, charset, "DELETE", sva.getToken(),
                        svaSSLVersion);
                LOG.debug("--sav验证--: 取消订阅，方式：非匿名化，返回结果：" + subResult.get("result"));
                mylog.location("[unsubscribe]result:" + subResult.get("result"));
            } else if (sva.getType() == 1) {
                // 匿名化取消订阅
                url = "https://" + sva.getIp() + ":" + sva.getTokenPort()
                        + "/enabler/catalog/locationstreamanonymousunreg/json/v1.0";
                content = "{\"APPID\":\"" + sva.getUsername() + "\"}";
                Map<String, String> subResultAnonymous = this.httpsPost(url, content, charset, "DELETE", sva.getToken(),
                        svaSSLVersion);
                LOG.debug("--sav验证--: 取消订阅，方式：匿名化，返回结果：" + subResultAnonymous.get("result"));
                mylog.location("[unsubscribe]anonymous result:" + subResultAnonymous.get("result"));
            }

            // 关闭amqp连接
            GlobalConf.removeAmqpThread(sva.getId());
            LOG.debug("[unsubscribe]connection closed!");
            mylog.location("[unsubscribe]connection closed!");

        } catch (KeyManagementException e) {
            LOG.error("--sav验证--: 取消订阅异常", e);
            return false;
        } catch (NoSuchAlgorithmException e) {
            LOG.error("--sav验证--: 取消订阅异常", e);
            return false;
        } catch (IOException e) {
            LOG.error("--sav验证--: 取消订阅异常", e);
            return false;
        } catch (Exception e) {
            LOG.error("--sav验证--: 取消订阅异常", e);
            return false;
        }
        return true;
    }

    /** 
     * @Title: subscribeSva 
     * @Description: 实现sva数据订阅 
     * @param sva
     * @return {status: booleanVal, msg:
     *         msgAbstract}，status=true成功，status=false失败，msg失败描述,注：
     *         此方法为SubscriptionService的subscribeSva复制版本，区别是添加了状态返回数据
     * @author gl
     */
    private Map<String, Object> subscribeSva(SvaModel sva) {
        LOG.debug("subscripiton started:" + "svaId:" + sva.getId() + ",ip:" + sva.getIp() + ",port:"
                + sva.getTokenPort());
        mylog.location("subscripiton started:" + "svaId:" + sva.getId() + ",ip:" + sva.getIp() + ",port:"
                + sva.getTokenPort());

        Map<String, Object> retMap = new HashMap<>();

        // 获取token地址ַ
        String url = "https://" + sva.getIp() + ":" + sva.getTokenPort() + "/v3/auth/tokens";
        // 获取token参数
        String content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                + sva.getUsername() + "\",\"password\": \"" + sva.getPassword() + "\"}}}}}";
        String charset = "UTF-8";

        try {
            // 获取token值ֵ
            Map<String, String> tokenResult = this.httpsPost(url, content, charset, "POST", null, svaSSLVersion);
            String token = tokenResult.get("token");
            sva.setToken(token);

            if (StringUtils.isEmpty(token)) {
                LOG.warn("--sav验证--：重新发起订阅请求时获取token失败:svaId:" + sva.getId());
                retMap.put("status", false);
                retMap.put("msg", "--sav验证--：重新发起订阅请求时获取token失败:svaId:" + sva.getId());
                return retMap;
            }
            LOG.debug("token got:" + token);
            mylog.location("token got:" + token);

            // 是否需要在订阅参数中加idType
            String idTypeString = "";
            String isIpMac = "0a0a0a0a";
            if (("MAC").equals(sva.getIdType())) {
                isIpMac = "C01ADA2E2BC0";
            }
            if (FLAG1.equals(hasIdType)) {
                idTypeString = ",\"idType\":\"" + sva.getIdType() + "\"";
            }

            // 非匿名化全量订阅
            if (sva.getType() == 0) {
                url = "https://" + sva.getIp() + ":" + sva.getTokenPort()
                        + "/enabler/catalog/locationstreamreg/json/v1.0";
                content = "{\"APPID\":\"" + sva.getUsername() + "\"" + idTypeString + "}";
            }
            // 匿名化全量订阅
            else if (sva.getType() == 1) {
                url = "https://" + sva.getIp() + ":" + sva.getTokenPort()
                        + "/enabler/catalog/locationstreamanonymousreg/json/v1.0";
                content = "{\"APPID\":\"" + sva.getUsername() + "\"}";
            }
            // 指定用户订阅
            else if (sva.getType() == 2) {
                url = "https://" + sva.getIp() + ":" + sva.getTokenPort()
                        + "/enabler/catalog/locationstreamreg/json/v1.0";
                content = "{\"APPID\":\"" + sva.getUsername() + "\"" + idTypeString + ",\"useridlist\":[\""+isIpMac+"\"]}";
            }
            LOG.debug("subscription param:" + content);
            // 获取订阅ID
            Map<String, String> subResult = this.httpsPost(url, content, charset, "POST", tokenResult.get("token"),
                    svaSSLVersion);
            LOG.debug("subscription result:" + subResult.get("result"));
            mylog.location("subscription result:" + subResult.get("result"));
            JSONObject jsonObj = JSONObject.fromObject(subResult.get("result"));
            // 判断是否订阅成功,成功为0
            JSONObject svaResult = jsonObj.getJSONObject("result");
            int svaString = svaResult.getInt("error_code");
            if (0 == svaString) {
                JSONArray list = jsonObj.getJSONArray("Subscribe Information");
                JSONObject obj = (JSONObject) list.get(0);
                String queueId = obj.getString("QUEUE_ID");
                LOG.debug("queueId:" + queueId);
                mylog.location("queueId:" + queueId);

                // 如果获取queueId，则进入数据对接逻辑
                if (StringUtils.isNotEmpty(queueId)) {
                    AmqpThread at = new AmqpThread(sva, amqpDao, queueId);
                    GlobalConf.addAmqpThread(sva.getId(), at);
                    at.start();
                } else {
                    LOG.warn("queueId got failed:svaId:" + sva.getId());
                    mylog.location("queueId got failed:svaId:" + sva.getId());
                    retMap.put("status", false);
                    retMap.put("msg", "尝试重新订阅时，获取queueId失败，svaid = " + sva.getId());
                    return retMap;
                }
            } else {
                LOG.debug("--sva验证--尝试重新订阅时，订阅失败，svaid = " + sva.getId() + "\n" + "订阅返回的error_code：" + jsonObj);
                mylog.location("sva Subscription failed: " + jsonObj);
                retMap.put("status", false);
                retMap.put("msg", "--sva验证--尝试重新订阅时，订阅失败，svaid = " + sva.getId());
                return retMap;
            }
        } catch (IOException e) {
            LOG.error("IOException.", e);
            LOG.debug("--sva验证--尝试重新订阅时，发生异常，svaid = " + sva.getId(), e);
            return retMap;
        } catch (KeyManagementException e) {
            LOG.debug("--sva验证--尝试重新订阅时，发生异常，svaid = " + sva.getId(), e);
            retMap.put("status", false);
            retMap.put("msg", "--sva验证--尝试重新订阅时，发生异常，svaid = " + sva.getId());
            return retMap;
        } catch (NoSuchAlgorithmException e) {
            LOG.debug("--sva验证--尝试重新订阅时，发生异常，svaid = " + sva.getId(), e);
            retMap.put("status", false);
            retMap.put("msg", "--sva验证--尝试重新订阅时，发生异常，svaid = " + sva.getId());
            return retMap;
        }
        retMap.put("status", true);
        retMap.put("msg", "尝试重新订阅成功");
        return retMap;
    }
}
