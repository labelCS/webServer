/**    
 * @Title:  CategoryDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    CategoryDao测试类   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:20:39   
 * @version V1.0     
 */  
package com.sva.test.dao;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import com.sva.dao.CategoryDao;
import com.sva.model.CategoryModel;
import org.junit.Assert;

/**   
 * @ClassName:  CategoryDaoTest   
 * @Description: CategoryDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class CategoryDaoTest extends BasicDaoTest {
    @Resource
    CategoryDao categoryDao;
    
    @Test
    public void doqueryTest(){
        List<CategoryModel> result = categoryDao.doquery();
        Assert.assertNotEquals("结果不为0",0, result.size());
    }
    
    @Test
    public void selectCategorySameTest(){
        String name = "aa";
        int id = 1;
        int result = categoryDao.selectCategorySame(name, id);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void saveInfoTest(){
        CategoryModel cm = new CategoryModel();
        cm.setName("aa");
        cm.setCreateTime(new Date());
        cm.setUpdateTime(new Date());
        int result = categoryDao.saveInfo(cm);
        Assert.assertNotEquals("结果不为0",0, result);
    }
    
    @Test
    public void updateInfoTest(){
        CategoryModel cm = new CategoryModel();
        cm.setName("aa");
        cm.setId(1);
        cm.setUpdateTime(new Date());
        int result = categoryDao.updateInfo(cm);
        Assert.assertNotEquals("结果不为0",0, result);
    }
    
    @Test
    public void deleteByIdTest(){
        String id = "1";
        int result = categoryDao.deleteById(id);
        Assert.assertEquals("结果为1",1, result);
    }
}
