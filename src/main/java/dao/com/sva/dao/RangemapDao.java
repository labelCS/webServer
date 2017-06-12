/**   
 * @Title: RangemapDao.java 
 * @Package com.sva.dao 
 * @Description: RangemapDao接口类
 * @author labelCS   
 * @date 2016年10月12日 下午2:29:52 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.sva.model.LinemapModel;

/** 
 * @ClassName: RangemapDao
 * @Description: RangemapDao接口类
 * @author labelCS 
 * @date 2016年10月12日 下午2:29:52 
 *  
 */
public interface RangemapDao {
    public List<LinemapModel> getData(@Param("placeId")String placeId, @Param("floorNo")String floorNo,
            @Param("x1")String x1, @Param("y1")String y1, @Param("x2")String x2, 
            @Param("y2")String y2, @Param("tableName")String tableName);

    public int getTotalCount(@Param("placeId")String placeId, @Param("floorNo")String floorNo, 
            @Param("x1")String x1, @Param("y1")String y1, @Param("x2")String x2, 
            @Param("y2")String y2, @Param("tableName")String tableName);

    public List<Map<String, Object>> getDataForArea(@Param("areaId")String areaId,
            @Param("startTime")String startTime, @Param("endTime")String endTime);

    public List<Map<String, Object>> getDataForAreaByDay(@Param("areaId")String areaId,
            @Param("startTime")String startTime, @Param("endTime")String endTime);

    public List<Map<String, Object>> getAreaName();

    public String getNameById(String areaId);

    public List<Map<String, Object>> getVisitData(@Param("areaId")String areaId,
            @Param("startTime")String startTime, @Param("endTime")String endTime);
}
