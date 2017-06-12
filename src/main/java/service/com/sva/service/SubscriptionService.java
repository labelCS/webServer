/**   
 * @Title: SubscriptionService.java 
 * @Package com.sva.service 
 * @Description: 订阅服务 
 * @author labelCS   
 * @date 2016年8月18日 下午4:43:51 
 * @version V1.0   
 */
package com.sva.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sva.common.ConvertUtil;
import com.sva.common.MyLog;
import com.sva.common.conf.GlobalConf;
import com.sva.dao.AmqpDao;
import com.sva.model.SvaModel;
import com.sva.service.core.HttpsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * @ClassName: SubscriptionService 
 * @Description: 订阅服务
 * @author labelCS 
 * @date 2016年8月18日 下午4:43:51 
 *  
 */
@Service
public class SubscriptionService extends HttpsService {
    /**
     * @Fields log ： 输出日志
     */
    private static final Logger LOG = Logger.getLogger(SubscriptionService.class);
    
    private static final MyLog mylog = MyLog.getInstance();

    /**   
     * @Fields amqpDao : amqp对接入库dao   
     */ 
    @Autowired
    private AmqpDao amqpDao;

    /** 
     * @Fields hasIdType : 向sva订阅时是否要加idType
     */ 
    @Value("${sva.hasIdType}")
    private String hasIdType;
    
    /** 
     * @Fields svaSSLVersion : SVA使用的SSL版本
     */ 
    @Value("${sva.sslVersion}")
    private String svaSSLVersion;
    
    private static final String FLAG1 = "1";
    
    /** 
     * @Title: subscribeSvaInBatch 
     * @Description: 批量订阅sva  
     */
    public void subscribeSvaInBatch(){
        List<SvaModel> svaList = amqpDao.getActiveSva();
        for(SvaModel sm : svaList){
            subscribeSva(sm);
        }
    }
    
    /** 
     * @Title: subscribeSvaPhone 
     * @Description: 实现sva指定用户订阅
     * @param sva: sva信息
     * @param phoneIp: 手机ip
     */
    public void subscribeSvaPhone(SvaModel sva, String phoneIp){
//        mylog.location("subscribeSvaPhone started:"
//                + "svaId:" + sva.getId() 
//                + ",ip:" + sva.getIp() 
//                + ",port:" + sva.getTokenPort()
//                + ",phoneIp:" + phoneIp);
        LOG.debug("subscribeSvaPhone started:"
                + "svaId:" + sva.getId() 
                + ",ip:" + sva.getIp() 
                + ",port:" + sva.getTokenPort()
                + ",phoneIp:" + phoneIp);
        
        // 获取token地址
        String url = "https://" + sva.getIp() + ":"
                + sva.getTokenPort() + "/v3/auth/tokens";
        // 获取token参数
        String content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                + sva.getUsername()
                + "\",\"password\": \""
                + sva.getPassword() + "\"}}}}}";
        String charset = "UTF-8";
        
        try{
            // 获取token值
            Map<String,String> tokenResult = this.httpsPost(url, content, charset,"POST", null, svaSSLVersion);
            String token = tokenResult.get("token");
            sva.setToken(token);
            
            if(StringUtils.isEmpty(token)){
                LOG.warn("token got failed:svaId:" + sva.getId());
                mylog.location("token got failed:svaId:" + sva.getId());
                return;
            }
            LOG.debug("token got:"+token);
            mylog.location("token got:"+token);
            
            // 是否需要在订阅参数中加idType
            String idTypeString = "";
            if(FLAG1.equals(hasIdType)){
                idTypeString = ",\"idType\":\""+sva.getIdType()+"\"";
            }
            
            url = "https://" + sva.getIp() + ":" + sva.getTokenPort()
                    + "/enabler/catalog/locationstreamreg/json/v1.0";
            content = "{\"APPID\":\"" + sva.getUsername()
                    + "\"" + idTypeString
                    + ",\"useridlist\":[\""
                    + ConvertUtil.convertMacOrIp(phoneIp)
                    + "\"]}";
            LOG.debug("subscribeSvaPhone param:"+content);
            // 获取订阅ID
            Map<String,String> subResult = this.httpsPost(url, content, charset,"POST", tokenResult.get("token"),svaSSLVersion);
            LOG.debug("subscribeSvaPhone result:" + subResult.get("result"));
            mylog.location("subscribeSvaPhone result:" + subResult.get("result"));
            JSONObject jsonObj = JSONObject.fromObject(subResult.get("result"));
            //判断是否订阅成功,成功为0
            JSONObject svaResult =  jsonObj.getJSONObject("result");
            int svaString = svaResult.getInt("error_code");
            if (0==svaString) {
            JSONArray list = jsonObj.getJSONArray("Subscribe Information");
            JSONObject obj = (JSONObject) list.get(0);
            String queueId = obj.getString("QUEUE_ID");
            LOG.debug("queueId:" + queueId);
            mylog.location("queueId:" + queueId);
            }
            else{
                LOG.debug("sva Subscription failed: "+jsonObj);
                mylog.location("sva Subscription failed: "+jsonObj);
            }
            
        }
        catch (IOException e)
        {
            LOG.error("IOException.", e);
        }
        catch (KeyManagementException e)
        {
            LOG.error("KeyManagementException.", e);
        }
        catch (NoSuchAlgorithmException e)
        {
            LOG.error("NoSuchAlgorithmException.", e);
        }
    }
    
    
    public void unsubscribeSvaPhone(SvaModel sva, String phoneIp)
    {
        LOG.debug("unsubscribeSvaPhone started!");
        mylog.location("unsubscribeSvaPhone started!");
        String url = "";
        String content = "";

        try
        {
            // 获取token
            url = "https://" + sva.getIp() + ":"
                    + sva.getTokenPort() + "/v3/auth/tokens";
            content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                    + sva.getUsername()
                    + "\",\"password\": \""
                    + sva.getPassword() + "\"}}}}}";
            String charset = "UTF-8";
            Map<String,String> tokenResult = this.httpsPost(url, content, charset,"POST", null, svaSSLVersion);            
            String token = tokenResult.get("token");            
            if(StringUtils.isEmpty(token)){
                LOG.warn("[unsubscribeSvaPhone]token got failed:svaId:" + sva.getId());
                mylog.location("[unsubscribeSvaPhone]token got failed:svaId:" + sva.getId());
                return;
            }
            LOG.debug("[unsubscribeSvaPhone]token got:"+token);
            mylog.location("[unsubscribeSvaPhone]token got:"+token);

            // 是否需要在订阅参数中加idType
            String idTypeString = "";
            if(FLAG1.equals(hasIdType)){
                idTypeString = ",\"idType\":\""+sva.getIdType()+"\"";
            }
            
                url = "https://" + sva.getIp() + ":" + sva.getTokenPort()
                        + "/enabler/catalog/locationstreamunreg/json/v1.0";
                content = "{\"APPID\":\"" + sva.getUsername()
                + "\"" + idTypeString
                + ",\"useridlist\":[\""
                + ConvertUtil.convertMacOrIp(phoneIp)
                + "\"]}";
//                content = "{\"APPID\":\"" + sva.getUsername()+ "\""+idTypeString+"}";
                Map<String,String> subResult = this.httpsPost(url, content,charset, "DELETE", token, svaSSLVersion);
                LOG.debug("[unsubscribeSvaPhone]result:" + subResult.get("result"));
                mylog.location("[unsubscribeSvaPhone]result:" + subResult.get("result"));
            // 关闭amqp连接
            GlobalConf.removeAmqpThread(sva.getId());
            LOG.debug("[unsubscribeSvaPhone]connection closed!");
            mylog.location("[unsubscribeSvaPhone]connection closed!");

        }
        catch (KeyManagementException e)
        {
            LOG.error("KeyManagementException.", e);
        }
        catch (NoSuchAlgorithmException e)
        {
            LOG.error("NoSuchAlgorithmException.", e);
        }
        catch (IOException e)
        {
            LOG.error("IOException.", e);
        }
        catch (Exception e)
        {
            LOG.error("Exception.", e);
        }
    }
    
    /** 
     * @Title: subscribeSva 
     * @Description: 实现sva数据订阅
     * @param sva sva信息
     */
    public void subscribeSva(SvaModel sva){
        LOG.debug("subscripiton started:"
                + "svaId:" + sva.getId() 
                + ",ip:" + sva.getIp() 
                + ",port:" + sva.getTokenPort());
        mylog.location("subscripiton started:"
                + "svaId:" + sva.getId() 
                + ",ip:" + sva.getIp() 
                + ",port:" + sva.getTokenPort());
        
        // 获取token地址
        String url = "https://" + sva.getIp() + ":"
                + sva.getTokenPort() + "/v3/auth/tokens";
        // 获取token参数
        String content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                + sva.getUsername()
                + "\",\"password\": \""
                + sva.getPassword() + "\"}}}}}";
        String charset = "UTF-8";
        
        try{
            // 获取token值
            Map<String,String> tokenResult = this.httpsPost(url, content, charset,"POST", null, svaSSLVersion);
            String token = tokenResult.get("token");
            sva.setToken(token);
            
            if(StringUtils.isEmpty(token)){
                LOG.warn("token got failed:svaId:" + sva.getId());
                mylog.location("token got failed:svaId:" + sva.getId());
                return;
            }
            LOG.debug("token got:"+token);
            mylog.location("token got:"+token);
            
            // 是否需要在订阅参数中加idType
            String idTypeString = "";
            String isIpMac = "0a0a0a0a";
            if (("MAC").equals(sva.getIdType())) {
                isIpMac = "C01ADA2E2BC0";
            }
            if(FLAG1.equals(hasIdType)){
                idTypeString = ",\"idType\":\""+sva.getIdType()+"\"";
            }
            
            // 非匿名化全量订阅
            if (sva.getType() == 0)
            {
                url = "https://" + sva.getIp() + ":" + sva.getTokenPort()
                        + "/enabler/catalog/locationstreamreg/json/v1.0";
                content = "{\"APPID\":\"" + sva.getUsername() + "\"" + idTypeString + "}";
            }
            // 匿名化全量订阅
            else if (sva.getType() == 1)
            {
                url = "https://"
                        + sva.getIp()
                        + ":"
                        + sva.getTokenPort()
                        + "/enabler/catalog/locationstreamanonymousreg/json/v1.0";
                content = "{\"APPID\":\"" + sva.getUsername() + "\"}";
            }
            // 指定用户订阅
            else if (sva.getType() == 2)
            {
                url = "https://" + sva.getIp() + ":" + sva.getTokenPort()
                        + "/enabler/catalog/locationstreamreg/json/v1.0";
                content = "{\"APPID\":\"" + sva.getUsername()+ "\""
                        + idTypeString
                        + ",\"useridlist\":[\""+isIpMac+"\"]}";
            }
            LOG.debug("subscription param:"+content);
            // 获取订阅ID
            Map<String,String> subResult = this.httpsPost(url, content, charset,"POST", tokenResult.get("token"), svaSSLVersion);
            LOG.debug("subscription result:" + subResult.get("result"));
            mylog.location("subscription result:" + subResult.get("result"));
            JSONObject jsonObj = JSONObject.fromObject(subResult.get("result")); 
            //判断是否订阅成功,成功为0
            JSONObject svaResult =  jsonObj.getJSONObject("result");
            int svaString = svaResult.getInt("error_code");
            if (0==svaString) {
                JSONArray list = jsonObj.getJSONArray("Subscribe Information");
                JSONObject obj = (JSONObject) list.get(0);
                String queueId = obj.getString("QUEUE_ID");
                LOG.debug("queueId:" + queueId);
                mylog.location("queueId:" + queueId);
                
                // 如果获取queueId，则进入数据对接逻辑
                if(StringUtils.isNotEmpty(queueId)){
                    AmqpThread at = new AmqpThread(sva,amqpDao,queueId);
                    GlobalConf.addAmqpThread(sva.getId(), at);
                    at.start();
                }else{
                    LOG.warn("queueId got failed:svaId:" + sva.getId());
                    mylog.location("queueId got failed:svaId:" + sva.getId());
                }
            }else
            {
                LOG.debug("sva Subscription failed: "+jsonObj);
                mylog.location("sva Subscription failed: "+jsonObj);
            }
        }
        catch (IOException e)
        {
            LOG.error("IOException.", e);
        }
        catch (KeyManagementException e)
        {
            LOG.error("KeyManagementException.", e);
        }
        catch (NoSuchAlgorithmException e)
        {
            LOG.error("NoSuchAlgorithmException.", e);
        }
    }
    
    /**   
     * @Title: unsubscribe   
     * @Description: 取消订阅    
     * @return: void      
     * @throws   
     */ 
    public void unsubscribe(SvaModel sva)
    {
        LOG.debug("unsubcribe started!");
        mylog.location("unsubcribe started!");
        if (StringUtils.isNotEmpty(sva.getToken()))
        {
            return;
        }
        String url = "";
        String content = "";

        try
        {
            // 获取token
            url = "https://" + sva.getIp() + ":"
                    + sva.getTokenPort() + "/v3/auth/tokens";
            content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                    + sva.getUsername()
                    + "\",\"password\": \""
                    + sva.getPassword() + "\"}}}}}";
            String charset = "UTF-8";
            Map<String,String> tokenResult = this.httpsPost(url, content, charset,"POST", null, svaSSLVersion);            
            String token = tokenResult.get("token");            
            if(StringUtils.isEmpty(token)){
                LOG.warn("[unsubscribe]token got failed:svaId:" + sva.getId());
                mylog.location("[unsubscribe]token got failed:svaId:" + sva.getId());
                return;
            }
            LOG.debug("[unsubscribe]token got:"+token);
            mylog.location("[unsubscribe]token got:"+token);

            // 是否需要在订阅参数中加idType
            String idTypeString = "";
            if(FLAG1.equals(hasIdType)){
                idTypeString = ",\"idType\":\""+sva.getIdType()+"\"";
            }
            
            // 非匿名化取消订阅
            if (sva.getType() == 0){
                url = "https://" + sva.getIp() + ":" + sva.getTokenPort()
                        + "/enabler/catalog/locationstreamunreg/json/v1.0";
                content = "{\"APPID\":\"" + sva.getUsername()+ "\""+idTypeString+"}";
                Map<String,String> subResult = this.httpsPost(url, content,charset, "DELETE", token, svaSSLVersion);
                LOG.debug("[unsubscribe]result:" + subResult.get("result"));
                mylog.location("[unsubscribe]result:" + subResult.get("result"));
            }else if(sva.getType() == 1){
                // 匿名化取消订阅
                url = "https://" + sva.getIp() + ":" + sva.getTokenPort()
                        + "/enabler/catalog/locationstreamanonymousunreg/json/v1.0";
                content = "{\"APPID\":\"" + sva.getUsername() + "\"}";
                Map<String,String> subResultAnonymous = this.httpsPost(url, content,charset, "DELETE", token, svaSSLVersion);
                LOG.debug("[unsubscribe]anonymous result:" + subResultAnonymous.get("result"));
                mylog.location("[unsubscribe]anonymous result:" + subResultAnonymous.get("result"));
            }
            
            // 关闭amqp连接
            GlobalConf.removeAmqpThread(sva.getId());
            LOG.debug("[unsubscribe]connection closed!");
            mylog.location("[unsubscribe]connection closed!");

        }
        catch (KeyManagementException e)
        {
            LOG.error("KeyManagementException.", e);
        }
        catch (NoSuchAlgorithmException e)
        {
            LOG.error("NoSuchAlgorithmException.", e);
        }
        catch (IOException e)
        {
            LOG.error("IOException.", e);
        }
        catch (Exception e)
        {
            LOG.error("Exception.", e);
        }
    }
}
