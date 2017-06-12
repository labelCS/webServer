package com.sva.web.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sva.common.Util;
import com.sva.dao.CodeDao;
import com.sva.model.CodeModel;
import com.sva.web.models.CodeMngModel;

@Controller
@RequestMapping(value = "/code")
public class CodeController
{

    @Autowired
    private CodeDao daoCode;
    
    private static final Logger LOG = Logger.getLogger(CodeController.class);

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData()
    {

        List<CodeMngModel> list = new ArrayList<CodeMngModel>(10);
        Collection<CodeModel> resultList = daoCode.getData();
        CodeMngModel cmm;
        for (CodeModel l : resultList)
        {
            cmm = new CodeMngModel();
            cmm.setName(l.getName());
            cmm.setPassword(l.getPassword());
            list.add(cmm);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", list);

        return modelMap;
    }

    @RequestMapping(value = "/api/saveData", method = {RequestMethod.POST})
    public String saveMsgData(HttpServletRequest request, CodeMngModel codeMngModel)
    {
        //构造对象
        CodeModel codeModel = new CodeModel();
        //赋值
        try {
            BeanUtils.copyProperties(codeModel,codeMngModel);
        } catch (Exception e) {
            LOG.error(e);;
        }

        // 保存
        String ip = Util.getIpAddr(request);
        LOG.info("insert code"+ip);
        daoCode.saveCodeInfo(codeModel);
        return "redirect:/home/showCodeMng";

    }

    @RequestMapping(value = "/api/checkName", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> checkName(@RequestParam("name") String name)
    {
        Map<String, Object> ma = new HashMap<String, Object>(2);
        Collection<CodeModel> codeModels = daoCode.getData();
        int i = daoCode.checkByName(name);
        if (i > 0)
        {
            for (CodeModel l : codeModels)
            {
                if (l.getName().equals(name))
                {
                    ma.put("data", false);
                    return ma;
                }
            }
        }
        else
        {
            ma.put("data", true);
            return ma;
        }
        return ma;

    }

    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteMsgData(HttpServletRequest request,
            @RequestParam("name") String name,
            @RequestParam("password") String password)
    {

        String ip = Util.getIpAddr(request);
        LOG.info("delete code name:"+name+" ip:"+ip);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = 0;
        try
        {
            result = daoCode.deleteCode(name, password);
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
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
}
