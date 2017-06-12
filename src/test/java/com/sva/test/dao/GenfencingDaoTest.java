/**    
 * @Title:  GeofencingDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    GeofencingDao测试类   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:20:39   
 * @version V1.0     
 */  
package com.sva.test.dao;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import com.sva.dao.GeofencingDao;
import com.sva.model.GeofencingModel;
import org.junit.Assert;

/**   
 * @ClassName:  GeofencingDaoTest   
 * @Description: GeofencingDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class GenfencingDaoTest extends BasicDaoTest {
    
    @Resource
    GeofencingDao geofencingDao;
    
    @Test
    public void getGeofencingTest(){
        String zoneid = "zoneid";
        String userid = "userid";
        List<GeofencingModel> result = geofencingDao.getGeofencing(zoneid, userid);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getGeofencingByZoneIdTest(){
        String zoneid = "zoneid";
        List<GeofencingModel> result = geofencingDao.getGeofencingByZoneId(zoneid);
        Assert.assertEquals("结果为0",0, result.size());
    }
}
