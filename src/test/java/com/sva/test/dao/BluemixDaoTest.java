/**    
 * @Title:  BluemixDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    BluemixDao测试类   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:20:39   
 * @version V1.0     
 */  
package com.sva.test.dao;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import com.sva.dao.BluemixDao;
import com.sva.model.BluemixModel;
import org.junit.Assert;

/**   
 * @ClassName:  BluemixDaoTest   
 * @Description:BluemixDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class BluemixDaoTest extends BasicDaoTest {
    @Resource
    BluemixDao bluemixDao;
    
    @Test
    public void doqueryTest(){
        List<BluemixModel> result = bluemixDao.doquery();
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void queryAllStatusTest(){
        List<BluemixModel> result = bluemixDao.queryAllStatus();
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void saveInfoTest(){
        BluemixModel bm = new BluemixModel();
        bm.setIp("10.10.10.10");
        bm.setSvaUser("user");
        bm.setSvaPassword("password");
        bm.setUrl("http://aa");
        bm.setSite("bbb");
        bm.setIbmUser("app0");
        bm.setIbmPassword("ibmPassword");
        bm.setStatus(1);
        bm.setUpdateTime(new Date());
        bm.setCreateTime(new Date());
        bm.setTokenProt("4703");
        bm.setBrokerProt("4703");
        int result = bluemixDao.saveInfo(bm);
        Assert.assertNotEquals("结果不为0",0, result);
    }
    
    @Test
    public void updateInfoTest(){
        BluemixModel bm = new BluemixModel();
        bm.setIp("10.10.10.10");
        bm.setSvaUser("user");
        bm.setSvaPassword("password");
        bm.setUrl("http://aa");
        bm.setSite("bbb");
        bm.setIbmUser("app0");
        bm.setIbmPassword("ibmPassword");
        bm.setStatus(1);
        bm.setUpdateTime(new Date());
        bm.setCreateTime(new Date());
        bm.setTokenProt("4703");
        bm.setBrokerProt("4703");
        int result = bluemixDao.updateInfo(bm);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void deleteInfoTest(){
        String id = "1";
        int result = bluemixDao.deleteInfo(id);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void deleteByIdTest(){
        String id = "1";
        int result = bluemixDao.deleteById(id);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void changeStatusTest(){
        String id = "1";
        String status = "1";
        int result = bluemixDao.changeStatus(id, status);
        Assert.assertEquals("结果为0",0, result);
    }
}
