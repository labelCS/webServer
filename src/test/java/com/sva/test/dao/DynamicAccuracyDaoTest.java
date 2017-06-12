/**    
 * @Title:  DynamicAccuracyDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    DynamicAccuracyDao测试类   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:20:39   
 * @version V1.0     
 */  
package com.sva.test.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.junit.Test;
import com.sva.dao.DynamicAccuracyDao;
import com.sva.model.DynamicAccuracyModel;
import com.sva.model.MapsModel;
import com.sva.model.StoreModel;
import org.junit.Assert;

/**   
 * @ClassName:  DynamicAccuracyDaoTest   
 * @Description: DynamicAccuracyDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class DynamicAccuracyDaoTest extends BasicDaoTest {
    @Resource
    DynamicAccuracyDao dynamicAccuracyDao;
    
    @Test
    public void doqueryTest(){
        int start = 1;
        int length = 10;
        String sortCol = "offset";
        String sortDir = "desc";
        List<DynamicAccuracyModel> result = dynamicAccuracyDao.getData(start, length, sortCol, sortDir);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getDataByPlaceIdTimeTest(){
        String start = "20160505";
        String end = "20160507";
        String placeId = "1";
        List<DynamicAccuracyModel> result = dynamicAccuracyDao.getDataByPlaceIdTime(start, end, placeId);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getAllDataByPlaceIdTimeTest(){
        String start = "20160505";
        String end = "20160507";
        String placeId = "1";
        List<DynamicAccuracyModel> result = dynamicAccuracyDao.getAllDataByPlaceIdTime(placeId, start, end);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getAllDataTest(){
        String start = "20160505";
        String end = "20160507";
        List<DynamicAccuracyModel> result = dynamicAccuracyDao.getAllData(start, end);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getStaticDataByStoreidTest(){
        int start = 1;
        int length = 10;
        String sortCol = "offset";
        String sortDir = "desc";
        int placeId = 1;
        List<DynamicAccuracyModel> result = 
                dynamicAccuracyDao.getStaticDataByStoreid(start, length, sortCol, sortDir, placeId);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getDataLengthTest(){
        int result = dynamicAccuracyDao.getDataLength();
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void dynamicSaveTestInfoTest(){
        DynamicAccuracyModel dam = new DynamicAccuracyModel();
        dam.setAvgeOffset(new BigDecimal(1));
        dam.setCount10(1);
        dam.setCount10p(1);
        dam.setCount3(1);
        dam.setCount5(1);
        dam.setDestination("x");
        dam.setDetail("ok");
        dam.setDeviation(new BigDecimal(1));
        dam.setStartDate(new Date());
        dam.setEndDate(new Date());
        dam.setMaxOffset(new BigDecimal(1));
        dam.setOffset(new BigDecimal(1));
        dam.setOrigin("xo");
        dam.setTriggerIp("0.0.0.0");
        MapsModel maps = new MapsModel();
        maps.setFloorNo(new BigDecimal(1));
        StoreModel store = new StoreModel();
        store.setId(1);
        dam.setMaps(maps);
        dam.setStore(store);
        
        int result = dynamicAccuracyDao.dynamicSaveTestInfo(dam);
        Assert.assertEquals("结果为1",1, result);
    }
    
    @Test
    public void allQueryDynamicAccuracyTest(){
        List<DynamicAccuracyModel> result = dynamicAccuracyDao.allQueryDynamicAccuracy();
        Assert.assertEquals("结果为0",0, result.size());
    }
}
