package com.sva.web.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.sva.common.ConvertUtil;
import com.sva.common.conf.GlobalConf;
import com.sva.common.conf.Params;
import com.sva.dao.ApLocationDao;
import com.sva.model.ApLocationModel;
import com.sva.model.LocationModel;

/** 
 * @ClassName: ApLocationController 
 * @Description: AP级定位controller
 * @author labelCS 
 * @date 2016年12月7日 下午2:21:05 
 *  
 */
@Controller
@RequestMapping(value = "/ap")
public class ApLocationController
{
    private static final Logger LOG = Logger.getLogger(ApLocationController.class);

    @Autowired
    private ApLocationDao dao;

    /** 
     * @Title: getUserMac 
     * @Description: 获取用户mac列表
     * @return 
     */
    @RequestMapping(value = "/api/getUserMac", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getUserMac()
    {
        // 查询截至时间
        long time = System.currentTimeMillis() - 10 * 1000;
        // 表名
        String tableName = Params.LOCATION + ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD);
        
        List<String> macList = dao.getUserMac(tableName, time);
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", macList);
        return result;
    }

    /** 
     * @Title: getApLocation 
     * @Description: 获取位置信息
     * @return 
     */
    @RequestMapping(value = "/api/getApLocation", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getApLocation(String userId)
    {
        LOG.debug("用户ID:"+ userId);
        // 返回值
        Map<String, Object> result = new HashMap<String, Object>();
        // 查询截至时间
        long time = System.currentTimeMillis() - 10 * 1000;
        // 表名
        String tableName = Params.LOCATION + ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD);
        // 原始数据
        List<LocationModel> macList = dao.getUserInfo(tableName, userId, time);
        if(!macList.isEmpty()){
            // 数据转换,计算用户的gps位置
            List<Map<String, Object>> data = calcUserGpsLocation(macList);
            result.put("data", data);
        }else{
            result.put("error", "User data is not available!");
        }
        
        return result;
    }
    
    /**   
     * @Title: saveData   
     * @Description: ap位置映射关系文件上传入库
     * @param file
     * @param request
     * @return：String       
     * @throws   
     */ 
    @RequestMapping(value = "/api/saveData")
    public String saveData(@RequestParam("file") MultipartFile file, HttpServletRequest request)
    {
        try{
            // 获取文件流
            InputStream is=file.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String temp;
            //一行一行的读取并写入数据库
            while((temp = br.readLine()) != null)
            {
                // 从文件中解析数据并入库
                String[] datas = temp.split(";");
                ApLocationModel ap = new ApLocationModel();
                ap.setMac(datas[0]);
                ap.setLat(datas[1]);
                ap.setLon(datas[2]);
                ap.setUpdateTime(new Date());
                dao.saveApMapper(ap);
            }
        }catch(IOException e){
            LOG.error(e);
        }
        return "redirect:/home/showApMapper";
        
    }
    
    /** 
     * @Title: calcUserGpsLocation 
     * @Description: 由rssi值计算用户的实际位置
     * @param data
     * @return 
     */
    private List<Map<String, Object>> calcUserGpsLocation(List<LocationModel> data){
        // 结果变量
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        // 遍历数据，计算用户实际经纬度
        for(LocationModel lm : data){
            Map<String, Object> tmp = new HashMap<String, Object>();
            // 用户id
            tmp.put("userId", lm.getUserID());
            // 所属mac
            tmp.put("mac", lm.getMac());
            // 用户距mac的距离
            Integer d = findDistance(GlobalConf.getRssiMapper(), lm.getRssi());
            // 随机一个角度
            int angle = (int)(Math.random()*360);
            // 纬度偏移量(米)
            BigDecimal offsetX = BigDecimal.valueOf(Math.cos(angle)*d);
            // 纬度偏移量
            offsetX = offsetX.divide(BigDecimal.valueOf(111*Math.cos(lm.getX().doubleValue())*1000),8,BigDecimal.ROUND_HALF_EVEN);
            // 经度偏移量(米)
            BigDecimal offsetY = BigDecimal.valueOf(Math.sin(angle)*d);
            // 经度偏移量
            offsetY = offsetY.divide(BigDecimal.valueOf(111000),8,BigDecimal.ROUND_HALF_EVEN);
            // 实际纬度
            tmp.put("lat", lm.getX().add(offsetX));
            // 实际经度
            tmp.put("lon", lm.getY().add(offsetY));
            result.add(tmp);
        }
        
        return result;
    }
    
    /** 
     * @Title: findDistance 
     * @Description: 找到rssi对应的距离
     * @param mapper
     * @param rssi
     * @return 
     */
    private Integer findDistance(Map<Integer, Integer> mapper, Integer rssi){
        // 返回值
        Integer result = 0;
        // 遍历映射map，确定rssi对应的距离
        Iterator<Entry<Integer, Integer>> it = mapper.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Integer, Integer> entity = (Entry<Integer, Integer>) it.next();
            Integer r = entity.getKey();
            Integer d = entity.getValue();
            if(rssi >= r){
                result = d;
                break;
            }
        }
        return result;
    }
}
