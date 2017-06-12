package com.sva.test.dao;

import java.sql.SQLException;
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
        try {
            lis = dao.queryMenu(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("relsut  0", 0, lis.size());
    } 
    
    @Test
    public void queryMenuENTest()
    {
        int id = 999;
        List<String> lis = null;
        try {
            lis = dao.queryMenuEN(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("relsut  0", 0, lis.size());
    } 
    
    @Test
    public void selmenuTest()
    {
        String keymenu = "key_svaManage";
        List<String> lis = null;
        try {
            lis = dao.selmenu(keymenu);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("relsut 1", 1, lis.size());
    } 
    
    @Test
    public void selmenuENTest()
    {
        String keymenu = "key_svaManage";
        List<String> lis = null;
        try {
            lis = dao.selmenuEN(keymenu);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("relsut 1", 1, lis.size());
    } 
    
    @Test
    public void queryMenuKeyTest()
    {
        int id = 1;
        List<String> lis = null;
        try {
            lis = dao.queryMenuKey(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertNotEquals("relsut not 0 ", 0, lis.size());
    } 
    
    @Test
    public void queryMenuKeyENTest()
    {
        int id = 1;
        List<String> lis = null;
        try {
            lis = dao.queryMenuKeyEN(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertNotEquals("relsut not 0 ", 0, lis.size());
    } 
    
    @Test
    public void queryMenuIdTest()
    {
        String name = "SVA管理";
        List<String> lis = null;
        try {
            lis = dao.queryMenuId(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertNotEquals("relsut not 0 ", 0, lis.size());
    } 
    
    @Test
    public void queryMenuIdENTest()
    {
        String name = "SVA管理";
        List<String> lis = null;
        try {
            lis = dao.queryMenuIdEN(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("relsut not 0 ", 0, lis.size());
    } 
    
    @Test
    public void queryStoreIdTest()
    {
        String name = "123";
        List<String> lis = null;
        try {
            lis = dao.queryStoreId(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        try {
            lis = dao.queryStore(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("relsut  0 ", 0, lis.size());
    }
    
    @Test
    public void selectRoleSameTest()
    {
        int id = 1;
        String name ="1";
        int reslut = 0;
        try {
            reslut = dao.selectRoleSame(name,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("relsut  0 ", 0,reslut);
    }
    
    @Test
    public void queryStoreidFromRoleTest()
    {
        String name ="admin";
        String reslut = null;
        try {
            reslut = dao.queryStoreidFromRole(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertNotEquals("relsut  not null",null,reslut);
    }
    
    @Test
    public void queryStoreFromRoleTest()
    {
        String name ="admin";
        String reslut = null;
        try {
            reslut = dao.queryStoreFromRole(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertNotEquals("relsut  not null",null,reslut);
    }
    
    @Test
    public void selectRoleidTest()
    {
        String name ="admin";
        int reslut = 0;
        try {
            reslut = dao.selectRoleid(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        try {
            reslut = dao.saveInfo(role);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        try {
            reslut = dao.updateInfo(role);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("relsut  not null",1,reslut);
    }
    
    
    @Test
    public void updateInfoStoreTest()
    {
        String store = "1";
        String storesid = "1";
        int id = 1;
        int reslut = 0;
        try {
            reslut = dao.updateInfoStore(storesid,store,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("relsut  not null",1,reslut);
    }
    
    @Test
    public void deleteByIdTest()
    {
        String id = "999";
        int reslut = 0;
        try {
            reslut = dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("relsut  not null",0,reslut);
    }
}
