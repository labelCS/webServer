package com.sva.test.dao;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import com.sva.dao.MessagePushDao;
import com.sva.model.MapsModel;
import com.sva.model.MessagePushModel;
import com.sva.model.StoreModel;

public class MessagePushDaoTest extends BasicDaoTest{

    @Resource
    MessagePushDao dao;
    
    @Test
    public void getDataByPlaceIdTimeTest(){
        long startTime = 11111L;
        long endTime = 111111111111L;
        String placeId = "1";
        List<MessagePushModel> lis = dao.getDataByPlaceIdTime(startTime,endTime,placeId);
        Assert.assertEquals("result 0",0,lis.size());
    }
    
    @Test
    public void getAllDatasTest(){
        long startTime = 11111L;
        long endTime = 111111111111L;
        List<MessagePushModel> lis = dao.getAllDatas(startTime,endTime);
        Assert.assertEquals("result 0",0,lis.size());
    }
    
    @Test
    public void getAllDataTest(){
        List<MessagePushModel> lis = dao.getAllData();
        Assert.assertEquals("result 0",0,lis.size());
    }
    
    @Test
    public void getAllDataByStoreIdTest(){
        int storeid = 1;
        List<MessagePushModel> lis = dao.getAllDataByStoreId(storeid);
        Assert.assertEquals("result 0",0,lis.size());
    }
    
    @Test
    public void savaMessagePushTest(){
        MessagePushModel model = new MessagePushModel();
        MapsModel map = new MapsModel();
        map.setFloorNo(new BigDecimal(1));
        map.setFloor("1F");
        model.setMap(map);
        StoreModel store = new StoreModel();
        store.setId(1);
        store.setName("Test");
        model.setStore(store);
        model.setCenterRadius("6");
        model.setCenterReality("6");
        model.setIsRigth(0);
        model.setNotPush(6.0D);
        model.setPushRight(2.0D);
        model.setPushWrong(3.0D);
        model.setUpdateTime(123123123L);
        int result = dao.savaMessagePush(model);
        Assert.assertEquals("result 1",1,result);
    }
}
