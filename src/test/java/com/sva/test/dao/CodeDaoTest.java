/**    
 * @Title:  CodeDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    CodeDao测试类   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:20:39   
 * @version V1.0     
 */  
package com.sva.test.dao;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import com.sva.dao.CodeDao;
import com.sva.model.CodeModel;
import org.junit.Assert;

/**   
 * @ClassName:  CodeDaoTest   
 * @Description: CodeDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class CodeDaoTest extends BasicDaoTest {
    @Resource
    CodeDao codeDao;
    
    @Test
    public void getDataTest(){
        List<CodeModel> result = codeDao.getData();
        Assert.assertNotEquals("结果不为0",0, result.size());
    }
    
    @Test
    public void saveCodeInfoTest(){
        CodeModel cm = new CodeModel();
        cm.setName("aaa");
        cm.setPassword("1234");
        int result = codeDao.saveCodeInfo(cm);
        Assert.assertNotEquals("结果不为",0, result);
    }
    
    @Test
    public void deleteCodeTest(){
        String name = "1";
        String password = "aa";
        int result = codeDao.deleteCode(name, password);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void checkIsValidTest(){
        String name = "1";
        String password = "aa";
        int result = codeDao.checkIsValid(name, password);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void checkByNameTest(){
        String name = "1";
        int result = codeDao.checkByName(name);
        Assert.assertEquals("结果为0",0, result);
    }
}
