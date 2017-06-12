/**   
 * @Title: ApLocationDao.java 
 * @Package com.sva.dao 
 * @Description: AP级定位Dao
 * @author labelCS   
 * @date 2016年12月7日 下午2:33:38 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sva.model.ApLocationModel;
import com.sva.model.LocationModel;

/** 
 * @ClassName: ApLocationDao 
 * @Description: AP级定位Dao
 * @author labelCS 
 * @date 2016年12月7日 下午2:33:38 
 *  
 */
@Repository
public interface ApLocationDao {
    /** 
     * @Title: getUserMac 
     * @Description: 获取当前的用户mac列表
     * @param tableName
     * @param time
     * @return 
     */
    public List<String> getUserMac(@Param("tableName")String tableName, @Param("time")Long time);
    
    /** 
     * @Title: getUserInfo 
     * @Description: 获取当前的用户ap级信息
     * @param tableName
     * @param userId
     * @return 
     */
    public List<LocationModel> getUserInfo(
            @Param("tableName")String tableName, 
            @Param("userId")String userId, 
            @Param("time")Long time);
    
    /**   
     * @Title: saveApMapper   
     * @Description: 更新ap位置映射表
     * @param ap
     * @return：int       
     * @throws   
     */ 
    public int saveApMapper(ApLocationModel ap);
}
