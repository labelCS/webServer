package com.sva.web.controllers;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.sva.dao.AreaDao;
import com.sva.dao.LocationDao;
import com.sva.dao.MessageDao;
import com.sva.dao.TicketDao;
import com.sva.model.AreaModel;
import com.sva.model.MapsModel;
import com.sva.model.MessageModel;
import com.sva.model.StoreModel;
import com.sva.model.TicketModel;
import com.sva.web.models.MsgMngModel;

@Controller
@RequestMapping(value = "/message")
public class MessageController
{

    @Autowired
    private MessageDao dao;
    
    @Autowired
    private TicketDao ticDao;

    @Autowired
    private AreaDao areaDao;
    
    private static final Logger LOG = Logger.getLogger(LocationDao.class);

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getMsgData(HttpServletRequest request)
    {
        Collection<MessageModel> resultList = new ArrayList<MessageModel>(10);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Collection<MessageModel> store;
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
    
    @RequestMapping(value = "/api/getTicketData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTicketData(HttpServletRequest request,
        @RequestParam("msgId") String msgId)
    {
        Collection<TicketModel> resultList = new ArrayList<TicketModel>(10);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Collection<TicketModel> store;
        Object userName = request.getSession().getAttribute("username");
        @SuppressWarnings("unchecked")
        List<String> storeides = (List<String>) request.getSession()
                .getAttribute("storeides");
        if ("admin".equals(userName))
        {

            resultList = ticDao.getAllTicket(msgId);
        }
        else if (!storeides.isEmpty())
        {
            String storeid = storeides.get(0);
            String[] stores = storeid.split(",");
            for (int i = 0; i < stores.length; i++)
            {
                store = ticDao.getAllTicketById(Integer.parseInt(stores[i]),msgId);
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
    public String saveMsgData(
            HttpServletRequest request,
            MsgMngModel msgMngModel,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "file1", required = false) MultipartFile file1,
            @RequestParam(value = "file2", required = false) MultipartFile file2
            )
    {
        // 保存
        String ip = Util.getIpAddr(request);
        LOG.info("insert message name="+msgMngModel.getFloorNo()+" ip:"+ip);
        String path = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload");
        String nu = "";
        int shopId = msgMngModel.getShopId();
        BigDecimal x = new BigDecimal(0);
        BigDecimal x1 = new BigDecimal(0);
        BigDecimal y = new BigDecimal(0);
        BigDecimal y1 = new BigDecimal(0);
        List<AreaModel> lis = areaDao.getAreaByAreaId(String.valueOf(shopId));
        if (!lis.isEmpty())
        {
            x = lis.get(0).getxSpot();
            x1 = lis.get(0).getX1Spot();
            y = lis.get(0).getySpot();
            y1 = lis.get(0).getY1Spot();
        }
        msgMngModel.setxSpot(x);
        msgMngModel.setX1Spot(x1);
        msgMngModel.setySpot(y);
        msgMngModel.setY1Spot(y1);

        Calendar calendar = Calendar.getInstance();
        Date d = calendar.getTime();
        if (msgMngModel.getId().equals(nu))
        {
            saveFile(msgMngModel, file, file1, path, nu, d);

            return "redirect:/home/showMsgMng";

        }
        else
        {
            saveFile1(msgMngModel, file, file1,file2, path, nu, d);

            return "redirect:/home/showMsgMng";
        }
    }

    
    
    @RequestMapping(value = "/api/saveTicket")
    public String saveTicket(
            HttpServletRequest request,
            TicketModel ticketModel,
            @RequestParam(value = "fileTicket", required = false) MultipartFile file)

    {
        String ip = Util.getIpAddr(request);
        String id = ticketModel.getId();
        if (StringUtils.isEmpty(id))
        {
            LOG.info("insert ticket name="+ticketModel.getMsg().getId()+" ip:"+ip);
            long timestamp = System.currentTimeMillis();
            String tiketPath = file.getOriginalFilename();
            String path = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload");
            if (!("").equals(tiketPath))
            {
                String ext = tiketPath.substring(tiketPath.lastIndexOf('.'));
                tiketPath = "ticket"+timestamp+ext;
                ticketModel.setTicketPath(tiketPath);
                File targetFile = new File(path, tiketPath);
                if (!targetFile.exists())
                {
                    targetFile.mkdirs();
                }
                try
                {
                    file.transferTo(targetFile);
                }
                catch (Exception e)
                {
                    LOG.error("save Ticke error:",e);
                }
            }
            ticDao.saveTicket(ticketModel);
            return "redirect:/home/showMsgMng";  
        }
        else
        {
            LOG.info("update ticket name="+ticketModel.getMsg().getId()+" ip:"+ip);            
            String tiketPath = file.getOriginalFilename();
            if (!("").equals(tiketPath))
            {
                long timestamp = System.currentTimeMillis();
                String path = request.getSession().getServletContext()
                    .getRealPath("/WEB-INF/upload"); 
                String ext = tiketPath.substring(tiketPath.lastIndexOf('.'));
                tiketPath = "ticket"+timestamp+ext;
                ticketModel.setTicketPath(tiketPath);
                File targetFile = new File(path, tiketPath);
                if (!targetFile.exists())
                {
                    targetFile.mkdirs();
                }
                try
                {
                    file.transferTo(targetFile);
                }
                catch (Exception e)
                {
                    LOG.error("save Ticke error:",e);
                }
                
            }
            ticDao.updataTicket(ticketModel);
            return "redirect:/home/showMsgMng";
        }
        
    }
    
    
    private void saveFile1(MsgMngModel msgMngModel, MultipartFile file,
            MultipartFile file1, MultipartFile file2, String path, String nu,
            Date d)
    {
        try
        {
            String fileName = file.getOriginalFilename();
            String fileName1 = file1.getOriginalFilename();
            String fileName2 = "";
            if (fileName.equals(nu) && fileName1.equals(nu)
                    && fileName2.equals(nu))
            {
                MessageModel msgModel = formToBean(msgMngModel);
                dao.updateMsgInfo(msgModel);
            }
            if (fileName.equals(nu) && fileName1.equals(nu)
                    && !fileName2.equals(nu))
            {
                String ext2 = fileName2.substring(fileName2.lastIndexOf('.'));
                fileName2 = "ticket" + d.getTime() + ext2;
                msgMngModel.setTicketPath(fileName2);
                File targetFile2 = new File(path, fileName2);
                getFile(targetFile2);
                file2.transferTo(targetFile2);
                MessageModel msgModel = formToBean(msgMngModel);
                dao.updateMsgInfo(msgModel);
            }
            if (fileName.equals(nu) && !fileName1.equals(nu)
                    && fileName2.equals(nu))
            {
                String ext1 = fileName1.substring(fileName1.lastIndexOf('.'));
                fileName1 = d.getTime() + ext1;
                msgMngModel.setMoviePath(fileName1);
                File targetFile1 = new File(path, fileName1);
                getFile(targetFile1);
                file1.transferTo(targetFile1);
                MessageModel msgModel = formToBean(msgMngModel);
                dao.updateMsgInfo(msgModel);
            }
            if (fileName.equals(nu) && !fileName1.equals(nu)
                    && !fileName2.equals(nu))
            {
                String ext1 = fileName1.substring(fileName1.lastIndexOf('.'));
                fileName1 = d.getTime() + ext1;
                msgMngModel.setMoviePath(fileName1);
                File targetFile1 = new File(path, fileName1);
                getFile(targetFile1);
                file1.transferTo(targetFile1);
                String ext2 = fileName2.substring(fileName2.lastIndexOf('.'));
                fileName2 = "ticket" + d.getTime() + ext2;
                msgMngModel.setTicketPath(fileName2);
                File targetFile2 = new File(path, fileName2);
                getFile(targetFile2);
                file2.transferTo(targetFile2);
                MessageModel msgModel = formToBean(msgMngModel);
                dao.updateMsgInfo(msgModel);
            }
            if (!fileName.equals(nu) && fileName1.equals(nu)
                    && fileName2.equals(nu))
            {
                String ext = fileName.substring(fileName.lastIndexOf('.'));
                fileName = d.getTime() + ext;
                msgMngModel.setPictruePath(fileName);
                File targetFile = new File(path, fileName);
                getFile(targetFile);
                file.transferTo(targetFile);
                MessageModel msgModel = formToBean(msgMngModel);
                dao.updateMsgInfo(msgModel);
            }
            if (!fileName.equals(nu) && fileName1.equals(nu)
                    && !fileName2.equals(nu))
            {
                String ext = fileName.substring(fileName.lastIndexOf('.'));
                fileName = d.getTime() + ext;
                msgMngModel.setPictruePath(fileName);
                File targetFile = new File(path, fileName);
                getFile(targetFile);
                file.transferTo(targetFile);
                String ext2 = fileName2.substring(fileName2.lastIndexOf('.'));
                fileName2 = "ticket" + d.getTime() + ext2;
                msgMngModel.setTicketPath(fileName2);
                File targetFile2 = new File(path, fileName2);
                getFile(targetFile2);
                file2.transferTo(targetFile2);
                MessageModel msgModel = formToBean(msgMngModel);
                dao.updateMsgInfo(msgModel);
            }
            if (!fileName.equals(nu) && !fileName1.equals(nu)
                    && fileName2.equals(nu))
            {
                String ext = fileName.substring(fileName.lastIndexOf('.'));
                fileName = d.getTime() + ext;
                msgMngModel.setPictruePath(fileName);
                File targetFile = new File(path, fileName);
                getFile(targetFile);
                file.transferTo(targetFile);
                String ext1 = fileName1.substring(fileName1.lastIndexOf('.'));
                fileName1 = d.getTime() + ext1;
                msgMngModel.setMoviePath(fileName1);
                File targetFile1 = new File(path, fileName1);
                getFile(targetFile1);
                file1.transferTo(targetFile1);
                MessageModel msgModel = formToBean(msgMngModel);
                dao.updateMsgInfo(msgModel);
            }
            if (!fileName.equals(nu) && !fileName1.equals(nu)
                    && !fileName2.equals(nu))
            {
                String ext = fileName.substring(fileName.lastIndexOf('.'));
                fileName = d.getTime() + ext;
                msgMngModel.setPictruePath(fileName);
                File targetFile = new File(path, fileName);
                getFile(targetFile);
                file.transferTo(targetFile);
                String ext1 = fileName1.substring(fileName1.lastIndexOf('.'));
                fileName1 = d.getTime() + ext1;
                msgMngModel.setMoviePath(fileName1);
                File targetFile1 = new File(path, fileName1);
                getFile(targetFile1);
                file1.transferTo(targetFile1);
                String ext2 = fileName2.substring(fileName2.lastIndexOf('.'));
                fileName2 = "ticket" + d.getTime() + ext2;
                msgMngModel.setTicketPath(fileName2);
                File targetFile2 = new File(path, fileName2);
                getFile(targetFile2);
                file2.transferTo(targetFile2);
                MessageModel msgModel = formToBean(msgMngModel);
                dao.updateMsgInfo(msgModel);
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

    private void saveFile(MsgMngModel msgMngModel, MultipartFile file,
            MultipartFile file1, String path, String nu,
            Date d)
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
        LOG.debug(path);
        File targetFile = new File(path, fileName);
        File targetFile1 = new File(path, fileName1);
        getFile(targetFile);
        getFile(targetFile1);
        // 保存
        try
        {
            file.transferTo(targetFile);
            file1.transferTo(targetFile1);
            MessageModel msgModel = formToBean(msgMngModel);
            dao.saveMsgInfo(msgModel);

        }
        catch (Exception e)
        {
            LOG.error(e);
        }
    }

    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteMsgData(HttpServletRequest request,
            @RequestParam("xSpot") String xSpot,
            @RequestParam("ySpot") String ySpot,
            @RequestParam("floorNo") String floorNo)
    {

        String ip =Util.getIpAddr(request);
        LOG.info("delete msg floorNo"+floorNo+" ip:"+ip);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = 0;
        try
        {
            result = dao.deleteMessage(xSpot, ySpot, floorNo);
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
    
    /** 
     * @Title: deleteTicket 
     * @Description: 删除奖券
     * @param id
     * @return Map<String,Object>       
     * @throws 
     */
    @RequestMapping(value = "/api/deleteTicket", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteTicket(HttpServletRequest request, @RequestParam("id") String id)
    {
        String ip =Util.getIpAddr(request);
        LOG.info("delete ticket id"+id+" ip:"+ip);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = 0;
        try
        {
            result = ticDao.deleteTicket(id);
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

    /** 
     * @Title: formToBean 
     * @Description: 将前台bean转换为后台bean
     * @param form
     * @return 
     */
    private MessageModel formToBean(MsgMngModel form){
        MessageModel result = new MessageModel();
        StoreModel store = new StoreModel();
        MapsModel maps = new MapsModel();
        AreaModel area = new AreaModel();
        result.setStore(store);
        result.setMaps(maps);
        result.setArea(area);
        
        result.getStore().setName(form.getPlace());
        result.getStore().setId(Integer.parseInt(form.getPlaceId()));
        result.getArea().setId(String.valueOf(form.getShopId()));
        result.getArea().setAreaName(form.getShopName());
        result.getMaps().setFloor(form.getFloor());
        result.getMaps().setFloorNo(new BigDecimal(form.getFloorNo()));
        result.setTimeInterval(form.getTimeInterval());
        result.setxSpot(form.getxSpot());
        result.setX1Spot(form.getX1Spot());
        result.setySpot(form.getySpot());
        result.setY1Spot(form.getY1Spot());
        result.setId(form.getId());
        result.setRangeSpot(form.getRangeSpot());
        result.setPictruePath(form.getPictruePath());
        result.setMoviePath(form.getMoviePath());
        result.setTicketPath(form.getTicketPath());
        result.setMessage(form.getMessage());
        result.setIsEnable(form.getIsEnable());
        result.setShopName(form.getShopName());
        return result;
    }
}
