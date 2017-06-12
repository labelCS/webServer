/**   
 * @Title: ShowParamsController.java 
 * @Package com.sva.web.controllers 
 * @Description: 局点展示相关控制器
 * @author labelCS   
 * @date 2016年12月19日 下午2:57:20 
 * @version V1.0   
 */
package com.sva.web.controllers;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.common.ConvertUtil;
import com.sva.common.Util;
import com.sva.common.conf.Params;
import com.sva.dao.AreaDao;
import com.sva.dao.LocationDao;
import com.sva.dao.ShowParamsDao;
import com.sva.model.AreaModel;
import com.sva.model.ShowParamsModel;

/** 
 * @ClassName: ShowParamsController 
 * @Description: 局点展示相关控制器
 * @author labelCS 
 * @date 2016年12月19日 下午2:57:20 
 *  
 */
@Controller
@RequestMapping(value = "/showParams")
public class ShowParamsController {
    
    /** 
     * @Fields dao : dao句柄
     */ 
    @Autowired
    private ShowParamsDao dao;
    
    /** 
     * @Fields daoArea : 区域dao句柄
     */
    @Autowired
    private AreaDao daoArea;
    
    /** 
     * @Fields daoLocation : 位置dao句柄
     */
    @Autowired
    private LocationDao daoLocation;

    /** 
     * @Fields LOG : 日志句柄
     */ 
    private static final Logger LOG = Logger.getLogger(ShowParamsController.class);
    
    private static final int MAX_HEATMAP_COUNT = 8;
    /** 
     * @Title: getTableData 
     * @Description: 获取所有的局点配置信息
     * @return 
     */
    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData()
    {
        LOG.info("ShowParamsController:getTableData");

        List<ShowParamsModel> resultList = dao.getTableData();
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put(Params.RETURN_KEY_ERROR, null);
        modelMap.put(Params.RETURN_KEY_DATA, resultList);

        return modelMap;
    }
    
    /** 
     * @Title: deleteData 
     * @Description: 删除指定的局点配置
     * @param request
     * @param id
     * @return 
     */
    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteData(HttpServletRequest request,@RequestParam("id") String id)
    {
        String ip = Util.getIpAddr(request);
        LOG.info("ShowParamsController:deleteData::" + id+" ip:"+ip);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        
        int result = dao.deleteData(id);
        
        modelMap.put(Params.RETURN_KEY_DATA, result);
        
        return modelMap;
    }
    
    /** 
     * @Title: saveMsgData 
     * @Description: 添加新的局点配置
     * @param request
     * @param msgMngModel
     * @return 
     */
    @RequestMapping(value = "/api/saveData")
    @ResponseBody
    public Map<String, Object> saveMsgData(HttpServletRequest request, ShowParamsModel model){
        String ip = Util.getIpAddr(request);
        LOG.info("ShowParamsController:saveMsgData:: ip:"+ip);
        
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        // 设置更新时间为当前时间
        model.setUpdateTime(new Date());
        
        int result;
        // 判断是修改还是新增
        if(model.getId() != null && !model.getId().isEmpty()){
            // 修改
            result = dao.updateData(model);
        }else{
            // 新增
            result = dao.addData(model);
        }
        
        modelMap.put(Params.RETURN_KEY_DATA, result);
        
        return modelMap;
    }
    
    /** 
     * @Title: getConfigByShowId 
     * @Description: 获取指定配置
     * @param showId
     * @return 
     */
    @RequestMapping(value = "/getConfigByShowId", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getConfigByShowId(String showId)
    {
        LOG.info("ShowParamsController:getConfigByShowId--showId:" + showId);

        ShowParamsModel result = dao.getParamsByShowId(showId);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put(Params.RETURN_KEY_ERROR, null);
        modelMap.put(Params.RETURN_KEY_DATA, result);

        return modelMap;
    }
    
    /** 
     * @Title: getStatisticData 
     * @Description: 获取局点演示的统计数据
     * @param showId
     * @return 
     */
    @RequestMapping(value = "/getStatisticData")
    @ResponseBody
    public Map<String, Object> getStatisticData(String showId){
        // 返回值
        Map<String, Object> result = new HashMap<String, Object>();
        // 获取指定局点的配置信息
        ShowParamsModel paramData = dao.getParamsByShowId(showId);
        if(paramData == null || paramData.getId() == null){
            result.put(Params.RETURN_KEY_ERROR, "The params hasn't been set yet!");
            return result;
        }
        
        // 统计开始时间
        String startTime = paramData.getStartStatisticTime();
        // 热力图统计时长
        String periodHeatmap = paramData.getPeriodHeatmap();
        // 热力图统计开始时间戳
        long nowTime = System.currentTimeMillis() - (Integer.parseInt(periodHeatmap)+1) * 60 * 1000;
        // 模拟参数
        double fakeRatio = Double.parseDouble(paramData.getFakeRatio());
        
        // 遍历参数热力图,当前最大支持八张热力图
        for(int i = 1; i < MAX_HEATMAP_COUNT + 1; i++){
            // 通过反射获取mapId
            String getMethodName = "getMapId" + i;
            String mapId = "";
            try
            {  
                Class<?> tCls = Class.forName("com.sva.model.ShowParamsModel");
                Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                mapId = (String)getMethod.invoke(paramData, new Object[]{});
            }catch(Exception e){
                LOG.error(e);
                result.put(Params.RETURN_KEY_ERROR, e.getMessage());
                return result;
            }
            // 如果没有mapId，终止循环
            if(mapId.isEmpty()){
                break;
            }
            
            // 获取区域信息
            List<AreaModel> areaList = daoArea.selectAeareByMapId(mapId);
            // 位置表名
            String tableName = Params.LOCATION + ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD);
            // 当天日期
            String visitDay = ConvertUtil.dateFormat(System.currentTimeMillis(), "yyyy-MM-dd");
            // 开始时间戳，默认当天0点
            if (startTime == null)
            {
                startTime = visitDay + " 00:00:00";
            }else{
                startTime = visitDay + " " + startTime;
            }
            
            // 存放所有区域的统计信息
            List<Object> areaData = new ArrayList<Object>();
            // 总驻留时长
            long allStayTime = 0;
            for (int j = 0; j < areaList.size(); j++)
            {
                Map<String, Object> areaStatistic =  getAreaDate(areaList.get(j),visitDay, nowTime, fakeRatio, tableName);
                if (areaStatistic.size() != 0)
                {
                    allStayTime = Long.parseLong(areaStatistic.get("allTime").toString())+ allStayTime;
                    areaData.add(areaStatistic);
                }
            }
            
            int allLeiji = daoLocation.queryHeatmap6(mapId, tableName).size();
            // 累积总人数
            double allUser = Math.ceil(allLeiji * fakeRatio);
            // 当前总人数
            int allUsers = daoLocation.queryHeatmap5(mapId, nowTime, tableName).size();
            
            DecimalFormat df = new DecimalFormat("######0.00");   
            // 总平均驻留时长
            String avgAllTime = allUser == 0 ? "0.00" : df.format(allStayTime/60000.0/allUser);
            if (Double.parseDouble(avgAllTime)>=120)
            {
                avgAllTime = "120.23";
            }
            
            result.put("allTime"+i, avgAllTime);
            result.put("User"+i, Math.ceil(allUsers * fakeRatio));
            result.put("allUser"+i,allUser);
            result.put("item"+i, areaData);
            
        }
        result.put("coefficient", fakeRatio);
        return result;
    }
    
    /** 
     * @Title: getAreaDate 
     * @Description: 获取指定区域的相关统计信息
     * @param areaModel 区域信息
     * @param visitDay 制定日期
     * @param nowTime 制定时间
     * @param fakeRatio 模拟系数 
     * @param tableName 表名
     * @return 
     */
    private Map<String, Object> getAreaDate(AreaModel areaModel, String visitDay, long nowTime, 
            double fakeRatio, String tableName)
    {
        // 当前总人数
        int size = daoArea.getBaShowVisitUser(areaModel,tableName,String.valueOf(nowTime));
        // 实时累计总驻留人数
        int allSize1 = daoArea.getAllArea(areaModel,tableName,nowTime);
        // 区域预处理总驻留人数
        int allSize = 0;
        // 总驻留时长
        long allTime = 0;
        // 平均驻留时长
        String avgTime = null;
        // 获取指定区域、指定日期的驻留统计
        Map<String, Object> quyu = getAverageTimeByAreaId(areaModel.getId(), visitDay);
        // 
        if (!quyu.isEmpty())
        {
            allSize = Integer.parseInt(quyu.get("count").toString());
            allTime = Long.parseLong(quyu.get("timePeriod").toString());
            avgTime = getMinute(
                    (Long.parseLong(quyu.get("timePeriod").toString())),
                    allSize);
        }
        // 数据修正，当驻留时间过长的时候，改为指定值
        if (Double.parseDouble(avgTime)>120)
        {
            avgTime = "120.21";
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", areaModel.getAreaName());
        map.put("current", Math.ceil(size * fakeRatio));
        map.put("cumulative", Math.ceil(allSize1 * fakeRatio));
        map.put("average", avgTime);
        map.put("allTime", allTime);

        return map;
    }
    
    /** 
     * @Title: getAverageTimeByAreaId 
     * @Description: 获取指定区域、指定日期的驻留统计
     * @param areaId 区域id
     * @param visitDay 日期
     * @return 
     */
    private Map<String, Object> getAverageTimeByAreaId(String areaId, String visitDay)
    {
        // 获取指定区域、指定日期的驻留统计
        List<Map<String, Object>> res = daoArea.getAverageTimeByAreaId(areaId, visitDay);
        // 驻留时长
        long allTime = 0;
        // 人流数
        int count = 0;
        for (int i = 0; i < res.size(); i++)
        {
            allTime = Long.parseLong(res.get(i).get("allTime").toString());
            count = Integer.parseInt(res.get(i).get("number").toString());
        }
        // 返回值
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("timePeriod", allTime);
        result.put("count", count);
        return result;
    }
    
    /** 
     * @Title: getMinute 
     * @Description: 获取平均驻留时长
     * @param time 驻留总时长
     * @param size 人数
     * @return 
     */
    private String getMinute(long time, int size)
    {
        // 异常情况，直接返回0
        if (size == 0 || time == 0)
        {
            return "0";
        }
        else
        {
            // 换算成分钟
            float b = (float) (time / 1000) / 60;
            // 平均驻留时长
            float averageTime = b / size;
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(averageTime);
        }

    }
}
