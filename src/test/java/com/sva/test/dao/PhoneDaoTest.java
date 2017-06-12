package com.sva.test.dao;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import com.sva.dao.PhoneDao;
import com.sva.model.PhoneModel;

public class PhoneDaoTest extends BasicDaoTest{

    @Resource
    PhoneDao dao;
    
    @Test
    public void getAllDataTest(){
        List<PhoneModel> lis = dao.getAllData();
        Assert.assertNotEquals("result not 0",0,lis.size());
    }
    
    @Test
    public void savePhoneTest(){
        PhoneModel model = new PhoneModel();
        model.setIp("0.0.0.0");
        model.setPhoneNumber("123123123");
        model.setTimestamp(12312313L);
        int result = dao.savePhone(model);
        Assert.assertEquals("result 1",1,result);
    }
}
