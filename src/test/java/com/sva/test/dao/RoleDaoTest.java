package com.sva.test.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.sva.dao.RoleDao;
import com.sva.model.RoleModel;

public class RoleDaoTest extends BasicDaoTest{
    
    @Resource
    RoleDao dao;
    
    
    @Test
    public void doqueryTest()
    {
        List<RoleModel> lis = dao.doquery();
        Assert.assertEquals("relsut 0", 0, lis.size());
    }
    
    @Test
    public void selectMenuTest()
    {
        List<Map<String, Object>> lis = dao.selectMenu();
        Assert.assertNotEquals("relsut not 0", 0, lis.size());
        
    }
    
    @Test
    public void selectMenuEnglishTest()
    {
        List<Map<String, Object>> lis = dao.selectMenuEnglish();
        Assert.assertNotEquals("relsut not 0", 0, lis.size());
        
    }
    
    @Test
    public void selectMenuidTest()
    {
        List<String> lis  = dao.selectMenuid();
        Assert.assertNotEquals("relsut not 0", 0, lis.size());
    }
    
    @Test
    public void selectMenuidENTest()
    {
        List<String> lis  = dao.selectMenuidEN();
        Assert.assertNotEquals("relsut not 0", 0, lis.size());
    }
    
    @Test
    public void queryMenuTest()
    {
        int id = 999;
        List<String> lis = null;
        lis = dao.queryMenu(id);
        Assert.assertEquals("relsut  0", 0, lis.size());
    } 
    
    @Test
    public void queryMenuENTest()
    {
        int id = 999;
        List<String> lis = null;
        lis = dao.queryMenuEN(id);
        Assert.assertEquals("relsut  0", 0, lis.size());
    } 
    
    @Test
    public void selmenuTest()
    {
        String keymenu = "key_svaManage";
        List<String> lis = null;
        lis = dao.selmenu(keymenu);
        Assert.assertEquals("relsut 1", 1, lis.size());
    } 
    
    @Test
    public void selmenuENTest()
    {
        String keymenu = "key_svaManage";
        List<String> lis = null;
        lis = dao.selmenuEN(keymenu);
        Assert.assertEquals("relsut 1", 1, lis.size());
    } 
    
    @Test
    public void queryMenuKeyTest()
    {
        int id = 1;
        List<String> lis = null;
        lis = dao.queryMenuKey(id);
        Assert.assertNotEquals("relsut not 0 ", 0, lis.size());
    } 
    
    @Test
    public void queryMenuKeyENTest()
    {
        int id = 1;
        List<String> lis = null;
        lis = dao.queryMenuKeyEN(id);
        Assert.assertNotEquals("relsut not 0 ", 0, lis.size());
    } 
    
    @Test
    public void queryMenuIdTest()
    {
        String name = "SVA管理";
        List<String> lis = null;
        lis = dao.queryMenuId(name);
        Assert.assertNotEquals("relsut not 0 ", 0, lis.size());
    } 
    
    @Test
    public void queryMenuIdENTest()
    {
        String name = "SVA管理";
        List<String> lis = null;
        lis = dao.queryMenuIdEN(name);
        Assert.assertEquals("relsut not 0 ", 0, lis.size());
    } 
    
    @Test
    public void queryStoreIdTest()
    {
        String name = "123";
        List<String> lis = null;
        lis = dao.queryStoreId(name);
        Assert.assertEquals("relsut  0 ", 0, lis.size());
    } 
    
    @Test
    public void selectStoreidTest()
    {
        List<String> lis = dao.selectStoreid();
        Assert.assertNotEquals("relsut  0 ", 0, lis.size());
    } 
    
    
    @Test
    public void queryStoreTest()
    {
        int id = 999;
        List<String> lis = null;
        lis = dao.queryStore(id);
        Assert.assertEquals("relsut  0 ", 0, lis.size());
    }
    
    @Test
    public void selectRoleSameTest()
    {
        int id = 1;
        String name ="1";
        int reslut = 0;
        reslut = dao.selectRoleSame(name,id);
        Assert.assertEquals("relsut  0 ", 0,reslut);
    }
    
    @Test
    public void queryStoreidFromRoleTest()
    {
        String name ="admin";
        String reslut = null;
        reslut = dao.queryStoreidFromRole(name);
        Assert.assertNotEquals("relsut  not null",null,reslut);
    }
    
    @Test
    public void queryStoreFromRoleTest()
    {
        String name ="admin";
        String reslut = null;
        reslut = dao.queryStoreFromRole(name);
        Assert.assertNotEquals("relsut  not null",null,reslut);
    }
    
    @Test
    public void selectRoleidTest()
    {
        String name ="admin";
        int reslut = 0;
        reslut = dao.selectRoleid(name);
        Assert.assertNotEquals("relsut  not null",0,reslut);
    }
    
    
    @Test
    public void saveInfoTest()
    {
        RoleModel role = new RoleModel();
        long time = System.currentTimeMillis();
        role.setName("999");
        role.setUpdateTime(new Date(time));
        role.setMenues("1");
        role.setStores("1");
        role.setMenukey("1");
        role.setStoresid("1");
        int reslut = 0;
        reslut = dao.saveInfo(role);
        Assert.assertEquals("relsut  not null",1,reslut);
    }

    
    @Test
    public void updateInfoTest()
    {
        RoleModel role = new RoleModel();
        long time = System.currentTimeMillis();
        role.setName("66");
        role.setUpdateTime(new Date(time));
        role.setMenues("1");
        role.setStores("1");
        role.setMenukey("1");
        role.setStoresid("1");
        role.setId(1);
        int reslut = 0;
        reslut = dao.updateInfo(role);
        Assert.assertEquals("relsut  not null",1,reslut);
    }
    
    
    @Test
    public void updateInfoStoreTest()
    {
        String store = "1";
        String storesid = "1";
        int id = 1;
        int reslut = 0;
        reslut = dao.updateInfoStore(storesid,store,id);
        Assert.assertEquals("relsut  not null",1,reslut);
    }
    
    @Test
    public void deleteByIdTest()
    {
        String id = "999";
        int reslut = 0;
        reslut = dao.deleteById(id);
        Assert.assertEquals("relsut  not null",0,reslut);
    }
}
