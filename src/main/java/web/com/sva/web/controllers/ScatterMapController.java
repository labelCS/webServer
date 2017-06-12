/**   
 * @Title: ScatterMapController.java 
 * @Package com.sva.web.controllers 
 * @Description: 散点图逻辑
 * @Company:ICS
 * @author label  
 * @date 2016年7月21日 上午10:19:35 
 * @version V1.0 
 */
package com.sva.web.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.common.ConvertUtil;
import com.sva.common.conf.Params;
import com.sva.dao.LocationDao;
import com.sva.dao.PushMsgDao;

/**
 * <p>Title:ScatterMapController</p>
 * <p>Description:散点图控制器</p>
 * <p>Company: ICS</p>
 * @author label
 * @date 2016年7月21日 上午10:19:35
 */
@Controller
@RequestMapping(value = "/scattermap")
public class ScatterMapController {
    
    /**
     * @Fields log
     */
    private static final Logger LOG = Logger.getLogger(ScatterMapController.class);
    
    /**
     * @Fields locationDao
     */
    @Autowired
    private LocationDao locationDao;
    
    /**
     * @Fields PushMsgDao
     */
    @Autowired
    private PushMsgDao pushMsgDao;
    
    /** 
     * @Title: getChartData 
     * @Description: 获取散点图数据
     * @param model
     * @param floorNo 楼层号
     * @param times 时间段
     * @return Map<String,Object>   
     * @throws 
     */
    @RequestMapping(value = "/api/getData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getChartData(String floorNo,int times)
    {
        LOG.info("获取散点图数据:"+floorNo+"-"+times);
        long time = System.currentTimeMillis() - times * 1000;
        String tableName = Params.LOCATION + ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD);
        // 获取数据
        List<Map<String,Object>> list = locationDao.queryScatterMapData(floorNo, time, tableName);
        
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("error", false);
        modelMap.put("data", list);

        return modelMap;
    }
    
    /** 
     * @Title: sendMessage 
     * @Description: 推送消息入库
     * @param model
     * @param message 消息内容
     * @return Map<String,Object>   
     * @throws 
     */
    @RequestMapping(value = "/api/sendMessage",method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> sendMessage(String message,String userid){
        LOG.info("推送消息入库:"+message+"-"+userid);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        try{
            // 推送消息入库
            pushMsgDao.saveMessage(userid, message);
        }catch(Exception e){
            LOG.error(e);
            modelMap.put("error", e.getMessage());
        }
        
        modelMap.put("data", true);

        return modelMap;
    }
}
