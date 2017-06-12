package com.sva.web.controllers;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sva.common.HttpUtil;
import com.sva.common.Util;
import com.sva.common.conf.Params;
import com.sva.dao.SvaDao;
import com.sva.model.SvaModel;
import com.sva.service.SubscriptionService;
import com.sva.service.core.HttpsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/svalist")
public class SvaController  extends HttpsService
{
    
    @Autowired
    private SubscriptionService service;

    @Autowired
    private SvaDao dao;
    
    
    /** 
     * @Fields svaSSLVersion : SVA使用的SSL版本
     */ 
    @Value("${sva.sslVersion}")
    private String svaSSLVersion;
    

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
            sm.setType(l.getType());
            sm.setIdType(l.getIdType());
            sm.setTokenPort(l.getTokenPort());
            sm.setBrokerPort(l.getBrokerPort());
            sm.setApiLists(l.getApiLists());
            sm.setMapLists(l.getMapLists());
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
        //mapId个数
//        int size  = svaModel.getMapList().size();
//        String mapIds = null;
//        if (size>0) {
//            mapIds =  StringUtils.join(svaModel.getMapList(),",");
//        }
//        svaModel.setMapLists(mapIds);
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
        String token = null;

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int i = 0;
        String nu = "";
        try
        {
            token = capi.httpsPost(url, content, charset);
            if (id == nu)
            {
                i = dao.checkSvaByName(name);
                if (i > 0)
                {
                    modelMap.put("data", false);
//                    return modelMap;
                }
                else
                {
                    modelMap.put("data", true);
//                    return modelMap;
                }
            }
            else
            {
                i = dao.checkSvaByName1(name, id);
                if (i > 0)
                {
                    modelMap.put("data", false);
//                    return modelMap;
                }
                else
                {
                    modelMap.put("data", true);
//                    return modelMap;
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
        String apiUrl = "https://" + ip + ':' + tokenNumber + "/enabler/apilist/json/v1.0";
        Map<String,String> subResult = null;
        JSONArray apiList =null;
        JSONArray idTypeList = null;
        String mapListType = null;
        JSONArray mapList = null;
        LOG.debug("get apilist:"+" url:"+apiUrl+" svaSSLVersion:"+svaSSLVersion+" token:"+token);
        try {
            if (token!=null) {
                subResult = this.httpsPost(apiUrl, null, charset,"GET", token, svaSSLVersion);
                LOG.debug("get apilist result:" + subResult.get("result"));
                JSONObject jsonObj = JSONObject.fromObject(subResult.get("result"));
                //判断是否订阅成功,成功为0
                JSONObject svaResult =  jsonObj.getJSONObject("result");
                int svaString = svaResult.getInt("error_code");
                if (0==svaString) {
                 apiList = jsonObj.getJSONArray("API List");
                 idTypeList = jsonObj.getJSONArray("IdType List");
                 mapListType = jsonObj.getJSONObject("Map List Type").toString();
                 mapList  = jsonObj.getJSONArray("Map List");
                JSONObject obj = (JSONObject) apiList.get(0);
                String api = obj.getString("API 0");
                LOG.debug("apiList:" + apiList+" idTypeList:"+idTypeList+" mapListType:"+mapListType+" mapList:"+mapList+" api 0:"+api);
                }
            }
        } catch (KeyManagementException e) {
            LOG.error("get apilist KeyManagementException: ",e);
        } catch (NoSuchAlgorithmException e) {
            LOG.error("get apilist NoSuchAlgorithmException: ",e);
        } catch (IOException e) {
            LOG.error("get apilist IOException: ",e);
        }
//        List<String>  apiLists = new ArrayList<String>();
//        apiLists.add("Geofencing");
//        apiLists.add("locationstream");
//        apiLists.add("locationstreamanonymous");
//        apiLists.add("specifieduserslocationstream");
//        List<String>  idTypeLists = new ArrayList<String>();
//        idTypeLists.add("IMEI");
//        idTypeLists.add("IP");
//        List<String>  mapListS = new ArrayList<String>();
//        mapListS.add("123");
//        mapListS.add("133");
//        mapListS.add("143");
//        apiList = JSONArray.fromObject(apiLists);
//        idTypeList = JSONArray.fromObject(idTypeLists);
//        mapListType =  "ALL"; 
//        mapList =   JSONArray.fromObject(mapListS);
        modelMap.put("apiList", apiList);
        modelMap.put("idTypeList", idTypeList);
        modelMap.put("mapListType", mapListType);
        modelMap.put("mapList", mapList);
        return modelMap;
        
    }
}
