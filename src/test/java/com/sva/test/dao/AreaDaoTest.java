/**    
 * @Title:  AreaDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    AreaDao测试类   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:20:39   
 * @version V1.0     
 */  
package com.sva.test.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import com.sva.dao.AreaDao;
import com.sva.model.AreaModel;
import com.sva.model.CategoryModel;
import com.sva.model.LocationModel;
import com.sva.model.MapsModel;
import com.sva.model.StoreModel;
import org.junit.Assert;

/**   
 * @ClassName:  AreaDaoTest   
 * @Description: AreaDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class AreaDaoTest extends BasicDaoTest {
    
    @Resource
    AreaDao areaDao;
    
    @Test
    public void doqueryTest(){
        List<AreaModel> result = areaDao.doquery();
        Assert.assertNotEquals("结果不为0",0, result.size());
    }

    @Test
    public void doqueryAllTest(){
        int storeid = 1;
        List<AreaModel> result = areaDao.doqueryAll(storeid);
        Assert.assertNotEquals("结果不为0",0, result.size());
    }
    
    @Test
    public void selectAreaTest(){
        String zSel = "1";
        List<AreaModel> result = areaDao.selectArea(zSel);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getAreaDataByIdTest(){
        String id = "1";
        List<AreaModel> result = areaDao.getAreaDataById(id);
        Assert.assertEquals("结果为1",1, result.size());
    }
    
    @Test
    public void getAreaByPlaceIdTest(){
        String id = "1";
        List<AreaModel> result = areaDao.getAreaByPlaceId(id);
        Assert.assertNotEquals("结果不为0",0, result.size());
    }
    
    @Test
    public void saveAreaInfoTest(){
        AreaModel am = new AreaModel();
        MapsModel rm = new MapsModel();
        StoreModel sm = new StoreModel();
        CategoryModel cm = new CategoryModel();
        rm.setFloorNo(new BigDecimal(1));
        sm.setId(1);
        cm.setId(1);
        am.setAreaName("test");
        am.setxSpot(new BigDecimal(1));
        am.setX1Spot(new BigDecimal(1));
        am.setySpot(new BigDecimal(1));
        am.setY1Spot(new BigDecimal(1));
        am.setIsVip("true");
        am.setMaps(rm);
        am.setStore(sm);
        am.setCategory(cm);
        int result = 0;
        try{
            result = areaDao.saveAreaInfo(am);
            Assert.assertEquals("保存成功，结果为1",1, result);
        }catch(DataAccessException e){
            Assert.assertEquals("sql异常，结果为0",0, result);
        }
    }
    
    @Test
    public void deleteAreaTest(){
        AreaModel am = new AreaModel();
        MapsModel rm = new MapsModel();
        CategoryModel cm = new CategoryModel();
        rm.setFloorNo(new BigDecimal(1));
        cm.setId(1);
        am.setAreaName("test");
        am.setxSpot(new BigDecimal(1));
        am.setX1Spot(new BigDecimal(1));
        am.setySpot(new BigDecimal(1));
        am.setY1Spot(new BigDecimal(1));
        am.setMaps(rm);
        am.setCategory(cm);
        
        int result = areaDao.deleteArea(am);
        
        Assert.assertEquals("结果为0",0, result);
        
    }
    
    @Test
    public void updateAreaInfoTest(){
        AreaModel am = new AreaModel();
        MapsModel rm = new MapsModel();
        StoreModel sm = new StoreModel();
        CategoryModel cm = new CategoryModel();
        rm.setFloorNo(new BigDecimal(1));
        sm.setId(1);
        cm.setId(1);
        am.setAreaName("test");
        am.setxSpot(new BigDecimal(1));
        am.setX1Spot(new BigDecimal(1));
        am.setySpot(new BigDecimal(1));
        am.setY1Spot(new BigDecimal(1));
        am.setIsVip("true");
        am.setMaps(rm);
        am.setStore(sm);
        am.setCategory(cm);
        
        int result = areaDao.updateAreaInfo(am);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void getNumberByAreaTest(){
        AreaModel am = new AreaModel();
        MapsModel rm = new MapsModel();
        rm.setFloorNo(new BigDecimal(1));
        am.setxSpot(new BigDecimal(1));
        am.setX1Spot(new BigDecimal(1));
        am.setySpot(new BigDecimal(1));
        am.setY1Spot(new BigDecimal(1));
        am.setMaps(rm);
        long startTime = 1;
        long nowTime = 1;
        String tableName = "location20160829";
        int result = areaDao.getNumberByArea(am,nowTime, startTime,tableName);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void getNumberByAreaDayTest(){
        AreaModel am = new AreaModel();
        MapsModel rm = new MapsModel();
        rm.setFloorNo(new BigDecimal(1));
        am.setxSpot(new BigDecimal(1));
        am.setX1Spot(new BigDecimal(1));
        am.setySpot(new BigDecimal(1));
        am.setY1Spot(new BigDecimal(1));
        am.setMaps(rm);
        String tableName = "location20160829";
        int result = areaDao.getNumberByAreaDay(am,tableName);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void getNumberByTimeTest(){
        int floorNo = 1;
        String tableName = "location20160829";
        int result = areaDao.getNumberByTime(floorNo, tableName);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void getYesterdayNumberTest(){
        int floorNo = 1;
        String tableName = "location20160829";
        String time = "1";
        int result = areaDao.getYesterdayNumber(floorNo, tableName, time);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void getNumberByFloorNoTest(){
        AreaModel am = new AreaModel();
        MapsModel rm = new MapsModel();
        rm.setFloorNo(new BigDecimal(1));
        am.setxSpot(new BigDecimal(1));
        am.setX1Spot(new BigDecimal(1));
        am.setySpot(new BigDecimal(1));
        am.setY1Spot(new BigDecimal(1));
        am.setMaps(rm);
        String time = "1";
        String tableName = "location20160829";
        int result = areaDao.getNumberByFloorNo(am,time, tableName);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void checkByNameTest(){
        String name = "aa";
        int result = areaDao.checkByName(name);
        Assert.assertEquals("结果为0",0,result);
    }
    
    @Test
    public void checkByName1Test(){
        String name = "aa";
        int result = areaDao.checkByName1(name,"1");
        Assert.assertEquals("结果为0",0,result);
    }
    
    @Test
    public void getAreaNameTest(){
        String placeId = "1";
        List<Map<String,Object>> result = areaDao.getAreaName(placeId);
        Assert.assertNotEquals("结果不为0", 0, result.size());
    }
    
    @Test
    public void getAreaNamesTest(){
        String placeId = "1";
        List<String> result = areaDao.getAreaNames(placeId);
        Assert.assertNotEquals("结果不为0", 0, result.size());
    }
    
    @Test
    public void getAreaNameByAreaIdTest(){
        String areaId = "1";
        List<String> result = areaDao.getAreaNameByAreaId(areaId);
        Assert.assertNotEquals("结果不为0", 0, result.size());
    }
    
    @Test
    public void getZoneIdTest(){
        String zoneId = "1";
        List<String> result = areaDao.getZoneId(zoneId);
        Assert.assertNotEquals("结果不为0", 0, result.size());
    }
    
    @Test
    public void getVisitUserTest(){
        AreaModel am = new AreaModel();
        MapsModel mm = new MapsModel();
        mm.setFloorNo(new BigDecimal(1));
        am.setxSpot(new BigDecimal(1));
        am.setX1Spot(new BigDecimal(1));
        am.setySpot(new BigDecimal(1));
        am.setY1Spot(new BigDecimal(1));
        am.setMaps(mm);
        String tableName = "location20160829";
        List<String> result = areaDao.getVisitUser(am, tableName);
        Assert.assertEquals("结果为0", 0, result.size());
    }
    
    @Test
    public void getVisitTimesTest(){
        AreaModel am = new AreaModel();
        MapsModel mm = new MapsModel();
        mm.setFloorNo(new BigDecimal(1));
        am.setxSpot(new BigDecimal(1));
        am.setX1Spot(new BigDecimal(1));
        am.setySpot(new BigDecimal(1));
        am.setY1Spot(new BigDecimal(1));
        am.setMaps(mm);
        String userId = "1";
        String tableName = "location20160829";
        List<Long> result = areaDao.getVisitTimes(am, userId, tableName);
        Assert.assertEquals("结果为0", 0, result.size());
    }
    
    @Test
    public void saveVisitDataTest(){
        String areaId = "1";
        String time = "1";
        Long allTime = 1L;
        int number = 1;
        String averageTime = "1";
        int result = areaDao.saveVisitData(areaId, time, allTime, number, averageTime);;
        Assert.assertEquals("保存成功，结果为1",1, result);
    }
    
    @Test
    public void getBaShowVisitUserTest(){
        AreaModel am = new AreaModel();
        MapsModel mm = new MapsModel();
        mm.setFloorNo(new BigDecimal(1));
        am.setxSpot(new BigDecimal(1));
        am.setX1Spot(new BigDecimal(1));
        am.setySpot(new BigDecimal(1));
        am.setY1Spot(new BigDecimal(1));
        am.setMaps(mm);
        String tableName = "location20160829";
        String time = "111";
        int result = areaDao.getBaShowVisitUser(am, tableName, time);
        Assert.assertEquals("结果为0", 0, result);
    }
    
    @Test
    public void getAllBaShowVisitUserTest(){
        String floorNo = "1";
        String time = "1";
        String tableName = "location20160829";
        List<String> result = areaDao.getAllBaShowVisitUser(floorNo, tableName, time);
        Assert.assertEquals("保存成功，结果为0",0, result.size());
    }
    
    @Test
    public void getUserByFloorNoTest(){
        String floorNo = "1";
        String time = "1";
        String tableName = "location20160829";
        List<String> result = areaDao.getUserByFloorNo(floorNo, tableName, time);
        Assert.assertEquals("保存成功，结果为0",0, result.size());
    }
    
    @Test
    public void getBaShowVisitTimesTest(){
        AreaModel am = new AreaModel();
        MapsModel mm = new MapsModel();
        mm.setFloorNo(new BigDecimal(1));
        am.setxSpot(new BigDecimal(1));
        am.setX1Spot(new BigDecimal(1));
        am.setySpot(new BigDecimal(1));
        am.setY1Spot(new BigDecimal(1));
        am.setMaps(mm);
        String tableName = "location20160829";
        String time = "111";
        String userId = "1";
        List<Long> result = areaDao.getBaShowVisitTimes(am, userId, tableName, time);
        Assert.assertEquals("结果为0", 0, result.size());
    }
    
    @Test
    public void getBaShowAllVisitTimesTest(){
        String floorNo = "1";
        String tableName = "location20160829";
        String time = "111";
        String userId = "1";
        List<Long> result = areaDao.getBaShowAllVisitTimes(floorNo, userId, tableName, time);
        Assert.assertEquals("结果为0", 0, result.size());
    }
    
    @Test
    public void svaeBaShowByAeareTest(){
        String areaId = "1";
        String time = "1";
        Long allTime = 1L;
        int number = 1;
        String averageTime = "1";
        int result = areaDao.svaeBaShowByAeare(areaId, time, allTime, number, averageTime);;
        Assert.assertEquals("保存成功，结果为1",1, result);
    }
    
    @Test
    public void svaeBaShowByAllAeareTest(){
        String floorNo = "1";
        String time = "1";
        Long allTime = 1L;
        int number = 1;
        String averageTime = "1";
        int result = areaDao.svaeBaShowByAllAeare(floorNo, time, allTime, number, averageTime);;
        Assert.assertEquals("保存成功，结果为1",1, result);
    }
    
    @Test
    public void selectAeareBaShowTest(){
        String floorNo = "1";
        List<AreaModel> result = areaDao.selectAeareBaShow(floorNo);
        Assert.assertEquals("结果为0", 0,result.size());
    }
    
    @Test
    public void getAreaByAreaIdTest(){
        String floorNo = "1";
        List<AreaModel> result = areaDao.getAreaByAreaId(floorNo);
        Assert.assertNotEquals("结果不为0", 0,result.size());
    }
    
    @Test
    public void getAverageTimeByAreaIdTest(){
        String areaId = "1";
        String visiday = "1";
        List<Map<String, Object>> result = areaDao.getAverageTimeByAreaId(areaId, visiday);
        Assert.assertNull(result);
    }
    
    @Test
    public void getAllAverageTimeByAreaIdTest(){
        String floorNo = "1";
        String time = "1";
        List<Map<String, Object>> result = areaDao.getAllAverageTimeByAreaId(floorNo, time);
        Assert.assertEquals("结果为0", 0,result.size());
    }
    
    @Test
    public void getOverAverageTimeTest(){
        String areaId = "1";
        String visiday = "1";
        List<Map<String, Object>> result = areaDao.getOverAverageTime(areaId, visiday);
        Assert.assertEquals("结果为0", 0,result.size());
    }
    
    @Test
    public void getBaShowAllDataTest(){
        String floorNo = "1";
        String time = "1";
        String tableName = "location20160829";
        List<Map<String, Object>> result = areaDao.getBaShowAllData(floorNo, time, tableName);
        Assert.assertEquals("结果为0", 0,result.size());
    }
    
    @Test
    public void getBaShowDataTest(){
        AreaModel am = new AreaModel();
        MapsModel mm = new MapsModel();
        mm.setFloorNo(new BigDecimal(1));
        am.setxSpot(new BigDecimal(1));
        am.setX1Spot(new BigDecimal(1));
        am.setySpot(new BigDecimal(1));
        am.setY1Spot(new BigDecimal(1));
        am.setMaps(mm);
        String tableName = "location20160829";
        String time = "111";
        List<Map<String, Object>> result = areaDao.getBaShowData(am, time, tableName);
        Assert.assertEquals("结果为0", 0,result.size());
    }
    
    @Test
    public void getVisitTImeTest(){
        AreaModel am = new AreaModel();
        MapsModel mm = new MapsModel();
        mm.setFloorNo(new BigDecimal(1));
        am.setxSpot(new BigDecimal(1));
        am.setX1Spot(new BigDecimal(1));
        am.setySpot(new BigDecimal(1));
        am.setY1Spot(new BigDecimal(1));
        am.setMaps(mm);
        String tableName = "location20160829";
        List<Map<String, Object>> result = areaDao.getVisitTIme(am, tableName);
        Assert.assertEquals("结果为0", 0,result.size());
    }
    
    @Test
    public void getNowNumberTest(){
        String placeId = "1";
        List<Map<String, Object>> result = areaDao.getNowNumber(placeId);
        Assert.assertNotEquals("结果不为0", 0,result.size());
    }
    
    @Test
    public void getNowAllTimeTest(){
        String areaId = "1";
        String time = "1";
        List<Map<String, Object>> result = areaDao.getNowAllTime(areaId, time);
        Assert.assertEquals("结果为0", 0,result.size());
    }
    
    @Test
    public void updateStatusTest(){
        String areaId = "1";
        int result = areaDao.updateStatus(areaId);
        Assert.assertNotEquals("结果不为0", 0,result);
    }
    
    @Test
    public void updateStatus1Test(){
        String areaId = "1";
        int result = areaDao.updateStatus1(areaId);
        Assert.assertNotEquals("结果不为0", 0,result);
    }
    
    @Test
    public void updateZoneIdTest(){
        String areaId = "1";
        String zoneId = "1";
        int result = areaDao.updateZoneId(areaId, zoneId);
        Assert.assertNotEquals("结果不为0", 0,result);
    }
    
    @Test
    public void updateZoneIdToNullTest(){
        String areaId = "1";
        int result = areaDao.updateZoneIdToNull(areaId);
        Assert.assertNotEquals("结果不为0", 0,result);
    }
    
    @Test
    public void getBaShowVisitUser2Test(){
        String areaId = "1";
        String time = "11";
        int result = areaDao.getBaShowVisitUser2(areaId,time);
        Assert.assertEquals("结果为0", 0,result);
    }
    
    @Test
    public void getAllPeoplesTest(){
        String floorNO = "";
        String tableName = "location20160829";
        long time = 11;
        int result = areaDao.getAllPeoples(floorNO,tableName,time);
        Assert.assertEquals("结果为0", 0,result);
    }
    
//    @Test
//    public void getAllAreaTest(){
//        String areaId = "1";
//        int result = areaDao.getAllArea(areaId);
//        Assert.assertEquals("结果为0", 0,result);
//    }
    
    @Test
    public void getZoneIdByAreaIdTest(){
        String areaId = "1";
        String result = areaDao.getZoneIdByAreaId(areaId);
        Assert.assertNull("结果为Null", result);
    }
    
    @Test
    public void getAllAverageTimeByAreaId2Test(){
        String floorNo = "1";
        long time = 1;
        String tableName = "location20160829";
        List<Map<String, Object>> result = areaDao.getAllAverageTimeByAreaId2(floorNo, time, tableName);
        Assert.assertEquals("结果为0", 0,result.size());
    }
    
    @Test
    public void getMaxTimestampTest(){
        Long result = areaDao.getMaxTimestamp();
        Assert.assertNull("结果为Null", result);
    }
    
    @Test
    public void geofencingByLocationTest(){
        LocationModel lm = new LocationModel();
        lm.setX(new BigDecimal(1));
        lm.setY(new BigDecimal(1));
        lm.setZ(new BigDecimal(1));
        List<AreaModel> result = areaDao.geofencingByLocation(lm);
        Assert.assertEquals("结果为Null",0, result.size());
    }
    
    @Test
    public void getAverageTimeByAreaId1Test(){
        String areaId = "1";
        long time = 1;
        List<Map<String,Object>> result = areaDao.getAverageTimeByAreaId1(areaId, time);
        Assert.assertEquals("结果为Null",0, result.size());
    }
}
