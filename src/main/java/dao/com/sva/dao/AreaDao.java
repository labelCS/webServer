/**    
 * @Title:  AreaDao.java   
 * @Package com.sva.dao   
 * @Description:  AreaDao接口类    
 * @author: LabelCS    
 * @date:   2016年9月17日 下午9:14:31   
 * @version V1.0     
 */  
package com.sva.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.sva.model.ACRModel;
import com.sva.model.AreaModel;
import com.sva.model.LocationModel;

/**   
 * @ClassName:  AreaDao   
 * @Description: AreaDao接口类   
 * @author: LabelCS  
 * @date:   2016年9月17日 下午9:14:31   
 *      
 */
public interface AreaDao {
    /**   
     * @Title: doquery   
     * @Description: 此方法把表对应的字段查询出来依次放入model中  
     * @return：List<AreaModel>       
     * @throws   
     */ 
    public List<AreaModel> doquery();
    
    public List<AreaModel> getAreaByMessage();

    public List<AreaModel> doqueryAll(int storeid);
    
    public List<AreaModel> getAreaByFloorNo(int floorNo);

    public List<AreaModel> selectArea(String zSel);

    public List<AreaModel> getAreaDataById(String id);

    public List<AreaModel> getAreaByPlaceId(String zSel);

    public int saveAreaInfo(AreaModel area) throws DataAccessException;

    public int deleteArea(AreaModel area);

    public int updateAreaInfo(AreaModel mmm);

    public int getNumberByArea(@Param("area")AreaModel mm, @Param("nowTime")long nowTime, 
            @Param("startTime")long startTime, @Param("tableName")String tableName);

    public int getNumberByAreaDay(@Param("area")AreaModel mm, @Param("tableName")String tableName);

    public int getNumberByTime(@Param("floorNo")int floorNo, @Param("tableName")String tableName);

    public int getYesterdayNumber(@Param("floorNo")int floorNo, @Param("tableName")String tableName, 
            @Param("time")String time);

    public int getNumberByFloorNo(@Param("area")AreaModel mm, @Param("time")String time, 
            @Param("tableName")String tableName);

    public int checkByName(String name);

    public int checkByName1(@Param("name")String name, @Param("id")String id);

    public List<Map<String, Object>> getAreaName(String placeId);

    public List<String> getAreaNames(String placeId);

    public List<String> getAreaNameByAreaId(String areaId);

    public List<String> getZoneId(String areaId);

    public List<String> getVisitUser(@Param("area")AreaModel mm, @Param("tableName")String tableName);

    public List<Long> getVisitTimes(@Param("area")AreaModel mm, @Param("userId")String userId,
            @Param("tableName")String tablename);

    public int saveVisitData(@Param("areaId")String areaId, @Param("time")String time, @Param("allTime")Long allTime,
            @Param("number")int number, @Param("averageTime")String averageTime);

    public int getBaShowVisitUser(@Param("area")AreaModel mm, @Param("tableName")String tableName,
            @Param("time")String time);

    public List<String> getAllBaShowVisitUser(@Param("floorNo")String floorNo, @Param("tableName")String tableName,
            @Param("time")String time);

    public List<String> getUserByFloorNo(@Param("floorNo")String floorNo, @Param("tableName")String tableName,
            @Param("time")String time);

    public List<Long> getBaShowVisitTimes(@Param("area")AreaModel mm, @Param("userId")String userId,
            @Param("tableName")String tablename, @Param("time")String time);

    public List<Long> getBaShowAllVisitTimes(@Param("floorNo")String floorNo, @Param("userId")String userId,
            @Param("tableName")String tablename, @Param("time")String time);

    public int svaeBaShowByAeare(@Param("areaId")String areaId, @Param("time")String time, 
            @Param("allTime")Long allTime, @Param("number")int number, @Param("averageTime")String averageTime);

    public int svaeBaShowByAllAeare(@Param("floorNo")String floorNo, @Param("time")String time, 
            @Param("allTime")Long allTime, @Param("number")int number, @Param("averageTime")String averageTime);

    public List<AreaModel> selectAeareBaShow(String zSel);

    public List<AreaModel> getAreaByAreaId(String id);

    public List<Map<String, Object>> getAverageTimeByAreaId(@Param("areaId")String areaId, @Param("visiday")String visiday);

    public List<Map<String, Object>> getAllAverageTimeByAreaId(@Param("floorNo")String floorNo,
            @Param("time")String time);

    public List<Map<String, Object>> getOverAverageTime(@Param("areaId")String areaId, @Param("time")String time);

    public List<Map<String, Object>> getBaShowAllData(@Param("floorNo")String floorNo,
            @Param("time")String time, @Param("tableName")String tableName);

    public List<Map<String, Object>> getBaShowData(@Param("area")AreaModel mm, @Param("time")String time,
            @Param("tableName")String tableName);

    public List<Map<String, Object>> getVisitTIme(@Param("area")AreaModel mm, @Param("tableName")String tableName);

    public List<Map<String, Object>> getNowNumber(String placeId);

    public List<Map<String, Object>> getNowAllTime(@Param("areaId")String areaId, @Param("time")String visitDay);

    public int updateStatus(String areaId);

    public int updateStatus1(String areaId);

    public int updateZoneId(@Param("areaId")String areaId, @Param("zoneId")String zoneId);

    /**
     * 更新zoneid为空
     * 
     * @param areaId
     */
    public int updateZoneIdToNull(String areaId);

    public int getBaShowVisitUser2(@Param("areaId")String areaId, @Param("time")String time);

    public int getAllPeoples(@Param("floorNo")String floorNo, @Param("tableName")String tableName, 
            @Param("time")long bzTime);

//    public int getAllArea(String areaId);
    
    public String getZoneIdByAreaId(String areaId);

    public List<Map<String, Object>> getAllAverageTimeByAreaId2(@Param("floorNo")String floorNo,
            @Param("time")long time, @Param("tableName")String tableName);

    public Long getMaxTimestamp();

    public List<AreaModel> geofencingByLocation(LocationModel loc);
    
    public List<Map<String, Object>> getAverageTimeByAreaId1(@Param("areaId")String areaId, 
            @Param("time")long time);

    public int getAllArea(@Param("area")AreaModel mm, @Param("tableName")String tableName,
            @Param("time")long time);
    
    public List<Map<String,Object>>getACRData(@Param("area")AreaModel mm, @Param("tableName")String tableName, @Param("floorNo")int floorNo,@Param("startTime")long startTime,@Param("endTime")long endTime);
    
    public int savaACR(@Param("acr")ACRModel acr);
    
    public List<String> getTicketByLocation(@Param("x")double x,@Param("y")double y,@Param("floorNo")int floorNo);
}
