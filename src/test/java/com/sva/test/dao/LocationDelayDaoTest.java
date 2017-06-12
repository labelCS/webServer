/**    
 * @Title:  LocationDelayDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    LocationDelayDao测试类   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:20:39   
 * @version V1.0     
 */  
package com.sva.test.dao;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import com.sva.dao.LocationDelayDao;
import com.sva.model.LocationDelayModel;
import com.sva.model.MapsModel;
import com.sva.model.StoreModel;

import org.junit.Assert;

/**   
 * @ClassName:  LocationDelayDaoTest   
 * @Description: LocationDelayDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class LocationDelayDaoTest extends BasicDaoTest {
    
    @Resource
    LocationDelayDao locationDelayDao;
    
    @Test
    public void getAllDataTest(){
        List<LocationDelayModel> result = locationDelayDao.getAllData();
        Assert.assertEquals("结果为0",0, result.size());
    }

    @Test
    public void getAllDatasTest(){
        long startTime = 1L;
        long endTime = 1476925335001L;
        List<LocationDelayModel> result = locationDelayDao.getAllDatas(startTime, endTime);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getDataByPlaceIdTimeTest(){
        String placeId = "1";
        long startTime = 1L;
        long endTime = 2L;
        List<LocationDelayModel> result = locationDelayDao.getDataByPlaceIdTime(startTime, endTime, placeId);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getAllDataByStoreidTest(){
        int storeid = 1;
        List<LocationDelayModel> result = locationDelayDao.getAllDataByStoreid(storeid);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void savaMessagePushTest(){
        LocationDelayModel ldm = new LocationDelayModel();
        ldm.setDataDelay(2D);
        ldm.setPositionDelay(2D);
        ldm.setUpdateTime(222L);
        MapsModel maps = new MapsModel();
        maps.setFloorNo(new BigDecimal(1));
        ldm.setMaps(maps);
        StoreModel store = new StoreModel();
        store.setId(1);
        ldm.setStore(store);
        int result = locationDelayDao.savaMessagePush(ldm);
        Assert.assertEquals("结果为1",1, result);
    }
}
