package com.sva.web.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.sva.common.Util;
import com.sva.dao.ElectronicDao;
import com.sva.model.ElectronicModel;

@Controller
@RequestMapping(value = "/electronic")
public class ElectronicController
{

    @Autowired
    private ElectronicDao dao;
    
    private static final Logger LOG = Logger.getLogger(DynamicAccuracyController.class);

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getMsgData(HttpServletRequest request)
    {
        Collection<ElectronicModel> resultList = new ArrayList<ElectronicModel>(
                10);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Collection<ElectronicModel> store;
        Object userName = request.getSession().getAttribute("username");
        @SuppressWarnings("unchecked")
        List<String> storeides = (List<String>) request.getSession()
                .getAttribute("storeides");
        if ("admin".equals(userName))
        {

            resultList = dao.doquery();
        }
        else if (!storeides.isEmpty())
        {
            String storeid = storeides.get(0);
            String[] stores = storeid.split(",");
            for (int i = 0; i < stores.length; i++)
            {
                store = dao.doqueryByStoreid(Integer.parseInt(stores[i]));
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

    @RequestMapping(value = "/api/saveData")
    public String saveMsgData(HttpServletRequest request, ElectronicModel electronicModel,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "file1", required = false) MultipartFile file1)
    {
        // 保存
        String ip = Util.getIpAddr(request);
        LOG.info("insert electronic "+ip);
        String path = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload");
        String nu = "";
        Calendar calendar = Calendar.getInstance();
        Date d = calendar.getTime();
        if (electronicModel.getId().equals(nu))
        {
            saveFile(electronicModel, file, file1, path, nu, d);

            return "redirect:/home/showElectronic";

        }
        else
        {
            saveFile1(electronicModel, file, file1, path, nu, d);

            return "redirect:/home/showElectronic";
        }
    }

    private void saveFile1(ElectronicModel msgMngModel, MultipartFile file,
            MultipartFile file1, String path, String nu, Date d)
    {
        try
        {
            String fileName = file.getOriginalFilename();
            String fileName1 = file1.getOriginalFilename();
            if (fileName.equals(nu) && fileName1.equals(nu))
            {
                dao.updateMsgInfo(msgMngModel);
            }
            if (fileName.equals(nu) && !fileName1.equals(nu))
            {
                String ext1 = fileName1.substring(fileName1.lastIndexOf('.'));
                fileName1 = d.getTime() + ext1;
                msgMngModel.setMoviePath(fileName1);
                File targetFile1 = new File(path, fileName1);
                getFile(targetFile1);
                file1.transferTo(targetFile1);
                dao.updateMsgInfo1(msgMngModel);
            }
            if (!fileName.equals(nu) && fileName1.equals(nu))
            {
                String ext = fileName.substring(fileName.lastIndexOf('.'));
                fileName = d.getTime() + ext;
                msgMngModel.setPictruePath(fileName);
                File targetFile = new File(path, fileName);
                getFile(targetFile);
                file.transferTo(targetFile);
                dao.updateMsgInfo2(msgMngModel);
            }
            if (!fileName.equals(nu) && !fileName1.equals(nu))
            {
                String ext = fileName.substring(fileName.lastIndexOf('.'));
                fileName = d.getTime() + ext;
                msgMngModel.setPictruePath(fileName);
                File targetFile = new File(path, fileName);
                getFile(targetFile);
                file.transferTo(targetFile);
                String _ext1 = fileName1.substring(fileName1.lastIndexOf('.'));
                fileName1 = d.getTime() + _ext1;
                msgMngModel.setMoviePath(fileName1);
                File targetFile1 = new File(path, fileName1);
                getFile(targetFile1);
                file1.transferTo(targetFile1);
                dao.updateMsgInfo3(msgMngModel);
            }
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
    }

    private static void getFile(File targetFile1)
    {
        if (!targetFile1.exists())
        {
            targetFile1.mkdirs();
        }
    }

    private void saveFile(ElectronicModel msgMngModel, MultipartFile file,
            MultipartFile file1, String path, String nu, Date d)
    {
        String fileName = file.getOriginalFilename();
        String fileName1 = file1.getOriginalFilename();
        String ext = null;
        String ext1 = null;
        if (fileName != nu)
        {
            ext = fileName.substring(fileName.lastIndexOf('.'));

        }
        if (fileName1 != nu)
        {
            ext1 = fileName1.substring(fileName1.lastIndexOf('.'));

        }

        fileName = d.getTime() + ext;
        fileName1 = d.getTime() + ext1;
        if (ext != null)
        {
            msgMngModel.setPictruePath(fileName);
        }
        if (ext1 != null)
        {
            msgMngModel.setMoviePath(fileName1);
        }
        Logger.getLogger(MapController.class).debug(path);
        File targetFile = new File(path, fileName);
        File targetFile1 = new File(path, fileName1);
        getFile(targetFile);
        getFile(targetFile1);
        // 保存
        try
        {
            file.transferTo(targetFile);
            file1.transferTo(targetFile1);
            dao.saveElectronic(msgMngModel);

        }
        catch (Exception e)
        {
            LOG.error(e);
        }
    }

    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteMsgData(@RequestParam("id") String id)
    {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        try
        {
             dao.deleteElectronic(Integer.valueOf(id));
        }
        catch (Exception e)
        {
            LOG.error(e);
        }

        return modelMap;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex)
    {
        String info;
        if (ex instanceof MaxUploadSizeExceededException)
        {
            info = "Max";
        }
        else
        {
            info = "未知错误: " + ex.getMessage();
        }
        ModelAndView model = new ModelAndView("redirect:/home/showMsgMng");
        model.addObject("info", info);
        return model;

    }

}
