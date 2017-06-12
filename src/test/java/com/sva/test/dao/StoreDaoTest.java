package com.sva.test.dao;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import com.sva.dao.StoreDao;
import com.sva.model.StoreModel;

public class StoreDaoTest extends BasicDaoTest{
    
    @Resource
    StoreDao dao;
    
    @Test
    public void doqueryTest()
    {
        String id = "1";
        List<StoreModel> reslut = dao.doquery(id);
        Assert.assertNotEquals("relsut not 0", 0, reslut.size());
    }

    
    @Test
    public void doStoresTest()
    {
        List<StoreModel> reslut = dao.doStores();
        Assert.assertNotEquals("relsut not 0", 0, reslut.size());
    }
    
    @Test
    public void getStoreNameBySvaTest()
    {
        int svaId = 11;
        List<String> reslut = dao.getStoreNameBySva(svaId);
        Assert.assertEquals("relsut 0", 0,reslut.size());
    }
    
    @Test
    public void saveInfoTest()
    {
        StoreModel sm = new StoreModel();
        sm.setName("HM");
        sm.setCreateTime(new Date());
        sm.setUpdateTime(new Date());
        int reslut = dao.saveInfo(sm);
        Assert.assertEquals("relsut 1", 1,reslut);
    }
    
    @Test
    public void selectStoreidTest()
    {
        String name = "13990069977";
        Integer reslut = dao.selectStoreid(name);
        Assert.assertNull("relsut null", reslut);
    }
    
    @Test
    public void selectStoreSameTest()
    {
        String name = "13990069977";
        int id = 1;
        int reslut = dao.selectStoreSame(name, id);
        Assert.assertEquals("relsut 0", 0,reslut);
    }
    
    @Test
    public void selectStoreSame1Test()
    {
        String name = "13990069977";
        int reslut = dao.selectStoreSame1(name);
        Assert.assertEquals("relsut 0", 0,reslut);
    }    
    
    @Test
    public void updateInfoTest()
    {
        StoreModel sm = new StoreModel();
        sm.setName("HM");
        sm.setUpdateTime(new Date());
        int reslut = dao.updateInfo(sm);
        Assert.assertEquals("relsut 0", 0,reslut);
    }  
    
    @Test
    public void deleteByIdTest()
    {
        String id = "1";
        int reslut = dao.deleteById(id);
        Assert.assertEquals("relsut 1", 1, reslut);
    }  
}
