package com.sva.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.sva.dao.VisitDao;
import com.sva.model.VisitModel;

public class VisitDaoTest extends BasicDaoTest{

    @Resource
    VisitDao dao;
    
    @Test
    public void getIpExistenceTest(){
        List<VisitModel> lis = dao.getIpExistence("10.10.10.10");
        Assert.assertEquals("result 0",0,lis.size());
    }
    
    @Test
    public void getAllCountTest(){
        List<String> lis = dao.getAllCount();
        Assert.assertNotEquals("result not 0",0, lis.size());
    }
    
    @Test
    public void updateDataTest(){
        VisitModel vi = new VisitModel();
        long time = System.currentTimeMillis();
        vi.setFirstVisitTime(time);
        vi.setIp("10.1.1.1");
        vi.setLastVisitTime(time);
        vi.setUserName("admin");
        vi.setVisitCount(1);
        int result = dao.updateData(vi);
        Assert.assertEquals("reslut 0", 0, result);
    }
    
    @Test
    public void saveDataTest(){
        VisitModel vi = new VisitModel();
        long time = System.currentTimeMillis();
        vi.setFirstVisitTime(time);
        vi.setIp("111.111.111.111");
        vi.setLastVisitTime(time);
        vi.setUserName("admin");
        vi.setVisitCount(1);
        int result = dao.saveData(vi);
        Assert.assertEquals("reslut 1", 1, result);
        
    }
}
