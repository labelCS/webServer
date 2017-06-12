/**   
 * @Title: PetAttributesDao.java 
 * @Package com.sva.dao 
 * @Description: 宠物属性dao 
 * @author labelCS   
 * @date 2016年10月26日 下午3:49:09 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sva.model.PetAttributesModel;

/** 
 * @ClassName: PetAttributesDao
 * @Description: 宠物属性dao 
 * @author labelCS 
 * @date 2016年10月26日 下午3:49:09 
 *  
 */
public interface PetAttributesDao {
    /** 
     * @Title: getAllData 
     * @Description: 查询所有配置
     * @return 
     */
    public List<PetAttributesModel> getAllData();
    
    /** 
     * @Title: saveData 
     * @Description: 保存宠物属性
     * @param pet 
     */
    public int saveData(PetAttributesModel pet);
    
    /** 
     * @Title: updateData 
     * @Description: 宠物属性修改
     * @param pet 
     */
    public int updateData(PetAttributesModel pet);
    
    public int deleteData(String id);
    
    public int updatePetTime(@Param("petName")String petName, @Param("petTime")long petTime);
    
    public int updatePetTimeByCaputrue(long petTime);
}
