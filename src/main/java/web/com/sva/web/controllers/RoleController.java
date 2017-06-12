package com.sva.web.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.sva.common.Util;
import com.sva.dao.RoleDao;
import com.sva.model.RoleModel;

@Controller
@RequestMapping(value = "/role")
public class RoleController
{
    @Autowired
    private RoleDao dao;

    private static final Logger LOG = Logger.getLogger(RoleController.class);

    @RequestMapping(value = "/api/getData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData(HttpServletRequest request)
    {
        LOG.info("Role:getTableData");

        Collection<RoleModel> resultList = dao.doquery();
        RequestContext requestContext = new RequestContext(request);
        StringBuilder sbf = new StringBuilder();
        List<String> menu = new ArrayList<String>();
        String menues = "";
        Locale myLocale = requestContext.getLocale();
        int menukSize = 0;
        String[] menukey = null;
        for (RoleModel roleModel : resultList)
        {
            menukey = roleModel.getMenukey().split(",");
            menukSize = menukey.length;
            for (int i = 0; i < menukSize; i++)
            {
                if (Locale.CHINA.equals(myLocale))
                {
                    try
                    {
                        menu = dao.selmenu(menukey[i]);
                    }
                    catch (SQLException e)
                    {
                        LOG.error(e);
                    }
                }
                else
                {
                    try
                    {
                        menu = dao.selmenuEN(menukey[i]);
                    }
                    catch (SQLException e)
                    {
                        LOG.error(e);
                    }
                }
                if (!menu.isEmpty())
                {
                    menues = menu.get(0);
                }
                sbf.append(menues + ',');
            }
            roleModel.setMenues(sbf.toString().substring(0, sbf.length() - 1));
            sbf.delete(0, sbf.length());
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", resultList);

        return modelMap;
    }

    // 查询数据库中所有的菜单
    @RequestMapping(value = "/api/menu", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> menu(HttpServletRequest request)
    {
        List<Map<String, Object>> menuList;
        RequestContext requestContext = new RequestContext(request);
        Locale myLocale = requestContext.getLocale();
        if (Locale.CHINA.equals(myLocale))
        {
            menuList = dao.selectMenu();
        }
        else
        {
            menuList = dao.selectMenuEnglish();
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("error", null);
        modelMap.put("data", menuList);

        return modelMap;
    }

    // 通过菜单查询数据库中菜单id
    @RequestMapping(value = "/api/selmenuid", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> menuId(HttpServletRequest request,
            @RequestParam("menu") String menu) throws SQLException
    {
        StringBuilder sbmenu = new StringBuilder();
        List<String> menuIdList;
        RequestContext requestContext = new RequestContext(request);
        Locale myLocale = requestContext.getLocale();
        String menus = "";
        String menuId = "";
        String[] menues = {};
        try {
            menues = URLDecoder.decode(menu, "utf-8").split(",");
        } catch (UnsupportedEncodingException e) {
            LOG.error(e);
        }
        String menuName;
        int menueSize = menues.length;
        for (int i = 0; i < menueSize; i++)
        {
            menuName = menues[i];
            if (Locale.CHINA.equals(myLocale))
            {
                menuIdList = dao.queryMenuId(menuName);
                if (!menuIdList.isEmpty())
                {
                    menuId = menuIdList.get(0);
                }
            }
            else
            {
                menuIdList = dao.queryMenuIdEN(menuName);
                if (!menuIdList.isEmpty())
                {
                    menuId = menuIdList.get(0);
                }
            }
            menus = sbmenu.append(menuId + ',').toString();
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        List<String> menuid;
        if (Locale.CHINA.equals(myLocale))
        {
            menuid = dao.selectMenuid();
        }
        else
        {
            menuid = dao.selectMenuidEN();
        }
        modelMap.put("error", null);
        modelMap.put("dataes", menus.substring(0, menus.length() - 1));
        modelMap.put("menuId", menuid);
        return modelMap;
    }

    // 通过商场名称查询数据库中商场id
    @RequestMapping(value = "/api/selRoleid", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> storeId(@RequestParam("stores") String stores)
            throws SQLException
    {
        StringBuilder sbStore = new StringBuilder();
        String storess = "";
        String storeId;
        String[] storeNames = stores.split(",");
        List<String> storeIdList;
        String storeName;
        for (int i = 0; i < storeNames.length; i++)
        {
            storeName = storeNames[i];
            storeIdList = dao.queryStoreId(storeName);
            if (!storeIdList.isEmpty())
            {
                storeId = storeIdList.get(0);
                storess = sbStore.append(storeId + ',').toString();
            }

        }
        List<String> storeIdes = dao.selectStoreid();
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("error", null);
        modelMap.put("storedata", storess.substring(0, storess.length() - 1));
        modelMap.put("storeId", storeIdes);

        return modelMap;
    }

    @RequestMapping(value = "/api/saveData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> saveData(HttpServletRequest request,
            @RequestParam(value = "id", required = false) String id,
            @RequestParam("name") String name,
            @RequestParam("menuAccount") String menuAccount,
            @RequestParam("storeAccount") String storeAccount)
            throws SQLException
    {
        String ip = Util.getIpAddr(request);
        // 遍历所有的storeAccount，选择菜单的集合
        StringBuilder sbmenu = new StringBuilder();
        StringBuilder sbmenukey = new StringBuilder();
        StringBuilder sbstore = new StringBuilder();
        RequestContext requestContext = new RequestContext(request);
        Locale myLocale = requestContext.getLocale();
        List<String> menuList;
        List<String> menuKeyList;
        List<String> storeList;
        String menus = "";
        String stores = "";
        String menuKeyes = "";
        String menu = "";
        String menuKey = "";
        String[] menues = menuAccount.split(",");
        String[] storesArray = storeAccount.split(",");
        int size1 = menues.length;
        String str;
        int menuId;
        for (int i = 0; i < size1; i++)
        {
            str = menues[i];
            menuId = Integer.parseInt(str);
            if (Locale.CHINA.equals(myLocale))
            {
                menuList = dao.queryMenu(menuId);
                if (!menuList.isEmpty())
                {
                    menu = menuList.get(0);
                }
            }
            else
            {
                menuList = dao.queryMenuEN(menuId);
                if (!menuList.isEmpty())
                {
                    menu = menuList.get(0);
                }
            }
            menus = sbmenu.append(menu + ',').toString();
            // 通过id查询菜单key值
            if (Locale.CHINA.equals(myLocale))
            {
                menuKeyList = dao.queryMenuKey(menuId);
                if (!menuKeyList.isEmpty())
                {
                    menuKey = menuKeyList.get(0);
                }
            }
            else
            {
                menuKeyList = dao.queryMenuKeyEN(menuId);
                if (!menuKeyList.isEmpty())
                {
                    menuKey = menuKeyList.get(0);
                }
            }
            menuKeyes = sbmenukey.append(menuKey + ',').toString();
        }
        String store;
        int size = storesArray.length;
        String str1;
        int storeId;
        for (int i = 0; i < size; i++)
        {
            str1 = storesArray[i];
            storeId = Integer.parseInt(str1);
            storeList = dao.queryStore(storeId);
            if (!storeList.isEmpty())
            {
                store = storeList.get(0);
                stores = sbstore.append(store + ',').toString();
            }
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        RoleModel roleModel = new RoleModel();
        Date current = new Date();
        if (!"".equals(id))
        {
            roleModel.setId(Integer.parseInt(id));
        }
        roleModel.setUpdateTime(current);
        roleModel.setName(name);
        roleModel.setStoresid(storeAccount);
        roleModel.setMenues(menus.substring(0, menus.length() - 1));
        roleModel.setStores(stores.substring(0, stores.length() - 1));
        roleModel.setMenukey(menuKeyes.substring(0, menuKeyes.length() - 1));
        // 保存
        try
        {
            if ("".equals(id))
            {
                LOG.info("insert role name:"+name+" ip:"+ip);
                dao.saveInfo(roleModel);
            }
            else
            {
                LOG.info("update role name:"+name+" ip:"+ip);
                dao.updateInfo(roleModel);
            }
        }
        catch (DuplicateKeyException e)
        {
            LOG.error(e);
            modelMap.put("error", "role name can not be the same");
        }
        catch (Exception e)
        {
            LOG.error(e);
            modelMap.put("error", e.getStackTrace());
        }

        modelMap.put("data", null);
        return modelMap;
    }

    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteData(HttpServletRequest request,@RequestParam("id") String id)
    {
        String ip = Util.getIpAddr(request);
        LOG.info("CategoryController:deleteData::" + id);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        try
        {
            LOG.info("delete role id:" + id+" ip:"+ip);
            dao.deleteById(id);
        }
        catch (Exception e)
        {
            LOG.error(e);
            modelMap.put("error", e.getStackTrace());
        }

        return modelMap;
    }
}
