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
import com.sva.dao.PetLocationDao;
import com.sva.model.PetLocationModel;

import org.junit.Assert;

/**   
 * @ClassName:  PetAttributesDaoTest   
 * @Description: PetAttributesDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class PetLocationDaoTest extends BasicDaoTest {
    
    @Resource
    PetLocationDao petLocationDao;
    
    @Test
    public void getAllDataTest(){
        List<PetLocationModel> result = petLocationDao.getAllData();
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getDataByPetTest(){
        String floorNo = "1";
        String petName = "pika";
        List<PetLocationModel> result = petLocationDao.getDataByPet(floorNo, petName);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getPetDataByPositionTest(){
        int floorNo = 1;
        List<PetLocationModel> result = petLocationDao.getPetDataByPosition(floorNo);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getMaxPetTimeTest(){
        long result = petLocationDao.getMaxPetTime();
        Assert.assertEquals("结果为0",0, result);
    }
}
