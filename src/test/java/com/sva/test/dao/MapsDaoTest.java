/**    
 * @Title:  MapsDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    MapsDao测试类   
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
import com.sva.dao.MapsDao;
import com.sva.model.MapsModel;
import com.sva.model.StoreModel;
import org.junit.Assert;

/**   
 * @ClassName:  MapsDaoTest   
 * @Description:MapsDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class MapsDaoTest extends BasicDaoTest {
    
    @Resource
    MapsDao mapsDao;
    
    @Test
    public void doqueryTest(){
        List<MapsModel> result = mapsDao.doquery();
        Assert.assertNotEquals("结果不为0",0, result.size());
    }

    @Test
    public void doqueryByStoreidTest(){
        int storeId = 1;
        List<MapsModel> result = mapsDao.doqueryByStoreid(storeId);
        Assert.assertNotEquals("结果不为0",0, result.size());
    }
    
    @Test
    public void getMapDetailTest(){
        String floorNo = "40001";
        List<MapsModel> result = mapsDao.getMapDetail(floorNo);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getMapNameTest(){
        String floorNo = "40001";
        String result = mapsDao.getMapName(floorNo);
        Assert.assertNotEquals("结果不为空","", result);
    }
    
    @Test
    public void getFlooridTest(){
        String floorNo = "40001";
        String result = mapsDao.getFloorid(floorNo);
        Assert.assertNotEquals("结果不为空","", result);
    }
    
    @Test
    public void saveMapInfoTest(){
        MapsModel maps = new MapsModel();
        maps.setCoordinate("ll");
        maps.setFloor("1F");
        maps.setFloorid(new BigDecimal(11));
        maps.setFloorNo(new BigDecimal(10011));
        maps.setImgHeight(100);
        maps.setImgWidth(50);
        maps.setPath("f:/a.png");
        maps.setScale("100");
        maps.setXo("5");
        maps.setYo("5");
        StoreModel store = new StoreModel();
        store.setId(1);
        maps.setStore(store);
        int result = mapsDao.saveMapInfo(maps);
        Assert.assertEquals("结果为1",1, result);
    }
    
    @Test
    public void deleteMapByFloorTest(){
        String floorNo = "40001";
        int result = mapsDao.deleteMapByFloor(floorNo);
        Assert.assertNotEquals("结果不为0",0, result);
    }
    
    @Test
    public void getFloorsTest(){
        String placeId = "1";
        List<MapsModel> result = mapsDao.getFloors(placeId);
        Assert.assertNotEquals("结果不为0",0, result.size());
    }
    
    @Test
    public void getMapDataFromPrruTest(){
        List<MapsModel> result = mapsDao.getMapDataFromPrru();
        Assert.assertNotEquals("结果不为0",0, result.size());
    }
    
    @Test
    public void getMapDataFromPrruByStoreidTest(){
        int storeId = 1;
        List<MapsModel> result = mapsDao.getMapDataFromPrruByStoreid(storeId);
        Assert.assertNotEquals("结果不为0",0, result.size());
    }
    
    @Test
    public void checkByPlace1Test(){
        String placeId = "1"; 
        String floor = "1F";
        String id = "1";
        int result = mapsDao.checkByPlace1(placeId,floor,id);
        Assert.assertNotEquals("结果为0",0, result);
    }
    
    @Test
    public void getFloorByPlaceIdTest(){
        String placeId = "1"; 
        List<Integer> result = mapsDao.getFloorByPlaceId(placeId);
        Assert.assertNotEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getFloorNoTest(){
        List<Map<String, Object>> result = mapsDao.getFloorNo();
        Assert.assertNotEquals("结果为0",0, result.size());
    }
    
    @Test
    public void chekByFloorNo1Test(){
        int floorNo = 1; 
        String id = "1";
        int result = mapsDao.chekByFloorNo1(floorNo, id);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void checkByPlaceTest(){
        String placeId = "1"; 
        String floor = "1F";
        int result = mapsDao.checkByPlace(placeId, floor);
        Assert.assertNotEquals("结果为0",0, result);
    }
    
    @Test
    public void chekByFloorNoTest(){
        int floorNo = 1;
        int result = mapsDao.chekByFloorNo(floorNo);
        Assert.assertNotEquals("结果为0",0, result);
    }
    
    @Test
    public void updateMapTest(){
        MapsModel maps = new MapsModel();
        maps.setCoordinate("ll");
        maps.setFloor("1F");
        maps.setFloorid(new BigDecimal(11));
        maps.setFloorNo(new BigDecimal(10011));
        maps.setImgHeight(100);
        maps.setImgWidth(50);
        maps.setPath("f:/a.png");
        maps.setScale("100");
        maps.setXo("5");
        maps.setYo("5");
        maps.setId("1");
        StoreModel store = new StoreModel();
        store.setId(1);
        maps.setStore(store);
        int result = mapsDao.updateMap(maps);
        Assert.assertEquals("结果为0",0, result);
    }
}
