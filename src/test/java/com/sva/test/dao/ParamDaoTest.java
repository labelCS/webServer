package com.sva.test.dao;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import com.sva.dao.ParamDao;
import com.sva.model.ParamModel;

public class ParamDaoTest extends BasicDaoTest{

    @Resource
    ParamDao dao;
    
    @Test
    public void doqueryTest(){
        List<ParamModel> lis = dao.doquery();
        Assert.assertNotEquals("result not 0",0,lis.size());
    }
    
    @Test
    public void paramUpdateTimeTest(){
        String lis = dao.paramUpdateTime();
        Assert.assertNotNull("result not null",lis);
    }
    
    @Test
    public void saveDataTest(){
        ParamModel model = new ParamModel();
        model.setBanThreshold(1.0D);
        model.setCorrectMapDirection(1.0D);
        model.setExceedMaxDeviation(1.0D);
        model.setFilterLength(1.0D);
        model.setFilterPeakError(1.0D);
        model.setHorizontalWeight(10D);
        model.setMaxDeviation(1.0D);
        model.setOngitudinalWeight(1.0D);
        model.setPeakWidth(1.0D);
        model.setRestTimes(1.0D);
        model.setStep(1.0D);
        model.setUpdateTime(111111L);
        int result = dao.saveData(model);
        Assert.assertEquals("result 1",1,result);
    }
}
