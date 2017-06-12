package com.sva.web.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sva.common.HttpUtil;
import com.sva.common.Util;
import com.sva.common.conf.Params;
import com.sva.dao.AreaDao;
import com.sva.dao.LocationDao;
import com.sva.dao.MapsDao;
import com.sva.dao.SvaDao;
import com.sva.model.AreaModel;
import com.sva.model.CategoryModel;
import com.sva.model.MapsModel;
import com.sva.model.SvaModel;

@Controller
@RequestMapping(value = "/input")
public class AreaController
{

    @Autowired
    private AreaDao dao;

    @Autowired
    private SvaDao svaDao;

    @Autowired
    private MapsDao mapDao;

    private static final Logger LOG = Logger.getLogger(LocationDao.class);

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getInputData(HttpServletRequest request,
            Model model) throws SQLException
    {
        Collection<AreaModel> resultList = new ArrayList<AreaModel>(10);
        Collection<AreaModel> store;
        Object userName = request.getSession().getAttribute("username");
        @SuppressWarnings("unchecked")
        List<String> storeides = (List<String>) request.getSession()
                .getAttribute("storeides");
        if (("admin").equals(userName))
        {

            resultList = dao.doquery();
        }
        else if (!storeides.isEmpty())
        {
            String storeid = storeides.get(0);
            String[] stores = storeid.split(",");
            for (int i = 0; i < stores.length; i++)
            {
                store = dao.doqueryAll(Integer.parseInt(stores[i]));
                if (store != null && !store.isEmpty())
                {
                    resultList.addAll(store);
                }
            }
        }
        
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put(Params.RETURN_KEY_ERROR, null);
        modelMap.put(Params.RETURN_KEY_DATA, resultList);

        return modelMap;
    }
    
    @RequestMapping(value = "/api/getTableDataById", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableDataById(HttpServletRequest request,
            @RequestParam("placeId") String placeId) throws SQLException
    {
        Collection<AreaModel> resultList = new ArrayList<AreaModel>(10);
        Collection<AreaModel> store;
        Object userName = request.getSession().getAttribute("username");
        @SuppressWarnings("unchecked")
        List<String> storeides = (List<String>) request.getSession()
                .getAttribute("storeides");
        if (("admin").equals(userName))
        {

            resultList = dao.doqueryAll(Integer.parseInt(placeId));
        }
        else if (storeides != null && !storeides.isEmpty())
        {
            String storeid = storeides.get(0);
            String[] stores = storeid.split(",");
            for (int i = 0; i < stores.length; i++)
            {
                store = dao.doqueryAll(Integer.parseInt(stores[i]));
                if (store != null && !store.isEmpty())
                {
                    resultList.addAll(store);
                }
            }
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put(Params.RETURN_KEY_ERROR, null);
        modelMap.put(Params.RETURN_KEY_DATA, resultList);

        return modelMap;
    }

    @RequestMapping(value = "/api/saveData")
    public String saveInputData(HttpServletRequest request, AreaModel inputModel)
    {
        // 保存
        String path = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload");
        String nu = "";
        if (inputModel.getId().equals(nu))
        {

            LOG.debug(path);

            // 保存
            try
            {
                String ip = Util.getIpAddr(request);
                LOG.info(ip+"insert areaData:AreaName="+inputModel.getAreaName());
                dao.saveAreaInfo(inputModel);
            }
            catch (Exception e)
            {
                LOG.error(e);
            }

            return "redirect:/home/showInputMng";

        }
        else
        {
            String ip = Util.getIpAddr(request);
            LOG.info(ip+"update areaData:AreaName="+inputModel.getAreaName());
            dao.updateAreaInfo(inputModel);

            return "redirect:/home/showInputMng";
        }

    }

    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteInputData(HttpServletRequest request,
            @RequestParam("xSpot") String xSpot,
            @RequestParam("ySpot") String ySpot,
            @RequestParam("x1Spot") String x1Spot,
            @RequestParam("y1Spot") String y1Spot,
            @RequestParam("floorNo") String floorNo,
            @RequestParam("categoryId") String categoryId)
    {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = 0;
        try
        {
            String ip = Util.getIpAddr(request);
            LOG.info(ip+"delete areaData:floorNo="+floorNo);
            AreaModel area = new AreaModel();
            area.setxSpot(new BigDecimal(xSpot));
            area.setX1Spot(new BigDecimal(x1Spot));
            area.setySpot(new BigDecimal(ySpot));
            area.setY1Spot(new BigDecimal(y1Spot));
            MapsModel map = new MapsModel();
            map.setFloorNo(new BigDecimal(floorNo));
            area.setMaps(map);
            CategoryModel category = new CategoryModel();
            category.setId(Integer.parseInt(categoryId));
            area.setCategory(category);
            result = dao.deleteArea(area);
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        if (result > 0)
        {
            modelMap.put(Params.RETURN_KEY_ERROR, null);
        }
        else
        {
            modelMap.put(Params.RETURN_KEY_ERROR, true);
        }

        return modelMap;
    }

    @RequestMapping(value = "/api/getArea", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getArea(@RequestParam("zSel") String zSel)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        List<AreaModel> areaes = new ArrayList<AreaModel>(10);
        try
        {
            areaes = dao.selectArea(zSel);
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        modelMap.put(Params.RETURN_KEY_ERROR, null);
        modelMap.put(Params.RETURN_KEY_DATA, areaes);
        return modelMap;
    }

    @RequestMapping(value = "/api/disableData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> disableData(@RequestParam("areaId") String areaId)
    {
        LOG.info("Params[areaId]:" + areaId);
        List<AreaModel> areaList = dao.getAreaByAreaId(areaId);
        if (areaList == null || areaList.isEmpty())
        {
            return null;
        }
        AreaModel area = areaList.get(0);

        Collection<SvaModel> svaList = svaDao.queryByStoreId2(String
                .valueOf(area.getStore().getId()));
        String token = null;
        HttpUtil capi = null;
        boolean result = true;
        String jsonStr = null;
        String charset = null;
        String content = null;
        String url = null;
        try
        {
            for (SvaModel sva : svaList)
            {

                url = "https://" + sva.getIp() + ':' + sva.getTokenPort()
                        + "/v3/auth/tokens";
                content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                        + sva.getUsername()
                        + "\",\"password\": \""
                        + sva.getPassword() + "\"}}}}}";
                charset = "UTF-8";
                LOG.debug("from ip:" + sva.getIp() + ",getToken url:" + url);
                capi = new HttpUtil();

                token = capi.httpsPost(url, content, charset);
                url = "https://" + sva.getIp() + ':' + sva.getTokenPort()
                        + "/enabler/zonedef/json/v1.0";
                if (area.getZoneId() < 0)
                {
                    LOG.debug("area Id:" + area.getId() + ",zoneid is null");
                }
                else
                {
                    content = "{\"appid\":\"" + sva.getUsername()
                            + "\",\"zoneid\":" + area.getZoneId() + "}";
                    LOG.debug("zonedef ip:" + sva.getIp()
                            + ",unsubscription url:" + url + " content:"
                            + content);
                    jsonStr = capi.subscription(url, content, token, "DELETE");
                    LOG.debug("area Id:" + area.getId()
                            + ",Zone definition unSubscription respone:"
                            + jsonStr);
                }

                dao.updateZoneIdToNull(area.getId());
                dao.updateStatus(areaId);

                // 电子围栏订阅
                url = "https://" + sva.getIp() + ':' + sva.getTokenPort()
                        + "/enabler/catalog/geofencingcancel/json/v1.0";
                content = "{\"appid\":\"" + sva.getUsername() + "\"}";
                jsonStr = capi.subscription(url, content, token, "DELETE");
                LOG.debug("area Id:" + area.getId()
                        + ",geofencing unSubscription respone:" + jsonStr);
            }
        }
        catch (KeyManagementException e)
        {
            result = false;
            LOG.error("KeyManagementException.", e);
        }
        catch (NoSuchAlgorithmException e)
        {
            result = false;
            LOG.error("NoSuchAlgorithmException.", e);
        }
        catch (IOException e)
        {
            result = false;
            LOG.error("IOException.", e);
        }
        catch (Exception e)
        {
            result = false;
            LOG.error("Exception.", e);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put(Params.RETURN_KEY_ERROR, null);
        modelMap.put("result", result);
        return modelMap;
    }

    @RequestMapping(value = "/api/enableData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> enableData(@RequestParam("areaId") String areaId)
    {
        LOG.info("Params[areaId]:" + areaId);
        List<AreaModel> areaList = dao.getAreaByAreaId(areaId);
        if (areaList == null || areaList.isEmpty())
        {
            return null;
        }
        AreaModel area = areaList.get(0);

        Collection<MapsModel> mapList = mapDao.getMapDetail(String.valueOf(area.getMaps().getFloorNo()));
        String mapId = "";
        for (MapsModel map : mapList)
        {
            mapId = String.valueOf(map.getMapId());
            break;
        }

        Collection<SvaModel> svaList = svaDao.queryByStoreId2(String
                .valueOf(area.getStore().getId()));
        String token = null;
        HttpUtil capi = null;
        boolean result = true;
        String jsonStr = null;
        String charset = null;
        String content = null;
        String url = null;
        Double x1 = area.getxSpot().doubleValue() * 10;
        Double y1 = area.getySpot().doubleValue() * 10;
        Double x2 = area.getX1Spot().doubleValue() * 10;
        Double y2 = area.getY1Spot().doubleValue() * 10;
        try
        {
            for (SvaModel sva : svaList)
            {

                url = "https://" + sva.getIp() + ':' + sva.getTokenPort()
                        + "/v3/auth/tokens";
                content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                        + sva.getUsername()
                        + "\",\"password\": \""
                        + sva.getPassword() + "\"}}}}}";
                charset = "UTF-8";
                LOG.debug("from ip:" + sva.getIp() + ",getToken url:" + url);
                capi = new HttpUtil();

                token = capi.httpsPost(url, content, charset);
                url = "https://" + sva.getIp() + ':' + sva.getTokenPort()
                        + "/enabler/zonedef/json/v1.0";
                if (area.getZoneId() > 0)
                {
                    content = "{\"appId\":\"" + sva.getUsername()
                            + "\",\"zone\":{" + "\"mapid\":" + mapId
                            + ",\"zoneid\":" + area.getZoneId()
                            + ",\"zonetype\":\"rectangle\",\"point\":[{\"x\":"
                            + x1.intValue() + ",\"y\":" + y1.intValue()
                            + "},{\"x\":" + x2.intValue() + ",\"y\":"
                            + y2.intValue() + "}]}" + "}";

                    LOG.debug("zonedef ip:" + sva.getIp()
                            + ",subscription url:" + url + " content:"
                            + content);
                }
                else
                {
                    content = "{\"appId\":\"" + sva.getUsername()
                            + "\",\"zone\":{" + "\"mapid\":" + mapId
                            + ",\"zonetype\":\"rectangle\",\"point\":[{\"x\":"
                            + x1.intValue() + ",\"y\":" + y2.intValue()
                            + "},{\"x\":" + x2.intValue() + ",\"y\":"
                            + y1.intValue() + "}]}" + "}";
                    LOG.debug("zonedef ip:" + sva.getIp()
                            + ",subscription url:" + url + " content:"
                            + content);
                }
                jsonStr = capi.subscription(url, content, token, "POST");
                JSONObject jsonObj = JSONObject.fromObject(jsonStr);
                LOG.debug("area Id:" + area.getId()
                        + ",Zone definition respone:" + jsonStr);
                String zoneId = "";
                if (jsonObj.containsKey("ADD ZONE"))
                {
                    JSONArray list = jsonObj.getJSONArray("ADD ZONE");
                    JSONObject object = (JSONObject) list.get(0);
                    zoneId = object.getString("ZONEID");
                }
                else if (jsonObj.containsKey("\u589e\u52a0\u533a\u57df"))
                {
                    JSONArray list = jsonObj
                            .getJSONArray("\u589e\u52a0\u533a\u57df");
                    JSONObject object = (JSONObject) list.get(0);
                    zoneId = object.getString("\u533a\u57dfID");
                }
                else if (jsonObj.containsKey("zone"))
                {
                    JSONArray list = jsonObj.getJSONArray("zone");
                    JSONObject object = (JSONObject) list.get(0);
                    zoneId = object.getString("zoneid");
                }
                dao.updateZoneId(area.getId(), zoneId);
                dao.updateStatus1(areaId);

                // 电子围栏订阅
                url = "https://" + sva.getIp() + ':' + sva.getTokenPort()
                        + "/enabler/catalog/geofencing/json/v1.0";
                content = "{\"appid\":\"" + sva.getUsername() + "\"}";
                jsonStr = capi.subscription(url, content, token, "POST");
                LOG.debug("area Id:" + area.getId()
                        + ",geofencing Subscription respone:" + jsonStr);
            }
        }
        catch (KeyManagementException e)
        {
            result = false;
            LOG.error("KeyManagementException.", e);
        }
        catch (NoSuchAlgorithmException e)
        {
            result = false;
            LOG.error("NoSuchAlgorithmException.", e);
        }
        catch (IOException e)
        {
            result = false;
            LOG.error("IOException.", e);
        }
        catch (Exception e)
        {
            result = false;
            LOG.error("Exception.", e);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put(Params.RETURN_KEY_ERROR, null);
        modelMap.put("result", result);
        return modelMap;
    }

    
    @RequestMapping(value = "/api/getTableDataByFloorNo", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableDataByFloorNo(HttpServletRequest request,
            @RequestParam("floorNo") String floorNo) throws SQLException
    {
        Collection<AreaModel> resultList = new ArrayList<AreaModel>(10);
        Collection<AreaModel> store;
        Object userName = request.getSession().getAttribute("username");
        @SuppressWarnings("unchecked")
        List<String> storeides = (List<String>) request.getSession()
                .getAttribute("storeides");
        if (("admin").equals(userName))
        {

            resultList = dao.getAreaByFloorNo(Integer.parseInt(floorNo));
        }
        else if (storeides != null && !storeides.isEmpty())
        {
            String storeid = storeides.get(0);
            String[] stores = storeid.split(",");
            for (int i = 0; i < stores.length; i++)
            {
                store = dao.doqueryAll(Integer.parseInt(stores[i]));
                if (store != null && !store.isEmpty())
                {
                    resultList.addAll(store);
                }
            }
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put(Params.RETURN_KEY_ERROR, null);
        modelMap.put(Params.RETURN_KEY_DATA, resultList);

        return modelMap;
    }
}
