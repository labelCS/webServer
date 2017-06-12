/**   
 * @Title: LocationDao.java 
 * @Package com.sva.dao 
 * @Description: LocationDao接口类 
 * @author labelCS   
 * @date 2016年9月21日 下午3:28:34 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.sva.model.LocationModel;

/** 
 * @ClassName: LocationDao 
 * @Description: LocationDao接口类 
 * @author labelCS 
 * @date 2016年9月21日 下午3:28:34 
 *  
 */
public interface LocationDao {

    public List<LocationModel> doquery1(@Param("userId")String userId, @Param("mapId")int mapId);

    public List<LocationModel> queryHeatmap(@Param("mapId")String mapId, @Param("time")long time, 
            @Param("tableName")String tableName);
    
    public List<LocationModel> queryHeatmap5(@Param("mapId")String mapId, @Param("time")long time,
            @Param("tableName")String tableName);
    
    public List<LocationModel> queryHeatmap6(@Param("mapId")String mapId, @Param("tableName")String tableName);

    /**
     * locationPhone为非匿名订阅，提高数据查询效率
     * 
     * @param userId
     * @return
     */
    public List<LocationModel> queryLocationByUseId(String userId);

    public List<LocationModel> getUserId(@Param("mapId")String floorid, @Param("time")String time,
            @Param("tableName")String tableName);

    public List<LocationModel> getMark(@Param("userId")String userId, @Param("time")String time,
            @Param("tableName")String tableName);

    public List<LocationModel> queryOverData(@Param("mapId")int mapId, @Param("time")String time, 
            @Param("tableName")String tableName);
    
    /** 
     * @Title: queryLocationForPosition 
     * @Description: 查询指定日期当天的顾客数据
     * @param time 指定日期，形如YYYYMMDD
     * @return Collection<LocationModel>   
     * @throws 
     */
    public List<Map<String,Object>> queryLocationForPosition(@Param("mapId")String mapId, 
            @Param("timeList")List<String> timeList);
    
    /** 
     * @Title: queryScatterMapData 
     * @Description: 获取散点图数据
     * @param mapId 楼层号
     * @param time 时间
     * @return Collection<LocationModel>   
     * @throws 
     */
    public List<Map<String,Object>> queryScatterMapData(@Param("mapId")String mapId, @Param("time")long time,
            @Param("tableName")String tableName);

    public int getNumberByMinute(@Param("tableName")String tableName, @Param("placeId")String placeId);

    public int getYesterdayNumber(@Param("tableName")String tableName, @Param("placeId")String placeId, 
            @Param("time")long time);

    public int getNowPeople(@Param("tableName")String tableName, @Param("placeId")String placeId, 
            @Param("time")String time);
}
