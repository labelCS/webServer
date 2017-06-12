/**   
 * @Title: LinemapDao.java 
 * @Package com.sva.dao 
 * @Description: LinemapDao接口类  
 * @author labelCS   
 * @date 2016年9月21日 上午10:37:30 
 * @version V1.0   
 */
package com.sva.dao;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.sva.model.LinemapModel;

/** 
 * @ClassName: LinemapDao
 * @Description: LinemapDao接口类 
 * @author labelCS 
 * @date 2016年9月21日 上午10:37:30 
 *  
 */
public interface LinemapDao {
    public List<LinemapModel> getDataByDay(@Param("placeId")String placeId, @Param("start")String start, 
            @Param("end")String end);

    public List<LinemapModel> getDataByHour(@Param("placeId")String placeId, @Param("start")String start, 
            @Param("end")String end);

    public int getTotalCount(@Param("placeId")String placeId, @Param("start")String start, 
            @Param("end")String end);

    public List<LinemapModel> getMaxTime(@Param("placeId")String placeId, @Param("start")String start, 
            @Param("end")String end);

    public List<LinemapModel> getMaxDay(@Param("placeId")String placeId,@Param("start") String start, 
            @Param("end")String end);

    public int getAreaNumberByDay(@Param("mapId")int mapId,@Param("tableName")String tableName,@Param("xSpot")BigDecimal xSpot,@Param("x1Spot")BigDecimal x1Spot,@Param("ySpot")BigDecimal ySpot,@Param("y1Spot")BigDecimal y1Spot);
    
    public int getAreaNumberByHour(@Param("mapId")int mapId,@Param("tableName")String tableName,@Param("time")long time,@Param("xSpot")BigDecimal x,@Param("x1Spot")BigDecimal x1,@Param("ySpot")BigDecimal y,@Param("y1Spot")BigDecimal y1);
    
    public int getAllAreaData(@Param("mapId")int mapId,@Param("tableName")String tableName,@Param("xSpot")BigDecimal x,@Param("x1Spot")BigDecimal x1,@Param("ySpot")BigDecimal y,@Param("y1Spot")BigDecimal y1);
}
