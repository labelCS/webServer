package com.sva.web.controllers;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sva.common.conf.Params;
import com.sva.dao.BluemixDao;
import com.sva.model.BluemixModel;

@Controller
@RequestMapping(value = "/bluemix")
public class BluemixController
{

    @Autowired
    private BluemixDao dao;
    
    private static final Logger LOG = Logger.getLogger(BluemixController.class);

    @RequestMapping(value = "/api/getData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData()
    {

        Collection<BluemixModel> resultList = dao.doquery();
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", resultList);

        return modelMap;
    }

    @RequestMapping(value = "/api/saveData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> saveData(@RequestBody BluemixModel bm)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Date current = new Date();
        bm.setCreateTime(current);
        bm.setUpdateTime(current);
        bm.setStatus(Params.STATUS_RUNNING);
        int id = bm.getId();
        // 保存
        try
        {
            if (id == Params.ZERO)
            {
                dao.saveInfo(bm);
            }
            else
            {
                dao.updateInfo(bm);
            }
        }
        catch (Exception e)
        {
            LOG.error(e);
            modelMap.put("error", e.getStackTrace());
        }

        modelMap.put("data", null);
        return modelMap;
    }

    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteData(@RequestParam("id") String id)
    {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        try
        {
            dao.deleteInfo(id);
        }
        catch (Exception e)
        {
            LOG.error(e);
            modelMap.put("error", e.getStackTrace());
        }

        return modelMap;
    }

    @RequestMapping(value = "/api/changeStatus", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> changeStatus(@RequestParam("id") String id,
            @RequestParam("status") String status)
    {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        try
        {
            dao.changeStatus(id, status);
        }
        catch (Exception e)
        {
            LOG.error(e);
            modelMap.put("error", e.getStackTrace());
        }

        return modelMap;
    }
}
