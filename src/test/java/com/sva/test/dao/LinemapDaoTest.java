/**    
 * @Title:  LinemapDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    LinemapDao测试类   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:20:39   
 * @version V1.0     
 */  
package com.sva.test.dao;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import com.sva.dao.LinemapDao;
import com.sva.model.LinemapModel;

import org.junit.Assert;

/**   
 * @ClassName:  LinemapDaoTest   
 * @Description: LinemapDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class LinemapDaoTest extends BasicDaoTest {
    
    @Resource
    LinemapDao linemapDao;
    
    @Test
    public void getDataByDayTest(){
        String placeId = "1";
        String start = "1";
        String end = "1";
        List<LinemapModel> result = linemapDao.getDataByDay(placeId,start,end);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getDataByHourTest(){
        String placeId = "1";
        String start = "1";
        String end = "1";
        List<LinemapModel> result = linemapDao.getDataByHour(placeId,start,end);
        Assert.assertEquals("结果为0", 0, result.size());
    }
    
    @Test
    public void getTotalCountTest(){
        String placeId = "1";
        String start = "1";
        String end = "1";
        int result = linemapDao.getTotalCount(placeId,start,end);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void getMaxTimeTest(){
        String placeId = "1";
        String start = "1";
        String end = "1";
        List<LinemapModel> result = linemapDao.getMaxTime(placeId,start,end);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getMaxDayTest(){
        String placeId = "1";
        String start = "1";
        String end = "1";
        List<LinemapModel> result = linemapDao.getMaxDay(placeId,start,end);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getAreaNumberByDayTest(){
        int mapId = 10001;
        BigDecimal x = new BigDecimal(10001);
        BigDecimal x1 = new BigDecimal(10001);
        BigDecimal y = new BigDecimal(10001);
        BigDecimal y1 = new BigDecimal(10001);
        String tableName = "location20161111";
        int result = linemapDao.getAreaNumberByDay(mapId,tableName,x,x1,y,y1);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void getAreaNumberByHourTest(){
        int mapId = 10001;
        BigDecimal x = new BigDecimal(10001);
        BigDecimal x1 = new BigDecimal(10001);
        BigDecimal y = new BigDecimal(10001);
        BigDecimal y1 = new BigDecimal(10001);
        String tableName = "location20161111";
        long time = 1;
        int result = linemapDao.getAreaNumberByHour(mapId,tableName,time,x,x1,y,y1);
        Assert.assertEquals("结果为0",0, result);
    }
}
