package com.sva.test.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import com.sva.dao.SvaDao;
import com.sva.model.SvaModel;

public class SvaDaoTest extends BasicDaoTest{
    
    @Resource
    SvaDao dao;
    
    @Test
    public void doqueryTest()
    {
        List<SvaModel> reslut = dao.doquery();
        Assert.assertEquals("relsut 0", 0, reslut.size());
    }

    
    @Test
    public void queryByFloorNoTest()
    {
        String floorNo = "1";
        List<SvaModel> reslut = dao.queryByFloorNo(floorNo);
        Assert.assertNotEquals("relsut 0", 0, reslut.size());
    }
    
    @Test
    public void queryActiveTest()
    {
        List<SvaModel> reslut = dao.queryActive();
        Assert.assertEquals("relsut 0", 0, reslut.size());
    }
    
    @Test
    public void storeBySvaTest()
    {
        String svaId = "1";
        List<String> reslut = dao.storeBySva(svaId);
        Assert.assertEquals("relsut 0", 0, reslut.size());
    }
    
    @Test
    public void storeIdByNameTest()
    {
        String name = "13990069977";
        String reslut = dao.storeIdByName(name);
        Assert.assertNull("relsut null", reslut);
    }
    
    @Test
    public void doqueryByAllTest()
    {
        List<SvaModel> reslut = dao.doqueryByAll();
        Assert.assertEquals("relsut 0", 0, reslut.size());
    }
    
    @Test
    public void queryByStoreIdTest()
    {
        String storeId = "1";
        List<SvaModel> reslut = dao.queryByStoreId(storeId);
        Assert.assertEquals("relsut 0", 0,reslut.size());
    }
    
    @Test
    public void queryByStoreId2Test()
    {
        String storeId = "1";
        List<SvaModel> reslut = dao.queryByStoreId2(storeId);
        Assert.assertEquals("relsut 0", 0,reslut.size());
    }
    
    @Test
    public void getEnabledTest()
    {
        List<SvaModel> reslut = dao.getEnabled();
        Assert.assertEquals("relsut 0", 0, reslut.size());
    }
    
    @Test
    public void saveSvaInfoTest()
    {
        SvaModel sm = new SvaModel();
        sm.setBrokerPort("4703");
        sm.setIdType("IP");
        sm.setIp("0.0.0.0");
        sm.setName("U1");
        sm.setPassword("User@123456");
        sm.setStatus(0);
        sm.setTokenPort("9001");
        sm.setType(1);
        sm.setUsername("app1");
        int reslut = dao.saveSvaInfo(sm);
        Assert.assertEquals("relsut 1", 1, reslut);
    }
    
    @Test
    public void updateSvaInfoTest()
    {
        SvaModel sm = new SvaModel();
        sm.setBrokerPort("4703");
        sm.setIdType("IP");
        sm.setIp("0.0.0.0");
        sm.setName("U1");
        sm.setPassword("User@123456");
        sm.setStatus(0);
        sm.setTokenPort("9001");
        sm.setType(1);
        sm.setUsername("app1");
        sm.setId("1");
        int reslut = dao.updateSvaInfo(sm);
        Assert.assertEquals("relsut 0", 0, reslut);
    }
    
    @Test
    public void disableSvaTest()
    {
        String id = "1";
        int reslut = dao.disableSva(id);
        Assert.assertEquals("relsut 0", 0, reslut);
    }
    
    @Test
    public void enableSvaTest()
    {
        String id = "1";
        int reslut = dao.enableSva(id);
        Assert.assertEquals("relsut 0", 0, reslut);
    }
    
    @Test
    public void deleteSvaTempTest()
    {
        String id = "1";
        int reslut = dao.deleteSvaTemp(id);
        Assert.assertEquals("relsut 0", 0, reslut);
    }
    
    @Test
    public void deleteMapBySvaTest()
    {
        int id = 1;
        int reslut = dao.deleteMapBySva(id);
        Assert.assertEquals("relsut 0", 0, reslut);
    }
    
    @Test
    public void deleteSvaTest()
    {
        String id = "1";
        int reslut = dao.deleteSva(id);
        Assert.assertEquals("relsut 0", 0, reslut);
    }
    
    @Test
    public void addMapperTest()
    {
        int id = 1;
        List<String> stores = new ArrayList<String>();
        stores.add("2");
        stores.add("3");
        stores.add("1");
        int reslut = dao.addMapper(id, stores);
        Assert.assertNotEquals("relsut not 0", 0, reslut);
    }
    
    @Test
    public void selectIDTest()
    {
        String svaName = "U1";
        List<SvaModel> reslut = dao.selectID(svaName);
        Assert.assertEquals("relsut 0", 0, reslut.size());
    }
    
    @Test
    public void selectID1Test()
    {
        String svaName = "U1";
        String id = "1";
        List<SvaModel> reslut = dao.selectID1(svaName, id);
        Assert.assertEquals("relsut 0", 0, reslut.size());
    }
    
    @Test
    public void checkSvaByNameTest()
    {
        String svaName = "U1";
        int reslut = dao.checkSvaByName(svaName);
        Assert.assertEquals("relsut 0", 0, reslut);
    }
    
    @Test
    public void checkSvaByName1Test()
    {
        String svaName = "U1";
        String id = "1";
        int reslut = dao.checkSvaByName1(svaName, id);
        Assert.assertEquals("relsut 0", 0, reslut);
    }
}
