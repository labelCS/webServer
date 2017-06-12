package com.sva.test.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import com.sva.dao.StaticAccuracyDao;
import com.sva.model.MapsModel;
import com.sva.model.StaticAccuracyModel;
import com.sva.model.StoreModel;

public class StaticAccuracyDaoTest extends BasicDaoTest{
    
    @Resource
    StaticAccuracyDao dao;
    
    @Test
    public void getDataTest()
    {
        int start = 0;
        int length = 10;
        String sortCol = "avgeOffset";
        String sortDir = "asc";
        List<StaticAccuracyModel> reslut = dao.getData(start, length, sortCol, sortDir);
        Assert.assertEquals("relsut 0", 0, reslut.size());
    }
    
    @Test
    public void getDataByPlaceIdTimeTest()
    {
        String startTime = "1";
        String endTime = "5";
        String placeId = "1";
        List<StaticAccuracyModel> reslut = dao.getDataByPlaceIdTime(startTime, endTime, placeId);
        Assert.assertEquals("relsut 0", 0, reslut.size());
    }
    
    @Test
    public void getAllDataByPlaceIdTimeTest()
    {
        String startTime = "1";
        String endTime = "5";
        String placeId = "1";
        List<StaticAccuracyModel> reslut = dao.getAllDataByPlaceIdTime(startTime, endTime, placeId);
        Assert.assertEquals("relsut 0", 0, reslut.size());
    }
    
    @Test
    public void getAllDataTest()
    {
        String startTime = "1";
        String endTime = "5";
        List<StaticAccuracyModel> reslut = dao.getAllData(startTime, endTime);
        Assert.assertEquals("relsut 0", 0, reslut.size());
    }
    
    @Test
    public void getStaticDataByStoreidTest()
    {
        int start = 0;
        int length = 10;
        String sortCol = "avgeOffset";
        String sortDir = "asc";
        int storeId = 1;
        List<StaticAccuracyModel> reslut = dao.getStaticDataByStoreid(start, length, sortCol, sortDir, storeId);
        Assert.assertEquals("relsut 0", 0, reslut.size());
    }
    
    @Test
    public void getDataLengthTest()
    {
        int reslut = dao.getDataLength();
        Assert.assertEquals("relsut 0", 0, reslut);
    }

    @Test
    public void staticSaveTestInfoTest()
    {
        StaticAccuracyModel sam = new StaticAccuracyModel();
        MapsModel map = new MapsModel();
        map.setFloorNo(new BigDecimal(1));
        map.setFloor("1F");
        sam.setMap(map);
        StoreModel store = new StoreModel();
        store.setId(1);
        store.setName("HM");
        sam.setStore(store);
        sam.setAvgeOffset(new BigDecimal(1));
        sam.setDeviation(new BigDecimal(1));
        sam.setCount10(1);
        sam.setCount10p(2);
        sam.setCount3(5);
        sam.setCount5(6);
        sam.setDestination("no");
        sam.setDetail("blabla");
        sam.setEnddate(new Date());
        sam.setMaxOffset(new BigDecimal(1));
        sam.setOffsetCenter(new BigDecimal(1));
        sam.setOffsetNumber(new BigDecimal(1));
        sam.setOrigin("ok");
        sam.setStability(new BigDecimal(1));
        sam.setStaicAccuracy(new BigDecimal(1));
        sam.setStartdate(new Date());
        sam.setTriggerIp("0.0.0.0");
        int reslut = dao.staticSaveTestInfo(sam);
        Assert.assertEquals("relsut 1", 1, reslut);
    }
    
    @Test
    public void allQueryStaicAccuracyTest()
    {
        List<StaticAccuracyModel> reslut = dao.allQueryStaicAccuracy();
        Assert.assertEquals("relsut 0", 0, reslut.size());
    }
}
