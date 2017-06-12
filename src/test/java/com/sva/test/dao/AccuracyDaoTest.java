/**   
 * @Title: AccuracyDaoTest.java 
 * @Package com.sva.test.dao 
 * @Description: AccuracyDao测试类 
 * @author labelCS   
 * @date 2016年9月14日 下午3:57:13 
 * @version V1.0   
 */
package com.sva.test.dao;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import com.sva.dao.AccuracyDao;
import com.sva.model.AccuracyModel;
import com.sva.model.MapsModel;
import com.sva.model.StoreModel;

/** 
 * @ClassName: AccuracyDaoTest 
 * @Description: AccuracyDao测试类 
 * @author labelCS 
 * @date 2016年9月14日 下午3:57:13 
 *  
 */
public class AccuracyDaoTest extends BasicDaoTest {

    @Resource
    AccuracyDao accuracyDao;
    
    @Test
    public void getDataTest() {
        int start = 1;
        int length = 10;
        String sortCol = "offset";
        String sortDir = "desc";
        List<AccuracyModel> result = accuracyDao.getData(start, length, sortCol, sortDir);
        Assert.assertNotNull(result);
    }
    
    @Test
    public void getDataByStoreidTest() {
        int start = 1;
        int length = 10;
        String sortCol = "offset";
        String sortDir = "desc";
        int storeid = 1;
        List<AccuracyModel> result = accuracyDao.getDataByStoreid(start, length, sortCol, sortDir, storeid);
        Assert.assertNotNull(result);
    }
    
    @Test
    public void getDataLengthTest(){
        int result = accuracyDao.getDataLength();
        Assert.assertNotEquals("结果不为0",0, result);
    }
    
    @Test
    public void allQueryAccuracyTest(){
        List<AccuracyModel> result = accuracyDao.allQueryAccuracy();
        Assert.assertNotNull(result);
    }
    
    @Test
    public void saveTestInfoTest(){
        AccuracyModel am = new AccuracyModel();
        StoreModel sm = new StoreModel();
        MapsModel mm = new MapsModel();
        sm.setId(1);
        mm.setFloorNo(new BigDecimal(1));
        am.setMaps(mm);
        am.setStore(sm);
        am.setAverDevi(new BigDecimal(1));
        am.setCount10(1);
        am.setCount10p(1);
        am.setCount3(1);
        am.setCount5(1);
        am.setDestination("1,2");
        am.setDetail("ok");
        am.setEnddate(new Date());
        am.setOffset(new BigDecimal(1));
        am.setOrigin("0,0");
        am.setStartdate(new Date());
        am.setTriggerIp("0.0.0.0");
        am.setType("static");
        am.setVariance(new BigDecimal(1));
        
        int result = accuracyDao.saveTestInfo(am);
        Assert.assertEquals("结果为1",1, result);
    }

}
