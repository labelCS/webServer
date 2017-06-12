package com.sva.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sva.model.RoleModel;

/** 
 * @ClassName: RoleDao 
 * @Description: 权限管理接口
 * @author JunWang 
 * @date 2016年9月23日 上午10:24:33 
 *  
 */
public interface RoleDao
{

    /** 
     * @Title: doquery 
     * @Description: 此方法把表对应的字段查询出来依次放入model中 
     * @return 
     */
    public List<RoleModel> doquery();


    /** 
     * @Title: selectMenu 
     * @Description: 查询菜单栏中所有的菜单 
     * @return 
     */
    public List<Map<String, Object>> selectMenu();

    /** 
     * @Title: selectMenuEnglish 
     * @Description: 查询菜单栏中所有的英文菜单
     * @return 
     */
    public List<Map<String, Object>> selectMenuEnglish();


    /** 
     * @Title: selectMenuid 
     * @Description: 查询菜单栏中所有的菜单
     * @return 
     */
    public List<String> selectMenuid();


    /** 
     * @Title: selectMenuidEN 
     * @Description:查询菜单栏中所有的菜单 
     * @return 
     */
    public List<String> selectMenuidEN();


    /** 
     * @Title: queryMenu 
     * @Description: 通过菜单id查询菜单queryMenu 
     * @param menuId
     * @return
     * @throws SQLException 
     */
    public List<String> queryMenu(int menuId) throws SQLException;


    /** 
     * @Title: queryMenuEN 
     * @Description: 通过菜单id查询菜单queryMenu 
     * @param menuId
     * @return
     * @throws SQLException 
     */
    public List<String> queryMenuEN(int menuId) throws SQLException;

    public List<String> selmenu(String menukey) throws SQLException;

    public List<String> selmenuEN(String menukey) throws SQLException;


    public List<String> queryMenuKey(int menuId) throws SQLException;


    public List<String> queryMenuKeyEN(int menuId) throws SQLException;


    /** 
     * @Title: queryMenuId 
     * @Description: 通过菜单name查询菜单menuid
     * @param menuName
     * @return
     * @throws SQLException 
     */
    public List<String> queryMenuId(String menuName) throws SQLException;


    /** 
     * @Title: queryMenuIdEN 
     * @Description: 通过菜单name查询菜单menuid
     * @param menuName
     * @return
     * @throws SQLException 
     */
    public List<String> queryMenuIdEN(String menuName) throws SQLException;


    /** 
     * @Title: queryStoreId 
     * @Description: 通过商场name查询菜单storeid
     * @param storeName
     * @return
     * @throws SQLException 
     */
    public List<String> queryStoreId(String storeName) throws SQLException;


    /** 
     * @Title: selectStoreid 
     * @Description: 查询菜单栏中所有的菜单
     * @return 
     */
    public List<String> selectStoreid();

    /** 
     * @Title: queryStore 
     * @Description: 通过菜单id查询商场名称
     * @param storeId
     * @return
     * @throws SQLException 
     */
    public List<String> queryStore(int storeId) throws SQLException;

    /** 
     * @Title: selectRoleSame 
     * @Description: 查询类别名称是否相同
     * @param floorNo
     * @param id
     * @return
     * @throws SQLException 
     */
    public int selectRoleSame(@Param("name")String floorNo,@Param("id")int id) throws SQLException;


    /** 
     * @Title: queryStoreid 
     * @Description: 通过用户名查询商场id
     * @param username
     * @return
     * @throws SQLException 
     */
    public String queryStoreidFromRole(String username) throws SQLException;


    /** 
     * @Title: queryStore 
     * @Description: 通过用户名查询商场id
     * @param username
     * @return
     * @throws SQLException 
     */
    public String queryStoreFromRole(String username) throws SQLException;


    /** 
     * @Title: selectRoleid 
     * @Description: 通过用户名查询角色id
     * @param username
     * @return
     * @throws SQLException 
     */
    public int selectRoleid(String username) throws SQLException;

    /** 
     * @Title: saveInfo 
     * @Description:保存信息
     * @param sm
     * @return
     * @throws SQLException 
     */
    public int saveInfo(RoleModel sm) throws SQLException;


    /** 
     * @Title: updateInfo 
     * @Description: 更新信息 
     * @param sm
     * @return
     * @throws SQLException 
     */
    public int updateInfo(RoleModel sm) throws SQLException;


    /** 
     * @Title: updateInfoStore 
     * @Description: 添加商场时自动更新到角色商场权限
     * @param storeid
     * @param stores
     * @param id
     * @return
     * @throws SQLException 
     */
    public int updateInfoStore(@Param("storesid")String storeid,@Param("stores")String stores,@Param("id")int id)
            throws SQLException;
 

    /** 
     * @Title: deleteById 
     * @Description: 删除信息 
     * @param id
     * @return
     * @throws SQLException 
     */
    public int deleteById(String id) throws SQLException;

}