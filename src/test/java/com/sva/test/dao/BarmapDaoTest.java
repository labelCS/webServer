/**    
 * @Title:  BarmapDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    BarmapDao测试类   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:20:39   
 * @version V1.0     
 */  
package com.sva.test.dao;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import com.sva.dao.BarmapDao;
import com.sva.model.StatisticFloorModel;
import org.junit.Assert;

/**   
 * @ClassName:  BarmapDaoTest   
 * @Description:BarmapDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class BarmapDaoTest extends BasicDaoTest {
    
    @Resource
    BarmapDao barmapDao;
    
    @Test
    public void getDataTest(){
        String placeId = "1";
        String start = "1"; 
        String end = "1";
        List<StatisticFloorModel> result = barmapDao.getData(placeId,start,end);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getTotalCountTest(){
        String placeId = "1";
        String start = "1"; 
        String end = "1";
        int result = barmapDao.getTotalCount(placeId,start,end);
        Assert.assertEquals("结果为0",0, result);
    }
}
