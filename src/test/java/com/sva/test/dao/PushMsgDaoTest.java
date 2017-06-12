package com.sva.test.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import com.sva.dao.PushMsgDao;

public class PushMsgDaoTest extends BasicDaoTest{

    @Resource
    PushMsgDao dao;
    
    @Test
    public void getPrruInfoByflooNoTest(){
        String userId = "1";
        List<Map<String, Object>> lis = dao.getMessageByUserId(userId);
        Assert.assertEquals("result 0",0,lis.size());
    }
    
    @Test
    public void saveMessageTest(){
        String userId = "11";
        String content = "ok";
        int result = dao.saveMessage(userId, content);
        Assert.assertEquals("result 1",1,result);
    }
    
    @Test
    public void deleteMessageByIdTest(){
        String floorNo = "1";
        int result = dao.deleteMessageById(floorNo);
        Assert.assertEquals("result 0",0,result);
    }
}
