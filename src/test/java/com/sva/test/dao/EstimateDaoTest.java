/**    
 * @Title:  EstimateDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    EstimateDao测试类   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:20:39   
 * @version V1.0     
 */  
package com.sva.test.dao;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import com.sva.dao.EstimateDao;
import com.sva.model.EstimateModel;
import com.sva.model.MapsModel;
import com.sva.model.StoreModel;
import org.junit.Assert;

/**   
 * @ClassName:  EstimateDaoTest   
 * @Description: EstimateDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class EstimateDaoTest extends BasicDaoTest {
    @Resource
    EstimateDao estimateDao;
    
    @Test
    public void doqueryTest(){
        List<EstimateModel> result = estimateDao.doquery();
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void doqueryByStoreidTest(){
        int id = 1;
        List<EstimateModel> result = estimateDao.doqueryByStoreid(id);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getEstimateTest(){
        String floorNo = "1";
        BigDecimal result = estimateDao.getEstimate(floorNo);
        Assert.assertEquals("结果为0",null, result);
    }
    
    @Test
    public void saveInfoTest(){
        EstimateModel em = new EstimateModel();
        em.setA(new BigDecimal(1));
        em.setB(new BigDecimal(1));
        em.setD(new BigDecimal(1));
        em.setDeviation(new BigDecimal(1));
        em.setN(1);
        em.setType(new BigDecimal(1));
        MapsModel maps = new MapsModel();
        maps.setFloorNo(new BigDecimal(1));
        em.setMaps(maps);
        StoreModel store = new StoreModel();
        store.setId(1);
        em.setStore(store);
        int result = estimateDao.saveInfo(em);
        Assert.assertNotEquals("结果不为0",0, result);
    }
    
    @Test
    public void deleteInfoTest(){
        String id = "1";
        int result = estimateDao.deleteInfo(id);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void updateInfoTest(){
        EstimateModel em = new EstimateModel();
        em.setId("1");
        em.setA(new BigDecimal(1));
        em.setB(new BigDecimal(1));
        em.setD(new BigDecimal(1));
        em.setDeviation(new BigDecimal(1));
        em.setN(1);
        em.setType(new BigDecimal(1));
        MapsModel maps = new MapsModel();
        maps.setFloorNo(new BigDecimal(1));
        em.setMaps(maps);
        StoreModel store = new StoreModel();
        store.setId(1);
        em.setStore(store);
        int result = estimateDao.updateInfo(em);
        Assert.assertEquals("结果为0",0, result);
    }

    @Test
    public void selectID1Test(){
        BigDecimal floorNo = new BigDecimal(1);
        String id = "1";
        List<EstimateModel> result = estimateDao.selectID1(floorNo, id);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getFloorByFloorNoTest(){
        String floorNo = "1";
        String result = estimateDao.getFloorByFloorNo(floorNo);
        Assert.assertEquals("结果为0",null, result);
    }
    
    @Test
    public void checkByFloorNoTest(){
        String floorNo = "1";
        int result = estimateDao.checkByFloorNo(floorNo);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void checkByFloorNo1Test(){
        String floorNo = "1";
        String id = "2";
        int result = estimateDao.checkByFloorNo1(floorNo,id);
        Assert.assertEquals("结果为0",0, result);
    }
}
