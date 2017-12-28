package com.sva.web.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
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
import org.springframework.web.multipart.MultipartFile;

import com.sva.common.Util;
import com.sva.dao.MapsDao;
import com.sva.dao.StoreDao;
import com.sva.model.MapsModel;
import com.sva.model.StoreModel;
import com.sva.web.models.MapMngModel;

@Controller
@RequestMapping(value = "/map")
public class MapController
{

    @Autowired
    private MapsDao dao;
    
    @Autowired
    private StoreDao storeDao;
    
    private static final Logger LOG = Logger.getLogger(MapController.class);
    

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData(HttpServletRequest request)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Collection<MapsModel> resultList = new ArrayList<MapsModel>(10);
        Collection<MapsModel> store;
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

    @RequestMapping(value = "/api/deleteByFloor", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteTableData(HttpServletRequest request, @RequestParam("floorNo") String floorNo)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = 0;
        int result1 = 0;
        int result2 = 0;
        try
        {
            // 删除图片
            String name = dao.getMapName(floorNo);
            // 文件路径
            String filedir = request.getSession().getServletContext()
                    .getRealPath("/WEB-INF/upload");
            File file = new File(filedir, name);
            if (!file.exists())
            {
                // 文件不存在
                modelMap.put("error", true);
            }
            else
            {
                if (file.isFile())
                {
                    file.delete();
                }
                else
                {
                    modelMap.put("error", true);
                }
            }

            // 删除数据
            result = dao.deleteMapByFloor(floorNo);
            String ip = Util.getIpAddr(request);
            LOG.info(ip+"delete mapData:floorNo="+floorNo);
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        if (result > 0 || result1 > 0 || result2 > 0)
        {
            modelMap.put("error", null);
        }
        else
        {
            modelMap.put("error", true);
        }

        return modelMap;
    }

    @RequestMapping(value = "/api/upload")
    public String upload(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "svgfile", required = false) MultipartFile svg,
            @RequestParam(value = "pathroutefile", required = false) MultipartFile pathFile,
            @RequestParam(value = "pathroutefile4", required = false) MultipartFile zMap,
            @RequestParam(value = "pathroutefile5", required = false) MultipartFile iosMap,
            @RequestParam(value = "pathroutefile6", required = false) MultipartFile zPathFile,
            @RequestParam(value = "pathroutefile7", required = false) MultipartFile fMap,
            @RequestParam(value = "routefile", required = false) MultipartFile route,
            HttpServletRequest request, MapMngModel mapMngModel) throws SQLException
    {
        String ip = Util.getIpAddr(request);
        long updateTime = System.currentTimeMillis();
        mapMngModel.setUpdateTime(String.valueOf(updateTime));
        BigDecimal id = new BigDecimal(10000 * mapMngModel.getPlaceId());
        String nu = "";
        BigDecimal sid = (BigDecimal) mapMngModel.getFloorid();
        // 通过placeid查询出需要的商场的值
        String placeName = storeDao.placeByPlaceId(mapMngModel.getPlaceId());
        mapMngModel.setPlace(placeName);
        int valueid = sid.intValue();
        if (valueid > 0)
        {
            mapMngModel.setFloorNo(id.add(mapMngModel.getFloorid()));
        }
        else
        {
            mapMngModel.setFloorNo(id.add(
                    mapMngModel.getFloorid().multiply(new BigDecimal(-1))).add(
                    new BigDecimal(5000)));
        }

        if (!mapMngModel.getId().equals(nu))
        {
            LOG.info(ip+"updata mapData:floorNo="+mapMngModel.getFloorNo());
            String fileName = file.getOriginalFilename();
            String svgName = svg.getOriginalFilename();
            String routeName = route.getOriginalFilename();
            String pathfileName = pathFile.getOriginalFilename();
            String fMapName = fMap.getOriginalFilename();
            String zMapName = zMap.getOriginalFilename();
            String zPathFileName = zPathFile.getOriginalFilename();
            String  iosMapName = iosMap.getOriginalFilename();
            if (!fileName.equals(nu))
            {
                String path = request.getSession().getServletContext()
                        .getRealPath("/WEB-INF/upload");
                String ext = fileName.substring(fileName.lastIndexOf('.'));
                fileName = mapMngModel.getFloorNo() + "_"
                        + mapMngModel.getFloor() + ext;
                mapMngModel.setPath(fileName);
                Logger.getLogger(MapController.class).debug(path);
                File targetFile = new File(path, fileName);
                if (!targetFile.exists())
                {
                    targetFile.mkdirs();
                }
                // 修改
                try
                {
                    BufferedImage sourceImg = javax.imageio.ImageIO.read(file
                            .getInputStream());
                    file.transferTo(targetFile);

                    mapMngModel.setImgWidth(sourceImg.getWidth());
                    mapMngModel.setImgHeight(sourceImg.getHeight());
                }
                catch (Exception e)
                {
                    LOG.error(e);
                }
            }

            if (!svgName.equals(nu))
            {
                String path = request.getSession().getServletContext()
                        .getRealPath("/WEB-INF/upload");
                String ext = svgName.substring(svgName.lastIndexOf('.'));
                svgName = mapMngModel.getFloorNo() + "_"
                        + mapMngModel.getFloor() + ext;
                mapMngModel.setSvg(svgName);
                Logger.getLogger(MapController.class).debug(svgName);
                File targetFile = new File(path, svgName);
                if (!targetFile.exists())
                {
                    targetFile.mkdirs();
                }
                // 修改
                try
                {
                    svg.transferTo(targetFile);
                }
                catch (Exception e)
                {
                    LOG.error(e);
                }
            }
            if (!routeName.equals(nu))
            {
                String path = request.getSession().getServletContext()
                        .getRealPath("/WEB-INF/upload");
                String ext = routeName.substring(routeName.lastIndexOf('.'));
                routeName = mapMngModel.getFloorNo() + "_"
                        + mapMngModel.getFloor() + ext;
                mapMngModel.setRoute(routeName);
                Logger.getLogger(MapController.class).debug(svgName);
                File targetFile = new File(path, routeName);
                if (!targetFile.exists())
                {
                    targetFile.mkdirs();
                }
                // 修改
                try
                {
                    route.transferTo(targetFile);
                }
                catch (Exception e)
                {
                    LOG.error(e);
                }
            }
            
            if (!pathfileName.equals(nu))
            {
                String path = request.getSession().getServletContext()
                        .getRealPath("/WEB-INF/upload");
                String ext = pathfileName.substring(pathfileName.lastIndexOf('.'));
                pathfileName = mapMngModel.getUpdateTime() + "_"
                        + mapMngModel.getFloor() + ext;
                mapMngModel.setPathFile(pathfileName);
                Logger.getLogger(MapController.class).debug(pathfileName);
                File targetFile = new File(path, pathfileName);
                if (!targetFile.exists())
                {
                    targetFile.mkdirs();
                }
                // 修改
                try
                {
                    pathFile.transferTo(targetFile);
                }
                catch (Exception e)
                {
                    LOG.error(e);
                }
            }
            if (!zMapName.equals(nu))
            {
                String path = request.getSession().getServletContext()
                    .getRealPath("/WEB-INF/upload");
                String ext = zMapName.substring(zMapName.lastIndexOf('.'));
                zMapName =System.currentTimeMillis() + "_"
                    + mapMngModel.getFloor() + ext;
                mapMngModel.setzMap(zMapName);
                Logger.getLogger(MapController.class).debug(zMapName);
                File targetFile = new File(path, zMapName);
                if (!targetFile.exists())
                {
                    targetFile.mkdirs();
                }
                // 修改
                try
                {
                    zMap.transferTo(targetFile);
                }
                catch (Exception e)
                {
                    LOG.error(e);
                }
            }
            if (!iosMapName.equals(nu))
            {
                String path = request.getSession().getServletContext()
                    .getRealPath("/WEB-INF/upload");
                String ext = iosMapName.substring(iosMapName.lastIndexOf('.'));
                iosMapName =System.currentTimeMillis() + "_"
                    + mapMngModel.getFloor() + ext;
                mapMngModel.setzIosMap(iosMapName);
                LOG.debug(iosMapName);
                File targetFile = new File(path, iosMapName);
                if (!targetFile.exists())
                {
                    targetFile.mkdirs();
                }
                // 修改
                try
                {
                    iosMap.transferTo(targetFile);
                }
                catch (Exception e)
                {
                    LOG.error(e);
                }
            }
            if (!zPathFileName.equals(nu))
            {
                String path = request.getSession().getServletContext()
                    .getRealPath("/WEB-INF/upload");
                String ext = zPathFileName.substring(zPathFileName.lastIndexOf('.'));
                zPathFileName = System.currentTimeMillis() + "_"
                    + mapMngModel.getFloor() + ext;
                mapMngModel.setzMapPathfile(zPathFileName);
                LOG.debug(zPathFileName);
                File targetFile = new File(path, zPathFileName);
                if (!targetFile.exists())
                {
                    targetFile.mkdirs();
                }
                // 修改
                try
                {
                    zPathFile.transferTo(targetFile);
                }
                catch (Exception e)
                {
                    LOG.error(e);
                }
            }
            if (!fMapName.equals(nu))
            {
                String path = request.getSession().getServletContext()
                    .getRealPath("/WEB-INF/upload");
                String ext = fMapName.substring(fMapName.lastIndexOf('.'));
                fMapName = System.currentTimeMillis() + "_"
                    + mapMngModel.getFloor() + ext;
                mapMngModel.setfMap(fMapName);
                LOG.debug(fMapName);
                File targetFile = new File(path, fMapName);
                if (!targetFile.exists())
                {
                    targetFile.mkdirs();
                }
                // 修改
                try
                {
                    fMap.transferTo(targetFile);
                }
                catch (Exception e)
                {
                    LOG.error(e);
                }
            }
            
            //form bean转dao bean
            MapsModel mapModel = new MapsModel();
            //赋值
            try {
                BeanUtils.copyProperties(mapModel,mapMngModel);
                StoreModel store = new StoreModel();
                store.setId(mapMngModel.getPlaceId());
                store.setName(mapMngModel.getPlace());
                mapModel.setStore(store);
            } catch (Exception e) {
                LOG.error(e);;
            }
            dao.updateMap(mapModel);
            return "redirect:/home/showMapMng";
        }
        else
        {
            Logger.getLogger(MapController.class).debug(mapMngModel.getFloor());
            String path = request.getSession().getServletContext()
                    .getRealPath("/WEB-INF/upload");
            saveFile(mapMngModel, file, svg, path, nu, route,pathFile,zMap,fMap,zPathFile,iosMap);
            LOG.info(ip+"insert mapData:floorNo="+mapMngModel.getFloorNo());
            return "redirect:/home/showMapMng";
        }

    }

    private static void getFile(File targetFile1)
    {
        if (!targetFile1.exists())
        {
            targetFile1.mkdirs();
        }
    }

    private void saveFile(MapMngModel mapMngModel, MultipartFile file,
            MultipartFile svg, String path, String nu, MultipartFile route,MultipartFile pathFile,
            MultipartFile zMap,MultipartFile fMap,MultipartFile zPathFile,MultipartFile iosMap)
    {
        String fileName = file.getOriginalFilename();
        String svgName = svg.getOriginalFilename();
        String routeName = route.getOriginalFilename();
        String pathfileName = pathFile.getOriginalFilename();
        String fMapName = fMap.getOriginalFilename();
        String zMapName = zMap.getOriginalFilename();
        String zPathFileName = zPathFile.getOriginalFilename();
        String iosMapName = iosMap.getOriginalFilename();
        String ext = null;
        String ext1 = null;
        String ext2 = null;
        String ext3 = null;
        String ext4 = null;
        String ext5 = null;
        String ext6 = null;
        String ext7 = null;
        // 保存
        try
        {
           
            if (fileName != nu)
            {
                ext = fileName.substring(fileName.lastIndexOf('.'));
                fileName = mapMngModel.getFloorNo() + "_" + mapMngModel.getFloor()
                + ext;
                mapMngModel.setPath(fileName);
                File targetFile = new File(path, fileName);
                getFile(targetFile);
                BufferedImage sourceImg = javax.imageio.ImageIO.read(file
                    .getInputStream());
                mapMngModel.setImgWidth(sourceImg.getWidth());
                mapMngModel.setImgHeight(sourceImg.getHeight());
                file.transferTo(targetFile);
    
            }
            if (routeName != nu)
            {
                ext2 = routeName.substring(routeName.lastIndexOf('.'));
                routeName = mapMngModel.getFloorNo() + "_" + mapMngModel.getFloor()
                + ext2;
                mapMngModel.setRoute(routeName);
                File targetFile2 = new File(path, routeName);
                getFile(targetFile2);
                route.transferTo(targetFile2);
    
            }
            if (svgName != nu)
            {
                ext1 = svgName.substring(svgName.lastIndexOf('.'));
                svgName = mapMngModel.getFloorNo() + "_" + mapMngModel.getFloor()
                + ext1;
                mapMngModel.setSvg(svgName);
                File targetFile1 = new File(path, svgName);
                getFile(targetFile1);
                svg.transferTo(targetFile1);
    
            }
            if (pathfileName != nu)
            {
                ext3 = pathfileName.substring(pathfileName.lastIndexOf('.'));
                pathfileName = mapMngModel.getUpdateTime() + "_" + mapMngModel.getFloor()
                + ext3;
                mapMngModel.setPathFile(pathfileName);
                File targetFile3 = new File(path, pathfileName);
                getFile(targetFile3);
                pathFile.transferTo(targetFile3);
                
            }
            if (zMapName != nu)
            {
                ext4 = zMapName.substring(zMapName.lastIndexOf('.'));
                zMapName = System.currentTimeMillis() + "_" + mapMngModel.getFloor()
                + ext4;
                mapMngModel.setzMap(zMapName);
                File targetFile3 = new File(path, zMapName);
                getFile(targetFile3);
                zMap.transferTo(targetFile3);
                
            }
            if (fMapName != nu)
            {
                ext5 = fMapName.substring(fMapName.lastIndexOf('.'));
                fMapName = System.currentTimeMillis() + "_" + mapMngModel.getFloor()
                + ext5;
                mapMngModel.setfMap(fMapName);
                File targetFile3 = new File(path, fMapName);
                getFile(targetFile3);
                fMap.transferTo(targetFile3);
                
            }
            if (zPathFileName != nu)
            {
                ext6 = zPathFileName.substring(zPathFileName.lastIndexOf('.'));
                zPathFileName = System.currentTimeMillis() + "_" + mapMngModel.getFloor()
                + ext6;
                mapMngModel.setzMapPathfile(zPathFileName);
                File targetFile3 = new File(path, zPathFileName);
                getFile(targetFile3);
                zPathFile.transferTo(targetFile3);
                
            }
            if (iosMapName != nu)
            {
                ext7 = iosMapName.substring(iosMapName.lastIndexOf('.'));
                iosMapName = System.currentTimeMillis() + "_" + mapMngModel.getFloor()
                + ext7;
                mapMngModel.setzIosMap(iosMapName);
                File targetFile3 = new File(path, iosMapName);
                getFile(targetFile3);
                iosMap.transferTo(targetFile3);
                
            }

            //form bean转dao bean
            MapsModel mapModel = new MapsModel();
            //赋值
            try {
                BeanUtils.copyProperties(mapModel,mapMngModel);
                StoreModel store = new StoreModel();
                store.setId(mapMngModel.getPlaceId());
                store.setName(mapMngModel.getPlace());
                mapModel.setStore(store);
            } catch (Exception e) {
                LOG.error(e);;
            }
            dao.saveMapInfo(mapModel);

        }
        catch (Exception e)
        {
            LOG.error(e);
        }

    }

    @RequestMapping(value = "/api/check", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> check(
            @RequestParam("id") String id, @RequestParam("place") String place,
            @RequestParam("floor") String floor,
            @RequestParam("floorNo") String floorId)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        String nu = "";
        int floorid = Integer.parseInt(floorId);
        int floorNo;
        int placeId = Integer.parseInt(place);
        if (floorid > 0)
        {
            floorNo = 10000 * placeId + floorid;
        }
        else
        {
            floorNo = 10000 * placeId + 5000 - floorid;
        }

        if (id == nu)
        {
            return check1(place, floor, floorNo, modelMap);
        }
        else
        {
            return check2(id, place, floorNo, floor, modelMap);
        }
    }

    private Map<String, Object> check2(String id, String place, int floorNo,
            String floor, Map<String, Object> modelMap)
    {
        int i = dao.chekByFloorNo1(floorNo, id);
        if (i > 0)
        {
            modelMap.put("data", false);
            modelMap.put("same", "0");
            return modelMap;
        }
        int j = dao.checkByPlace1(place, floor, id);
        if (j > 0)
        {
            modelMap.put("data", false);
            modelMap.put("same", "1");
            return modelMap;
        }
        else
        {
            modelMap.put("data", true);
            return modelMap;
        }
    }

    private Map<String, Object> check1(String place, String floor, int floorNo,
            Map<String, Object> modelMap)
    {
        int i = dao.chekByFloorNo(floorNo);
        if (i > 0)
        {
            modelMap.put("data", false);
            modelMap.put("same", "0");
            return modelMap;
        }
        int j = dao.checkByPlace(place, floor);
        if (j > 0)
        {
            modelMap.put("data", false);
            modelMap.put("same", "1");
            return modelMap;
        }
        else
        {
            modelMap.put("data", true);
            return modelMap;
        }
    }

}
