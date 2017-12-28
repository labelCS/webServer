package com.sva.web.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.common.Util;
import com.sva.dao.RoleDao;
import com.sva.dao.StoreDao;
import com.sva.model.StoreModel;
import com.sva.web.models.StoreMngModel;
import com.sva.web.models.extension.StoreMngModelExtension;

@Controller
@RequestMapping(value = "/store")
public class StoreController
{

    @Autowired
    private StoreDao dao;

    @Autowired
    private RoleDao roleDao;

    private static final Logger LOG = Logger.getLogger(StoreController.class);

    /*
     * getTableData--查询部分商场 selectStore--通过用户名查询有哪些商场权限
     */
    @RequestMapping(value = "/api/getData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData(HttpServletRequest request)
    {
        LOG.info("StoreController:getTableData");
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        @SuppressWarnings("unchecked")
        List<String> storeides = (List<String>) request.getSession()
                .getAttribute("storeides");
        Collection<StoreModel> resultList = new ArrayList<StoreModel>();
        Collection<StoreModel> store;
        Object userName = request.getSession().getAttribute("username");
        if ("admin".equals(userName))
        {
            resultList = dao.doStores();
        }
        else if (storeides != null && !storeides.isEmpty())
        {
            String storeid = storeides.get(0);
            String[] stores = storeid.split(",");

            for (int i = 0; i < stores.length; i++)
            {
                store = dao.doquery(stores[i]);
                if (store != null && !store.isEmpty())
                {
                    resultList.addAll(store);
                }
            }

        }
        modelMap.put("error", null);
        modelMap.put("data", resultList);

        return modelMap;
    }

    
    /** 
     * @Title: getDataBySample 
     * @Description: 获取所有商场无权限
     * @return Map<String,Object>       
     * @throws 
     */
    @RequestMapping(value = "/api/getDataBySample", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getDataBySample()
    {
        LOG.info("StoreController:getTableData");
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Collection<StoreModel> resultList = dao.doStores();
      
        modelMap.put("error", null);
        modelMap.put("data", resultList);

        return modelMap;
    }

    // 查询所有的商场
    @RequestMapping(value = "/api/stores", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getStores()
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Collection<StoreModel> resultList = dao.doStores();
        modelMap.put("error", null);
        modelMap.put("data", resultList);
        return modelMap;
    }

    @RequestMapping(value = "/api/getStoreBySvaId", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getStoreBySvaId(@RequestParam("svaId") String svaId)
    {
        LOG.info("StoreController:getStoreBySvaId::" + svaId);
        List<String> resultList = dao.getStoreNameBySva(Integer.parseInt(svaId));
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", resultList);

        return modelMap;
    }

    @RequestMapping(value = "/api/saveData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> saveData(HttpServletRequest request, @Valid @RequestBody StoreMngModel smm, BindingResult result)
    {
        LOG.info("StoreController:saveData:: " + smm.toString());
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 校验用户输入
        if(result.hasErrors()){
            modelMap.put("error", result.getAllErrors());
            return modelMap;
        }
        Object userName = request.getSession().getAttribute("username");
        StoreModel sm = StoreMngModelExtension.toStore(smm);

        // 保存
        if (StringUtils.isEmpty(smm.getId()))
        {
            // 校验商场是否重名 TODO
            String storeName = sm.getName();
            dao.saveInfo(sm);
            // 添加商场时自动更新到角色商场权限
            int storeId = dao.selectStoreid(storeName);
            String storeid = roleDao.queryStoreidFromRole(userName.toString())
                    + ',' + storeId;
            String stores = roleDao.queryStoreFromRole(userName.toString()) + ','
                    + storeName;
            int roleid = roleDao.selectRoleid(userName.toString());
            roleDao.updateInfoStore(storeid, stores, roleid);

        }
        else
        {
            // 校验商场是否重名 TODO
            sm.setId(Integer.parseInt(smm.getId()));
            dao.updateInfo(sm);
        }

        modelMap.put("data", null);
        return modelMap;
    }

    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteData(HttpServletRequest request,@RequestParam("id") String id)
    {
        String ip = Util.getIpAddr(request);
        LOG.info("StoreController:deleteData::" + id+" ip:"+ip);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        try
        {
            dao.deleteById(id);
        }
        catch (Exception e)
        {
            LOG.error(e);
            modelMap.put("error", e.getStackTrace());
        }

        return modelMap;
    }
}
