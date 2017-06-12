/**    
 * @Title:  AccountDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    AccountDao测试类   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:20:39   
 * @version V1.0     
 */  
package com.sva.test.dao;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import com.sva.dao.AccountDao;
import com.sva.model.AccountModel;
import com.sva.model.RoleModel;

import org.junit.Assert;

/**   
 * @ClassName:  AccountDaoTest   
 * @Description:AccountDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class AccountDaoTest extends BasicDaoTest {
    
    @Resource
    AccountDao accountDao;
    
    @Test
    public void selectStoreTest(){
        String username = "admin";
        List<String> result = accountDao.selectStore(username);
        Assert.assertEquals("结果为0",1, result.size());
    }

    @Test
    public void selMenuKeyTest(){
        String username = "admin";
        List<String> result = accountDao.selMenuKey(username);
        Assert.assertEquals("结果为1",1, result.size());
    }
    
    @Test
    public void doqueryTest(){
        List<AccountModel> result = accountDao.doquery();
        Assert.assertNotNull(result);
    }
    
    @Test
    public void selectCategorySameTest(){
        String name = "admin";
        int id = 1;
        int result = accountDao.selectCategorySame(name, id);
        Assert.assertEquals(1, result);
        Assert.assertNotNull(result);
    }
    
    @Test
    public void saveInfoTest(){
        AccountModel am = new AccountModel();
        RoleModel rm = new RoleModel();
        rm.setId(3);
        am.setUsername("test");
        am.setPassword("test");
        am.setRole(rm);
        am.setUpdateTime(new Date());
        
        int result = accountDao.saveInfo(am);
        Assert.assertEquals("结果为1",1, result);
    }
    
    @Test
    public void updateInfoTest(){
        AccountModel am = new AccountModel();
        RoleModel rm = new RoleModel();
        rm.setId(3);
        am.setUsername("test");
        am.setPassword("test");
        am.setRole(rm);
        am.setUpdateTime(new Date());
        am.setId(3);
        
        int result = accountDao.updateInfo(am);
        Assert.assertEquals(1, result);
    }
    
    @Test
    public void deleteByIdTest(){
        String id = "3";
        int result = accountDao.deleteById(id);
        
        Assert.assertEquals("结果为1",1, result);
        
    }
    
    @Test
    public void findUserTest(){
        String username = "admin";
        String password = "admin";
        List<AccountModel> result = accountDao.findUser(username, password);
        Assert.assertNotNull(result);
    }
}
