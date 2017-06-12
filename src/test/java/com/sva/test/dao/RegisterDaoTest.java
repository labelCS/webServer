package com.sva.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.sva.dao.RegisterDao;
import com.sva.model.RegisterModel;

public class RegisterDaoTest extends BasicDaoTest{
    
    @Resource
    RegisterDao dao;
    
    @Test
    public void saveRegisterTest()
    {
        RegisterModel re = new RegisterModel();
        long time = System.currentTimeMillis();
        re.setUserName("username");
        re.setTimes(time);
        re.setPhoneNumber("13990069977");
        int reslut = dao.saveRegister(re);
        Assert.assertEquals("relsut 1", 1,reslut);
    }

    
    @Test
    public void refreshRegisterTest()
    {
        RegisterModel re = new RegisterModel();
        long time = System.currentTimeMillis();
        re.setUserName("username");
        re.setTimes(time);
        re.setPhoneNumber("139900699");
        re.setUserId("13123");
        int reslut = dao.refreshRegister(re);
        Assert.assertEquals("relsut 0", 0,reslut);
        
    }
    
    @Test
    public void getDataByUserNameTest()
    {
        String name = "asd";
        List<RegisterModel> reslut = dao.getDataByUserName(name);
        Assert.assertEquals("relsut 0", 0,reslut.size());
        
    }
    
    @Test
    public void getDataByPhoneNumberTest()
    {
        String phone = "123456";
        List<RegisterModel> reslut = dao.getDataByPhoneNumber(phone);
        Assert.assertEquals("relsut 0", 0,reslut.size());
        
    }
    
    @Test
    public void getDataByIsTrueTest()
    {
        String phone = "13990069977";
        String isTrue = "1";
        List<RegisterModel> reslut = dao.getDataByIsTrue(phone,isTrue);
        Assert.assertEquals("relsut 0", 0,reslut.size());
        
    }
    
    @Test
    public void getDataByStatusTest()
    {
        String phone = "13990069977";
        String status = "6";
        List<RegisterModel> reslut = dao.getDataByStatus(phone,status);
        Assert.assertEquals("relsut 0", 0,reslut.size());
        
    }
    
    @Test
    public void updateIsTrueTest()
    {
        String phone = "222";
        String isTrue = "6";
        int reslut = dao.updateIsTrue(phone,isTrue);
        Assert.assertEquals("relsut 0", 0,reslut);
        
    }    
    
    @Test
    public void updataStatusTest()
    {
        String phone = "222";
        String otherPhone = "622";
        int reslut = dao.updataStatus(phone,otherPhone);
        Assert.assertEquals("relsut 0", 0,reslut);
        
    }  
    
    @Test
    public void updateStatusByPhoneNumberTest()
    {
        String phoneNumber = "666";
        String status = "2";
        int reslut = dao.updateStatusByPhoneNumber(phoneNumber,status);
        Assert.assertEquals("relsut 0", 0,reslut);
        
    }  
    
    @Test
    public void getIpByUserNameTest()
    {
        String phoneNumber = "777";
        List<String> reslut = dao.getIpByUserName(phoneNumber);
        Assert.assertEquals("relsut 0", 0,reslut.size());
        
    }  
    
    @Test
    public void getStatusByphoneNumber2Test()
    {
        String phoneNumber = "77777";
        List<String> reslut = dao.getStatusByphoneNumber2(phoneNumber);
        Assert.assertEquals("relsut 0", 0,reslut.size());
        
    }  
    
    @Test
    public void getStatusByIsTrueTest()
    {
        String phoneNumber = "999";
        String isTrue = "1";
        List<String> reslut = dao.getStatusByIsTrue(phoneNumber,isTrue);
        Assert.assertEquals("relsut 0", 0,reslut.size());
        
    }  
    
    @Test
    public void checkLogin1Test()
    {
        RegisterModel re = new RegisterModel();
        re.setPhoneNumber("8888");
        re.setPassWord("2222");
        int reslut = dao.checkLogin1(re);
        Assert.assertEquals("relsut 0", 0,reslut);
        
    } 
    
    @Test
    public void setLoginStatusTest()
    {
        String phoneNumber = "999";
        String loginStatus = "1231231";
        int reslut = dao.setLoginStatus(phoneNumber,loginStatus);
        Assert.assertEquals("relsut 0", 0,reslut);
        
    }  
    
}
