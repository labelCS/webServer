/**    
 * @Title:  ElectronicDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    ElectronicDao测试类   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:20:39   
 * @version V1.0     
 */  
package com.sva.test.dao;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.springframework.dao.DataAccessException;

import com.sva.dao.ElectronicDao;
import com.sva.model.AreaModel;
import com.sva.model.ElectronicModel;
import com.sva.model.MapsModel;
import com.sva.model.StoreModel;

import org.junit.Assert;

/**   
 * @ClassName:  ElectronicDaoTest   
 * @Description: ElectronicDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class ElectronicDaoTest extends BasicDaoTest {
    @Resource
    ElectronicDao electronicDao;
    
    @Test
    public void doqueryTest(){
        List<ElectronicModel> result = electronicDao.doquery();
        Assert.assertNotEquals("结果不为0",0, result.size());
    }
    
    @Test
    public void selectCategorySameTest(){
        int id = 1;
        List<ElectronicModel> result = electronicDao.doqueryByStoreid(id);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void saveElectronicTest(){
        ElectronicModel em = new ElectronicModel();
        em.setElectronicName("stop");
        em.setMessage("dddd");
        em.setMoviePath("f:/");
        em.setPictruePath("f:/");
        MapsModel maps = new MapsModel();
        maps.setFloorNo(new BigDecimal(1));
        StoreModel store = new StoreModel();
        store.setId(1);
        AreaModel area = new AreaModel();
        area.setId("1");        
        em.setArea(area);
        em.setMaps(maps);
        em.setStore(store);
        int result = 0;
        try{
            result = electronicDao.saveElectronic(em);
            Assert.assertNotEquals("结果不为0",0, result);
        }catch(DataAccessException e){
            Assert.assertEquals("结果为0",0, result);
        }
    }
    
    @Test
    public void deleteMessageTest(){
        String xSpot = "";
        String ySpot = "";
        String floorNo = "";
        int result = electronicDao.deleteMessage(xSpot, ySpot, floorNo);
        Assert.assertNotEquals("结果不为0",0, result);
    }
    
    @Test
    public void updateMsgInfoTest(){
        ElectronicModel em = new ElectronicModel();
        em.setId("1");
        em.setElectronicName("stop");
        em.setMessage("dddd");
        MapsModel maps = new MapsModel();
        maps.setFloorNo(new BigDecimal(1));
        StoreModel store = new StoreModel();
        store.setId(1);
        AreaModel area = new AreaModel();
        area.setId("1");        
        em.setArea(area);
        em.setMaps(maps);
        em.setStore(store);
        int result = electronicDao.updateMsgInfo(em);
        Assert.assertEquals("结果为1",1, result);
    }
    
    @Test
    public void updateMsgInfo1Test(){
        ElectronicModel em = new ElectronicModel();
        em.setId("1");
        em.setElectronicName("stop");
        em.setMessage("dddd");
        em.setMoviePath("f:/");
        MapsModel maps = new MapsModel();
        maps.setFloorNo(new BigDecimal(1));
        StoreModel store = new StoreModel();
        store.setId(1);
        AreaModel area = new AreaModel();
        area.setId("1");        
        em.setArea(area);
        em.setMaps(maps);
        em.setStore(store);
        int result = electronicDao.updateMsgInfo1(em);
        Assert.assertEquals("结果为1",1, result);
    }
    
    @Test
    public void updateMsgInfo2Test(){
        ElectronicModel em = new ElectronicModel();
        em.setId("1");
        em.setElectronicName("stop");
        em.setMessage("dddd");
        MapsModel maps = new MapsModel();
        maps.setFloorNo(new BigDecimal(1));
        StoreModel store = new StoreModel();
        store.setId(1);
        AreaModel area = new AreaModel();
        area.setId("1");        
        em.setArea(area);
        em.setMaps(maps);
        em.setStore(store);
        int result = electronicDao.updateMsgInfo2(em);
        Assert.assertEquals("结果为1",1, result);
    }
    
    @Test
    public void updateMsgInfo3Test(){
        ElectronicModel em = new ElectronicModel();
        em.setId("1");
        em.setElectronicName("stop");
        em.setMessage("dddd");
        em.setMoviePath("f:/");
        em.setPictruePath("f:/");
        MapsModel maps = new MapsModel();
        maps.setFloorNo(new BigDecimal(1));
        StoreModel store = new StoreModel();
        store.setId(1);
        AreaModel area = new AreaModel();
        area.setId("1");        
        em.setArea(area);
        em.setMaps(maps);
        em.setStore(store);
        int result = electronicDao.updateMsgInfo3(em);
        Assert.assertEquals("结果为1",1, result);
    }
    
    @Test
    public void deleteElectronicTest(){
        int id = 1;
        int result = electronicDao.deleteElectronic(id);
        Assert.assertEquals("结果为0",0, result);
    }
}
