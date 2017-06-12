/**   
 * @Title: MobileMapController.java 
 * @Package com.sva.web.controllers.mobile 
 * @Description: 移动端HTML5处理逻辑
 * @author labelCS   
 * @date 2016年11月10日 下午6:57:56 
 * @version V1.0   
 */
package com.sva.web.controllers.mobile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sva.common.conf.GlobalConf;
import com.sva.common.conf.Params;
import com.sva.dao.AreaDao;
import com.sva.dao.MapsDao;
import com.sva.dao.StoreDao;
import com.sva.model.AreaModel;
import com.sva.model.MapsModel;
import com.sva.model.StoreModel;
import com.sva.service.NavigateService;
import com.sva.service.XmlService;
import com.sva.web.controllers.StoreController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * @ClassName: MobileMapController 
 * @Description: 移动端HTML5处理逻辑
 * @author labelCS 
 * @date 2016年11月10日 下午6:57:56 
 *  
 */
@Controller
@RequestMapping(value = "/mobile/store")
public class MobileMapController {
    /** 
     * @Fields dao : 商场数据库操作句柄
     */ 
    @Autowired
    private StoreDao dao;
    
    /** 
     * @Fields daoMaps : 地图数据库操作句柄
     */ 
    @Autowired
    private MapsDao daoMaps;
    
    /** 
     * @Fields daoArea : 区域数据库操作句柄
     */ 
    @Autowired
    private AreaDao daoArea;
    
    @Autowired
    private XmlService xmlService;
    
    /** 
     * @Fields naviSerivce : 导航路径生成逻辑
     */ 
    @Autowired
    private NavigateService naviSerivce;
    
    /** 
     * @Fields LOG : 日志处理模块
     */ 
    private static final Logger LOG = Logger.getLogger(StoreController.class);
    
    /** 
     * @Title: getTableData 
     * @Description: 获取所有商场数据
     * @return 
     */
    @RequestMapping(value = "/api/getData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData()
    {
        LOG.info("MobileMapController:getTableData");
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        // 查询所有商场
        List<StoreModel> resultList = dao.doStores();
        
        modelMap.put("error", null);
        modelMap.put("data", resultList);

        return modelMap;
    }
    
    /** 
     * @Title: getInfo 
     * @Description: 获取指定商场的地图和区域信息
     * @param placeId
     * @return 
     */
    @RequestMapping(value = "/api/getInfoByStore", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getInfo(@RequestParam("placeId") String placeId)
    {
        // 返回值
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 指定商场的地图信息
        List<MapsModel> mapInfoList = daoMaps.getFloors(placeId);
        // 指定商场的区域信息
        List<AreaModel> areaInfoList = daoArea.doqueryAll(Integer.parseInt(placeId));
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("maps", mapInfoList);
        data.put("areas", areaInfoList);

        modelMap.put(Params.RETURN_KEY_ERROR, null);
        modelMap.put(Params.RETURN_KEY_DATA, data);

        return modelMap;
    }
    
    /** 
     * @Title: getNavigateLine 
     * @Description: 获取导航路径
     * @param param 参数，包含起始和终点
     * @return 
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/api/getNavigateLine", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getNavigateLine(HttpServletRequest request, @RequestBody Map<String, Object> param){
        LOG.info("MobileMapController:getNavigateLine");
        double xStart = Double.parseDouble(param.get("x1").toString());
        double yStart = Double.parseDouble(param.get("y1").toString());
        double xEnd = Double.parseDouble(param.get("x2").toString());
        double yEnd = Double.parseDouble(param.get("y2").toString());
        String fileName = param.get("fileName").toString();
        // 返回值
        Map<String, Object> result = new HashMap<String, Object>();
        
        // 读取路径规划配置文件
        String pathFileName = request.getSession().getServletContext().getRealPath(File.separator + "WEB-INF"
                + File.separator+"upload") + File.separator + "pathFile" + File.separator + fileName;
        Map<String, Object> xmlData = xmlService.readStandardPathFile(pathFileName);
        if(xmlData.get("error") != null){
            result.put("error", xmlData.get("error"));
            return result;
        }
        
        // 确定导航的开始点和结束点
        List<Map<String, Object>> data = (List<Map<String, Object>>) xmlData.get("point");
        Map<String, Object> fromPoint = getClosestPoint(xStart, yStart, data);
        Map<String, Object> toPoint = getClosestPoint(xEnd, yEnd, data);
        // 所有路径对
        List<Map<String, Object>> lines = (List<Map<String, Object>>) xmlData.get("line");
        // 起点ID
        int startId = Integer.parseInt(fromPoint.get("id").toString());
        // 终点ID
        int endId = Integer.parseInt(toPoint.get("id").toString());

        // 寻路
        Map<String, Object> naviData = GlobalConf.getNavigateDataByFileName(fileName);
        // 如果没有数据，则从新生成路径导航数据
        if(naviData == null){
            JSONArray pointArray = JSONArray.fromObject(data);
            JSONArray dataArray = JSONArray.fromObject(lines);
            // 格式转换
            JSONArray newDataArray = new JSONArray();
            for(int i = 0; i<dataArray.size(); i++){
                JSONArray temp = new JSONArray();
                JSONObject t = dataArray.getJSONObject(i);
                temp.add(t.get("from"));
                temp.add(t.get("to"));
                newDataArray.add(temp);
            }
            naviSerivce.calcNavigatePath(pointArray, newDataArray, fileName);
            naviData = GlobalConf.getNavigateDataByFileName(fileName);
        }
        Map<Integer,Map<Integer,List<Integer>>> naviDetail = (Map<Integer, Map<Integer, List<Integer>>>) naviData.get("navigate");
        Map<Integer,Object> pointData = (Map<Integer, Object>) naviData.get("point");
        List<Integer> routePath = naviDetail.get(startId).get(endId);
        // 找到id对应的点的坐标信息
        List<Map<String, Object>> pathData = new ArrayList<Map<String, Object>>();
        for(Integer i : routePath){
            Map<String, Object> p = (Map<String, Object>) pointData.get(i);
            pathData.add(p);
        }
        pathData.add((Map<String, Object>)pointData.get(startId));
        result.put("data", pathData);
        return result;
    }
    
    /** 
     * @Title: getClosestPoint 
     * @Description: 从data中找出距离给定点最近的坐标点
     * @param x 
     * @param y
     * @param data
     * @return 
     */
    private Map<String, Object> getClosestPoint(double x, double y, List<Map<String, Object>> data){
        double distance = Double.MAX_VALUE;
        Map<String, Object> closest = null;
        // 遍历data，找出最近的点
        for(Map<String, Object> m : data){
            double xTemp = Double.parseDouble(m.get("x").toString());
            double yTemp = Double.parseDouble(m.get("y").toString());
            double distanceTemp = Math.sqrt(Math.pow((x - xTemp),2) + Math.pow((y - yTemp),2));
            if(distanceTemp < distance){
                distance = distanceTemp;
                closest = m;
            }
        }
        return closest;
    }

}
