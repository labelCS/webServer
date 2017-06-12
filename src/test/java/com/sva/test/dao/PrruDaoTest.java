package com.sva.test.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import com.sva.dao.PrruDao;
import com.sva.model.PrruModel;

public class PrruDaoTest extends BasicDaoTest{

    @Resource
    PrruDao dao;
    
    @Test
    public void getPrruInfoByflooNoTest(){
        String floorNo = "1";
        List<PrruModel> lis = dao.getPrruInfoByflooNo(floorNo);
        Assert.assertEquals("result 0",0,lis.size());
    }
    
    @Test
    public void saveInfoTest(){
        PrruModel model = new PrruModel();
        model.setFloorNo("1");
        model.setNeCode("222");
        model.setNeId("1");
        model.setNeName("name");
        model.setPlaceId(1);
        model.setX("11");
        model.setY("2");
        int result = dao.saveInfo(model);
        Assert.assertEquals("result 1",1,result);
    }
    
    @Test
    public void deleteInfoTest(){
        String floorNo = "1";
        String eNodeBid = "59804";
        int result = dao.deleteInfo(floorNo,eNodeBid);
        Assert.assertEquals("result 0",0,result);
    }
    
    @Test
    public void checkByFloorNoTest(){
        String floorNo = "1";
        String id = "2";
        int lis = dao.checkByFloorNo(floorNo, id);
        Assert.assertEquals("result 0",0,lis);
    }
    
    @Test
    public void updateInfoTest(){
        String floorNo = "1";
        String newFloorNo = "2";
        int result = dao.updateInfo(floorNo, newFloorNo);
        Assert.assertEquals("result 0",0,result);
    }
    
    @Test
    public void getSignalTest(){
//        List<Map<String, Object>> lis = dao.getSignal();
//        Assert.assertNotEquals("result not 0",0,lis.size());
    }
}
