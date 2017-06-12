package com.sva.web.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sva.common.HttpUtil;
import com.sva.common.Util;
import com.sva.common.conf.Params;
import com.sva.common.email.SimpleMail;
import com.sva.common.email.SimpleMailSender;
import com.sva.dao.SvaDao;
import com.sva.model.SvaModel;
import com.sva.service.SubscriptionService;
import com.sva.service.ValidateSVAService;

@Controller
@RequestMapping(value = "/svalist")
public class SvaController
{
    
    @Autowired
    private SubscriptionService service;
    
    @Autowired
    private ValidateSVAService validateSVAService;
    
    @Autowired
    private SvaDao dao;

    private static final Logger LOG = Logger.getLogger(SvaController.class);

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getData(HttpServletRequest request)
    {

        List<SvaModel> list = new ArrayList<SvaModel>(10);
        List<SvaModel> listes = new ArrayList<SvaModel>(10);
        List<SvaModel> listTemp = new ArrayList<SvaModel>(10);
        Collection<SvaModel> resultList = dao.doquery();
        Object userName = request.getSession().getAttribute("username");
        @SuppressWarnings("unchecked")
        List<String> storeides = (List<String>) request.getSession()
                .getAttribute("storeides");
        SvaModel sm;
        List<String> store;
        for (SvaModel l : resultList)
        {
            sm = new SvaModel();
            sm.setId(l.getId());
            // 赋值商场地点的值
            store = dao.storeBySva(l.getId());
            sm.setPosition(store);
            sm.setIp(l.getIp());
            sm.setName(l.getName());
            sm.setUsername(l.getUsername());
            sm.setPassword(l.getPassword());
            sm.setStatus(l.getStatus());
            sm.setStatusCode(l.getStatusCode());
            sm.setType(l.getType());
            sm.setIdType(l.getIdType());
            sm.setTokenPort(l.getTokenPort());
            sm.setBrokerPort(l.getBrokerPort());
            sm.setManagerEmail(l.getManagerEmail());
            list.add(sm);
        }
        int liSize = list.size();
        for (int i = 0; i < liSize; i++)
        {
            if (list.get(i).getPosition().size() > 0)
            {
                listes.add(list.get(i));
            }
        }
        if (!("admin").equals(userName))
        {
            String place;
            String placeid;
            int listSize = listes.size();
            for (int i = 0; i < listSize; i++)
            {
                place = listes.get(i).getPosition().get(0);
                placeid = dao.storeIdByName(place);
                if (!storeides.isEmpty())
                {
                    String storeid = storeides.get(0);
                    String[] stores = storeid.split(",");
                    for (int j = 0; j < stores.length; j++)
                    {
                        if (placeid.equals(stores[j]))
                        {
                            listTemp.add(listes.get(i));
                        }
                    }
                }
            }
            listes = listTemp;
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", listes);

        return modelMap;
    }

    /** 
     * @Title: saveSvaData 
     * @Description: 保存sva逻辑，有id则更新，否则添加 
     * @param request
     * @param model
     * @param svaModel
     * @return 
     */
    @RequestMapping(value = "/api/saveData")
    public String saveSvaData(HttpServletRequest request, SvaModel svaModel)
    {
        String ip = Util.getIpAddr(request);
        //设置stroeId
        svaModel.setStoreId(svaModel.getPosition().get(0));
        LOG.info("insert sva "+svaModel.getIp()+","+svaModel.getStoreId()+" ip:"+ip);
        Integer id = Params.ZERO;
        Integer newId;
        String st = "";
        if (svaModel.getId() != st)
        {
            id = Integer.parseInt(svaModel.getId());
        }


        // 更新
        if (id > 0)
        {
            // 先取出之前的sva信息，发起取消订阅
            SvaModel sm = dao.getSvaById(id);
            service.unsubscribe(sm);
            newId = id;
            // 然后更新数据库
            dao.updateSvaInfo(svaModel);    
        }
        // 添加
        else
        {
            newId = dao.saveSvaInfo(svaModel);
            newId = Integer.parseInt(svaModel.getId());
        }
        // 如果sva是开启状态，则发起订阅
        if(svaModel.getStatus() == 1){
            service.subscribeSva(svaModel);
        }
        // 更新映射表
        dao.deleteMapBySva(newId);
        dao.addMapper(newId, svaModel.getPosition());


        return "redirect:/home/showSvaMng";
    }

    /** 
     * @Title: disableData 
     * @Description: 关闭sva，取消订阅 
     * @param model
     * @param id
     * @return 
     */
    @RequestMapping(value = "/api/disableData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> disableData(@RequestParam("id") String id)
    {
        LOG.info("Params[id]:" + id);

        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        int result = 0;
        
        result = dao.disableSva(id);
        // 取出sva信息，发起取消订阅
        SvaModel sm = dao.getSvaById(Integer.parseInt(id));
        service.unsubscribe(sm);

        if (result > 0)
        {
            modelMap.put("error", null);
        }
        else
        {
            modelMap.put("error", true);
        }

        return modelMap;
    }

    /**
     * 启用sva 
     * @param model
     * @param id 要启用的sva的id
     * @return
     */
    @RequestMapping(value = "/api/enableData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> enableData(@RequestParam("id") String id)
    {
        LOG.info("Params[id]:" + id);

        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        int result = dao.enableSva(id);
        // 取出sva信息，发起订阅
        SvaModel sm = dao.getSvaById(Integer.parseInt(id));
        service.subscribeSva(sm);

        if (result > 0)
        {
            modelMap.put("error", null);
        }
        else
        {
            modelMap.put("error", true);
        }

        return modelMap;
    }

    /**
     * 删除sva
     * @param model
     * @param id 要删除的sva的id
     * @return
     */
    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteData(@RequestParam("id") String id)
    {
        LOG.info("Params[id]:" + id);

        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        // 先取出sva信息，发起取消订阅
        SvaModel sm = dao.getSvaById(Integer.parseInt(id));
        service.unsubscribe(sm);
        // 删除sva信息
        int result = dao.deleteSvaTemp(id);
        // 删除sva在映射表的信息
        dao.deleteMapBySva(Integer.parseInt(id));

        if (result > 0)
        {
            modelMap.put("error", null);
        }
        else
        {
            modelMap.put("error", true);
        }

        return modelMap;
    }

    @RequestMapping(value = "/api/getEnabled", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getStartData()
    {

        List<SvaModel> list = new ArrayList<SvaModel>(10);
        Collection<SvaModel> resultList = dao.getEnabled();
        SvaModel sm;
        for (SvaModel l : resultList)
        {
            sm = new SvaModel();
            sm.setId(l.getId());
            sm.setIp(l.getIp());
            sm.setName(l.getName());
            sm.setUsername(l.getUsername());
            sm.setPassword(l.getPassword());
            sm.setStatus(l.getStatus());
            list.add(sm);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", list);

        return modelMap;
    }

    @RequestMapping(value = "/api/checkName", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> checkName(@RequestParam("id") String id,
            @RequestParam("svaName") String name,
            @RequestParam("ip") String ip,
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            @RequestParam("token") String tokenNumber)
    {
        String url = "https://" + ip + ':' + tokenNumber + "/v3/auth/tokens";
        String content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                + userName + "\",\"password\": \"" + password + "\"}}}}}";
        String charset = "UTF-8";
        LOG.debug("from ip:" + ip + ",getToken url:" + url);
        HttpUtil capi = new HttpUtil();

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int i = 0;
        String nu = "";
        try
        {
            capi.httpsPost(url, content, charset);
            if (id == nu)
            {
                i = dao.checkSvaByName(name);
                if (i > 0)
                {
                    modelMap.put("data", false);
                    return modelMap;
                }
                else
                {
                    modelMap.put("data", true);
                    return modelMap;
                }
            }
            else
            {
                i = dao.checkSvaByName1(name, id);
                if (i > 0)
                {
                    modelMap.put("data", false);
                    return modelMap;
                }
                else
                {
                    modelMap.put("data", true);
                    return modelMap;
                }

            }
        }
        catch (Exception e)
        {
            String st = e.getMessage();
            LOG.error("token: ",e);
            if ("Connection timed out: connect".equals(st))
            {
                modelMap.put("ip", true);
                return modelMap;
            }
            else
            {
                modelMap.put("error", true);
                return modelMap;
            }
        }
    }
    
    /** 
     * @Title: svaValidate 
     * @Description: sva验证(手动) 
     * @param id
     * @return 
     * @author gl
     */
    @RequestMapping(value = "/api/svaValidate", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> svaValidate(@RequestParam("id") String id)
    {
        LOG.info("Params[id]:" + id);

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Map<String, Object> retMap = validateSVAService.validateSVA(Integer.parseInt(id));
        SvaModel sva = dao.getSvaById(Integer.parseInt(id));
        SimpleMail simpleMail = new SimpleMail();
        String emailTitle = "";
        boolean isSendEmail = false;
        if(!(Boolean)retMap.get("status")){
            // 未修复成功
            emailTitle = "sva获取location数据获取失败";
            isSendEmail = true;
        }else{
            // 发送邮件（修复成功）
            if(retMap.get("isRepaired")!= null && (Boolean)retMap.get("isRepaired")){
                // 修复成功
                emailTitle = "sva获取location数据获取修复正常";
                isSendEmail = true;
            }
        }
        String emailContent = (String) retMap.get("msg");
        simpleMail.setSubject(emailTitle);
        simpleMail.setContent(emailContent);
        simpleMail.setToList(Arrays.asList(sva.getManagerEmail()));
        SimpleMailSender simpleMailSender = new SimpleMailSender(simpleMail);
        try {
            if(isSendEmail){
                simpleMailSender.send(simpleMail);
                LOG.info("--sva验证--：邮件已发送成功（手动）,收件人是" + simpleMail.getToList().get(0));
            }
        } catch (AddressException e) {
            LOG.error("邮件发送失败", e);
        } catch (MessagingException e) {
            LOG.error("邮件发送失败", e);
        }
        return modelMap;
    }
}
