/**    
 * @Title:  MapsDao.java   
 * @Package com.sva.dao   
 * @Description:  MapsDao接口类   
 * @author: LabelCS    
 * @date:   2016年9月25日 下午3:49:31   
 * @version V1.0     
 */  
package com.sva.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.sva.model.MapsModel;

/**   
 * @ClassName:  MapsDao   
 * @Description: MapsDao接口类   
 * @author: LabelCS  
 * @date:   2016年9月25日 下午3:49:31   
 *      
 */
public interface MapsDao {
    /**   
     * @Title: doquery   
     * @Description: 此方法把表对应的字段查询出来依次放入model中  
     * @return：List<MapsModel>       
     * @throws   
     */ 
    public List<MapsModel> doquery();

    /**   
     * @Title: doqueryByStoreid   
     * @Description: 通过用户名获取相对应的商场权限  
     * @param storeid
     * @return：List<MapsModel>       
     * @throws   
     */ 
    public List<MapsModel> doqueryByStoreid(int storeId);

    public List<MapsModel> getMapDetail(String mapId);

    public String getMapName(String mapId);

    public String getFloorid(String mapId);

    public int saveMapInfo(MapsModel mmm);

    public int deleteMapByFloor(String mapId);

    public List<MapsModel> getFloors(String placeId);

    public List<MapsModel> getMapDataFromPrru();

    /**   
     * @Title: getMapDataFromPrruByStoreid   
     * @Description: 通过信商场登录名称查询对应的商场权限  
     * @param storeid
     * @return：List<MapsModel>       
     * @throws   
     */ 
    public List<MapsModel> getMapDataFromPrruByStoreid(int storeId);

    public int checkByPlace1(@Param("placeId")String placeId, @Param("floor")String floor, 
            @Param("id")String id);

    public List<Integer> getFloorByPlaceId(String placeId);

    public List<Map<String, Object>> getFloorNo();

    public int chekByFloorNo1(@Param("mapId")int mapId, @Param("id")String id);

    public int checkByPlace(@Param("placeId")String placeId, @Param("floor")String floor);

    public int chekByFloorNo(int mapId);

    public int updateMap(MapsModel mmm);

    public String getFloorByFloorNo(String mapId);

    public int saveAllPeople(@Param("placeId")int placeId, @Param("yesNumber")int yesNumber, @Param("number")int number,
            @Param("nowNumber")int nowNumber, @Param("visiday")String visiday);

    public int saveNowPeople(@Param("placeId")int placeId, @Param("number")int number, 
            @Param("areaName")String areaName, @Param("areaId")String areaId);

    public List<Map<String, Object>> getAllPeopleByPlaceId(@Param("placeId")String placeId,
            @Param("visiday")String visiday);
    
    public MapsModel getMapCarData(int floorNo);
    
    public List<String> getParkingNumber(int floorNo);
    
}
