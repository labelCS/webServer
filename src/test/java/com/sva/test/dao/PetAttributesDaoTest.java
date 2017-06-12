/**    
 * @Title:  PetAttributesDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    PetAttributesDao测试类   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:20:39   
 * @version V1.0     
 */  
package com.sva.test.dao;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import com.sva.dao.PetAttributesDao;
import com.sva.model.PetAttributesModel;
import org.junit.Assert;

/**   
 * @ClassName:  PetAttributesDaoTest   
 * @Description: PetAttributesDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class PetAttributesDaoTest extends BasicDaoTest {
    
    @Resource
    PetAttributesDao petAttributesDaoDao;
    
    @Test
    public void getAllDataTest(){
        List<PetAttributesModel> result = petAttributesDaoDao.getAllData();
        Assert.assertEquals("结果为0",0, result.size());
    }

    @Test
    public void saveDataTest(){
        PetAttributesModel attr = new PetAttributesModel();
        attr.setPetName("pika");
        attr.setViewRange(2.5D);
        attr.setCaptureRange(3.0D);
        attr.setProbability(2.0D);
        attr.setCount(2);
        int result = petAttributesDaoDao.saveData(attr);
        Assert.assertEquals("结果为1",1, result);
    }
    
    @Test
    public void updateDataTest(){
        PetAttributesModel attr = new PetAttributesModel();
        attr.setPetName("pika");
        attr.setViewRange(2.5D);
        attr.setCaptureRange(3.0D);
        attr.setProbability(2.0D);
        attr.setCount(2);
        attr.setId(1);
        int result = petAttributesDaoDao.updateData(attr);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void deleteDataTest(){
        String id = "1";
        int result = petAttributesDaoDao.deleteData(id);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void updatePetTimeTest(){
        String petName = "pika";
        long petTime = 1231231313L;
        int result = petAttributesDaoDao.updatePetTime(petName, petTime);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void updatePetTimeByCaputrueTest(){
        long petTime = 1231231313L;
        int result = petAttributesDaoDao.updatePetTimeByCaputrue(petTime);
        Assert.assertEquals("结果为0",0, result);
    }
}
