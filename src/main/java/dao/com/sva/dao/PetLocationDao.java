/**   
 * @Title: PetLocationDao.java 
 * @Package com.sva.dao 
 * @Description: 宠物位置dao
 * @author labelCS   
 * @date 2016年10月26日 下午4:07:24 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sva.model.PetLocationModel;

/** 
 * @ClassName: PetLocationDao
 * @Description: 宠物位置dao
 * @author labelCS 
 * @date 2016年10月26日 下午4:07:24 
 *  
 */
public interface PetLocationDao {
    /** 
     * @Title: getAllData 
     * @Description: 查询所有配置
     * @return 
     */
    public List<PetLocationModel> getAllData();
    
    public List<PetLocationModel> getDataByPet(@Param("mapId")String mapId,@Param("petName")String petName);
    
    public List<PetLocationModel> getPetDataByPosition(int mapId);
    
    public int petRefresh(@Param("randNumberX")int randNumberX,@Param("randNumberY")int randNumberY,@Param("petTime")int petTime);
    
    public Long getMaxPetTime();
}
