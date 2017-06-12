/**    
 * @Title:  AccountDao.java   
 * @Package com.sva.dao   
 * @Description:   AccountDao接口类   
 * @author: LabelCS    
 * @date:   2016年9月1日 下午10:31:19   
 * @version V1.0     
 */  
package com.sva.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sva.model.AccountModel;

/**   
 * @ClassName:  AccountDao   
 * @Description:AccountDao接口类   
 * @author: LabelCS  
 * @date:   2016年9月1日 下午10:31:19   
 *      
 */
public interface AccountDao {
    /**   
     * @Title: selectStore   
     * @Description: 通过登陆名称查询出对应权限的商场   
     * @param userName
     * @return：List<String>       
     * @throws   
     */ 
    public List<String> selectStore(String userName);
    
    public List<String> selMenuKey(String username);
    
    /** 
     * @Title: doquery 
     * @Description: 查询所有配置 
     * @return 
     */
    public List<AccountModel> doquery();

    /** 
     * @Title: selectCategorySame 
     * @Description: 查询名称是否相同
     * @param name
     * @param id
     * @return 
     */
    public int selectCategorySame(@Param("name")String name, @Param("id")int id);

    /** 
     * @Title: saveInfo 
     * @Description: 保存信息
     * @param sm 
     * @return 1:保存成功；0：失败
     */
    public int saveInfo(AccountModel sm);

    /** 
     * @Title: updateInfo 
     * @Description: 更新信息 
     * @param sm 
     * @return 1:更新成功；0：失败
     */
    public int updateInfo(AccountModel sm);

    /** 
     * @Title: deleteById 
     * @Description: 删除信息 
     * @param id 
     * @return 1:删除成功；0：失败
     */
    public int deleteById(String id);

    /** 
     * @Title: findUser 
     * @Description: 此方法把表对应的字段查询出来依次放入model中
     * @param username
     * @param password
     * @return 
     */
    public List<AccountModel> findUser(@Param("username")String username, @Param("password")String password);
}
