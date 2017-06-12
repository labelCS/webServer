package com.sva.test.dao;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import com.sva.dao.RangemapDao;
import com.sva.model.LinemapModel;

public class RangemapDaoTest extends BasicDaoTest{

    @Resource
    RangemapDao dao;
    
    @Test
    public void getDataTest(){
        String placeId = "1"; 
        String floorNo = "1";
        String x1 = "0";
        String y1 = "0";
        String x2 = "100";
        String y2 = "100";
        String tableName = "location20161010";
        List<LinemapModel> lis = dao.getData(placeId, floorNo, x1, x2, y1, y2, tableName);
        Assert.assertEquals("result 0",0,lis.size());
    }
    
    @Test
    public void getTotalCountTest(){
        String placeId = "1"; 
        String floorNo = "1";
        String x1 = "0";
        String y1 = "0";
        String x2 = "100";
        String y2 = "100";
        String tableName = "location20161010";
        int lis = dao.getTotalCount(placeId, floorNo, x1, x2, y1, y2, tableName);
        Assert.assertEquals("result 0",0,lis);
    }
    
    @Test
    public void getDataForAreaTest(){
        String areaId = "1"; 
        String startTime = "2016-10-03 15:00:00";
        String endTime = "2016-10-19 15:00:00";
        List<Map<String, Object>> lis = dao.getDataForArea(areaId, startTime, endTime);
        Assert.assertEquals("result 0",0,lis.size());
    }
    
    @Test
    public void getDataForAreaByDayTest(){
        String areaId = "1"; 
        String startTime = "1";
        String endTime = "0";
        List<Map<String, Object>> lis = dao.getDataForAreaByDay(areaId, startTime, endTime);
        Assert.assertEquals("result 0",0,lis.size());
    }
    
    @Test
    public void getAreaNameTest(){
        List<Map<String, Object>> lis = dao.getAreaName();
        Assert.assertEquals("result 0",0,lis.size());
    }
    
    @Test
    public void getNameByIdTest(){
        String areaId = "11";
        String name = dao.getNameById(areaId);
        Assert.assertNull("result null",name);
    }
    
    @Test
    public void getVisitDataTest(){
        String areaId = "1"; 
        String startTime = "1";
        String endTime = "0";
        List<Map<String, Object>> lis = dao.getVisitData(areaId, startTime, endTime);
        Assert.assertEquals("result 0",0,lis.size());
    }
}
