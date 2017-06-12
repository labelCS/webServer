/**    
 * @Title:  CommonDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    CommonDao测试类   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:20:39   
 * @version V1.0     
 */  
package com.sva.test.dao;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.junit.Test;
import com.sva.dao.CommonDao;
import org.junit.Assert;

/**   
 * @ClassName:  CommonDaoTest   
 * @Description: CommonDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class CommonDaoTest extends BasicDaoTest {
    @Resource
    CommonDao commonDao;
    
    @Test
    public void createTableTest(){
        int result = commonDao.createTable("test");
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void isTableExistTest1(){
        int result = commonDao.isTableExist("maps","sva");
        Assert.assertEquals("结果为",1, result);
    }
    
    @Test
    public void isTableExistTest2(){
        int result = commonDao.isTableExist("maps","svaa");
        Assert.assertEquals("结果为",0, result);
    }
    
    @Test
    public void doUpdateTest(){
        String sql = "update account set username = 'a' where id = 3";
        int result = commonDao.doUpdate(sql);
        Assert.assertEquals("结果为1",1, result);
    }
    
    @Test
    public void doTestTest(){
        String sql = "select count(*) from account";
        int result = commonDao.doTest(sql);
        Assert.assertNotEquals("结果不为0",0, result);
    }
    
    @Test
    public void doTest1Test(){
        String sql = "select * from account";
        List<Map<String, Object>> result = commonDao.doTest1(sql);
        Assert.assertNotEquals("结果不为0",0, result.size());
    }
    
    @Test
    public void doDeleteInfoTest(){
        String time = "location20160830";
        List<String> result = commonDao.doDeleteInfo(time);
        Assert.assertNotEquals("结果不为0",0, result.size());
    }
    
    @Test
    public void deleteTableTest(){
        String tableName = "test";
        int result = commonDao.deleteTable(tableName);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void getStatisticTempTest(){
        List<Map<String, Object>> result = commonDao.getStatisticTemp();
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getDataTodayTest(){
        String time = "20160812";
        List<Map<String, Object>> result = commonDao.getDataToday(time);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getAllPeopleTest(){
        String tableName = "location20160830";
        long time = 1L;
        List<Map<String, Object>> result = commonDao.getAllPeople(tableName, time);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getAllAreaTest(){
        List<Map<String, Object>> result = commonDao.getAllArea();
        Assert.assertNotEquals("结果不为0",0, result.size());
    }
    
    @Test
    public void getAreaVisitTimeTest(){
        String areaId = "1";
        List<Map<String, Object>> result = commonDao.getAreaVisitTime(areaId);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void saveVisitiTimeTest(){
        String sql = "insert into store(name) values('aa')";
        int result = commonDao.saveVisitiTime(sql);
        Assert.assertNotEquals("结果不为0",0, result);
    }
}
