/**   
 * @Title: StoreDao.java 
 * @Package com.sva.dao 
 * @Description: StoreDao接口类 
 * @author labelCS   
 * @date 2016年10月12日 下午4:12:56 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sva.model.StoreModel;

/** 
 * @ClassName: StoreDao
 * @Description: StoreDao接口类
 * @author labelCS 
 * @date 2016年10月12日 下午4:12:56 
 *  
 */
public interface StoreDao {
    public List<StoreModel> doquery(String storeid);

    /** 
     * @Title: doStores 
     * @Description: 查询所有配置
     * @return 
     */
    public List<StoreModel> doStores();

    /** 
     * @Title: getStoreNameBySva 
     * @Description: 查询所有配置
     * @param svaId
     * @return 
     */
    public List<String> getStoreNameBySva(int svaId);

    /** 
     * @Title: saveInfo 
     * @Description: 保存信息 
     * @param sm 
     */
    public int saveInfo(StoreModel sm); 

    public Integer selectStoreid(String name); 

    /** 
     * @Title: selectStoreSame 
     * @Description: 查询商场名称是否相同 
     * @param name
     * @param id
     * @return 
     */
    public int selectStoreSame(@Param("name")String name, @Param("id")int id); 

    public int selectStoreSame1(String name);

    /** 
     * @Title: updateInfo 
     * @Description: 更新信息
     * @param sm 
     */
    public int updateInfo(StoreModel sm); 

    /** 
     * @Title: deleteById 
     * @Description: 删除信息
     * @param id 
     */
    public int deleteById(String id); 

    /**   
     * @Title: placeByPlaceId   
     * @Description: 通过placeId查询出商城地点的名称   
     * @param placeId
     * @return：String       
     * @throws   
     */ 
    public String placeByPlaceId(int placeId);
}
