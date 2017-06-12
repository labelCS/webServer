/**    
 * @Title:  BZParamsDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    BZParamsDao测试类   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:20:39   
 * @version V1.0     
 */  
package com.sva.test.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.junit.Test;

import com.sva.dao.BZParamsDao;
import com.sva.model.BZPramesModel;
import com.sva.model.BZPramesModel1;
import org.junit.Assert;

/**   
 * @ClassName:  BZParamsDaoTest   
 * @Description:BZParamsDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class BZParamsDaoTest extends BasicDaoTest {

    @Resource
    BZParamsDao bZParamsDao;
    
    @Test
    public void doqueryTest(){
        List<BZPramesModel> result = bZParamsDao.doquery();
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void doquery3Test(){
        List<BZPramesModel> result = bZParamsDao.doquery3();
        Assert.assertNotEquals("结果不为0",0, result.size());
    }
    
    @Test
    public void doquery4Test(){
        List<BZPramesModel> result = bZParamsDao.doquery4();
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void doquery1Test(){
        List<BZPramesModel> result = bZParamsDao.doquery1();
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void updateBZInfoTest(){
        BZPramesModel mmm = new BZPramesModel();
        mmm.setDensitySel(1);
        mmm.setDensitySel1(1);
        mmm.setDensitySel2(1);
        mmm.setDensitySel3(1);
        mmm.setDensitySel4(1);
        mmm.setDensitySel5(1);
        mmm.setDensitySel6(1);
        mmm.setDensitySel7(1);
        mmm.setRadiusSel(1);
        mmm.setRadiusSel1(1);
        mmm.setRadiusSel2(1);
        mmm.setRadiusSel3(1);
        mmm.setRadiusSel4(1);
        mmm.setRadiusSel5(1);
        mmm.setRadiusSel6(1);
        mmm.setRadiusSel7(1);
        mmm.setFloorNo1(new BigDecimal(1));
        mmm.setFloorNo2(new BigDecimal(1));
        mmm.setFloorNo3(new BigDecimal(1));
        mmm.setFloorNo4(new BigDecimal(1));
        mmm.setFloorNo5(new BigDecimal(1));
        mmm.setFloorNo6(new BigDecimal(1));
        mmm.setFloorNo7(new BigDecimal(1));
        mmm.setFloorNo8(new BigDecimal(1));
        mmm.setCoefficient(1D);
        mmm.setPeriodSel(1);
        mmm.setStartTime(new Date());
        mmm.setId(1);
        int result = bZParamsDao.updateBZInfo(mmm);
        Assert.assertEquals("结果为1",1, result);
    }
    
    @Test
    public void updateSHInfoTest(){
        BZPramesModel mmm = new BZPramesModel();
        mmm.setDensitySel(1);
        mmm.setDensitySel1(1);
        mmm.setDensitySel2(1);
        mmm.setRadiusSel(1);
        mmm.setRadiusSel1(1);
        mmm.setRadiusSel2(1);
        mmm.setFloorNo1(new BigDecimal(1));
        mmm.setFloorNo2(new BigDecimal(1));
        mmm.setFloorNo3(new BigDecimal(1));
        mmm.setCoefficient(1D);
        mmm.setPeriodSel(1);
        mmm.setStartTime(new Date());
        mmm.setId(1);
        int result = bZParamsDao.updateSHInfo(mmm);
        Assert.assertNotEquals("结果不为0",0, result);
    }
    
    @Test
    public void updateSHInfoJingTest(){
        BZPramesModel mmm = new BZPramesModel();
        mmm.setDensitySel(1);
        mmm.setDensitySel1(1);
        mmm.setDensitySel2(1);
        mmm.setDensitySel3(1);
        mmm.setRadiusSel(1);
        mmm.setRadiusSel1(1);
        mmm.setRadiusSel2(1);
        mmm.setRadiusSel3(1);
        mmm.setFloorNo1(new BigDecimal(1));
        mmm.setFloorNo2(new BigDecimal(1));
        mmm.setFloorNo3(new BigDecimal(1));
        mmm.setFloorNo4(new BigDecimal(1));
        mmm.setCoefficient(1D);
        mmm.setPeriodSel(1);
        mmm.setStartTime(new Date());
        mmm.setId(1);
        int result = bZParamsDao.updateSHInfoJing(mmm);
        Assert.assertNotEquals("结果不为0",0, result);
    }
    
    @Test
    public void getBzDataTest(){
        String placeId = "1";
        List<Map<String, Object>> result = bZParamsDao.getBzData(placeId);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getBzAllDataTest(){
        String placeId = "1";
        List<Map<String, Object>> result = bZParamsDao.getBzAllData(placeId);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getAllFloorNoTest(){
        String id = "1";
        List<Map<String, Object>> result = bZParamsDao.getAllFloorNo(id);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getAllFloorNo2Test(){
        String id = "111";
        List<Map<String, Object>> result = bZParamsDao.getAllFloorNo2(id);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getAllFloorNoJingTest(){
        String id = "1";
        List<Map<String, Object>> result = bZParamsDao.getAllFloorNoJing(id);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void doquery2Test(){
        List<BZPramesModel1> result = bZParamsDao.doquery2();
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void updateBZInfo1Test(){
        BZPramesModel1 mmm = new BZPramesModel1();
        mmm.setDensitySel(1);
        mmm.setDensitySel1(1);
        mmm.setDensitySel2(1);
        mmm.setRadiusSel(1);
        mmm.setRadiusSel1(1);
        mmm.setRadiusSel2(1);
        mmm.setPlaceId(1);
        mmm.setPlaceId2(2);
        mmm.setPlaceId2sp(2);
        mmm.setPlaceId3(3);
        mmm.setPlaceId3sp(3);
        mmm.setFloorNo(new BigDecimal(1));
        mmm.setFloorNo2(new BigDecimal(2));
        mmm.setFloorNo2sp(new BigDecimal(2));
        mmm.setFloorNo3(new BigDecimal(3));
        mmm.setFloorNo3sp(new BigDecimal(3));
        mmm.setCoefficient(1D);
        mmm.setPeriodSel(1);
        mmm.setStartTime(new Date());
        mmm.setId(1);
        int result = bZParamsDao.updateBZInfo1(mmm);
        Assert.assertNotEquals("结果不为0",0, result);
    }
}
