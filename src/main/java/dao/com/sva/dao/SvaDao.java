/**   
 * @Title: SvaDao.java 
 * @Package com.sva.dao 
 * @Description: SvaDao接口类
 * @author labelCS   
 * @date 2016年10月12日 下午5:42:56 
 * @version V1.0   
 */
package com.sva.dao;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.sva.model.SvaModel;

/** 
 * @ClassName: SvaDao
 * @Description: SvaDao接口类
 * @author labelCS 
 * @date 2016年10月12日 下午5:42:56 
 *  
 */
public interface SvaDao {
    /** 
     * @Title: doquery 
     * @Description: 查询所有sva
     * @return 
     */
    public List<SvaModel> doquery();

    /** 
     * @Title: queryByFloorNo 
     * @Description: 根据floorNo查询所有sva
     * @param floorNo
     * @return 
     */
    public List<SvaModel> queryByFloorNo(String floorNo);

    /** 
     * @Title: queryActive 
     * @Description: 查询所有sva
     * @return 
     */
    public List<SvaModel> queryActive();

    /** 
     * @Title: storeBySva 
     * @Description: 通过svaid查询出store的id
     * @param svaid
     * @return 
     */
    public List<String> storeBySva(String svaid);

    /** 
     * @Title: storeIdByName 
     * @Description: 通过svaid查询出store的id
     * @param storeName
     * @return 
     */
    public String storeIdByName(String storeName);

    /** 
     * @Title: doqueryByAll 
     * @Description: 查询所有sva
     * @return 
     */
    public List<SvaModel> doqueryByAll();

    /** 
     * @Title: queryByStoreId 
     * @Description: 查询所有sva
     * @param storeId
     * @return 
     */
    public List<SvaModel> queryByStoreId(String storeId);
    
    /**
     * 查询sva列表，类型为非匿名化和匿名化，不包含指定用户
     * @param storeId
     * @return
     */
    public List<SvaModel> queryByStoreId2(String storeId);

    /** 
     * @Title: getEnabled 
     * @Description: 查询所有启用的sva
     * @return 
     */
    public List<SvaModel> getEnabled();

    /** 
     * @Title: saveSvaInfo 
     * @Description: 保存sva信息
     * @param mmm
     * @return 
     */
    public int saveSvaInfo(SvaModel mmm); 

    public int updateSvaInfo(SvaModel sm);

    /** 
     * @Title: disableSva 
     * @Description: 禁用sva
     * @param id
     * @return 
     */
    public int disableSva(String id);

    /** 
     * @Title: enableSva 
     * @Description: 启用sva
     * @param id
     * @return 
     */
    public int enableSva(String id);

    /** 
     * @Title: deleteSvaTemp 
     * @Description: 删除sva
     * @param id
     * @return 
     */
    public int deleteSvaTemp(String id);

    /** 
     * @Title: deleteMapBySva 
     * @Description: 删除sva在映射表中的数据
     * @param id
     * @return 
     */
    public int deleteMapBySva(int id);

    /** 
     * @Title: deleteSva 
     * @Description: 删除sva的数据
     * @param id
     * @return 
     */
    public int deleteSva(String id);

    /** 
     * @Title: addMapper 
     * @Description: 添加sva在映射表中的映射关系
     * @param id
     * @param store
     * @throws SQLException 
     */
    public int addMapper(@Param("id")int id, @Param("store")List<String> store);

    public List<SvaModel> selectID(String svaName);

    public List<SvaModel> selectID1(@Param("svaName")String svaName, @Param("id")String id);

    public int checkSvaByName(String svaName);

    public int checkSvaByName1(@Param("svaName")String svaName, @Param("id")String id);

    public SvaModel getSvaById(int id);

}
