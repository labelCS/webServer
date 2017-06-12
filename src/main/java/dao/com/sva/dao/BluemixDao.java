/**    
 * @Title:  BluemixDao.java   
 * @Package com.sva.dao   
 * @Description:   BluemixDao接口类   
 * @author: LabelCS    
 * @date:   2016年9月1日 下午10:31:19   
 * @version V1.0     
 */  
package com.sva.dao;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.sva.model.BluemixModel;

/**   
 * @ClassName:  BluemixDao   
 * @Description:BluemixDao接口类   
 * @author: LabelCS  
 * @date:   2016年9月1日 下午10:31:19   
 *      
 */
public interface BluemixDao {
    /** 
     * @Title: doquery 
     * @Description: 查询所有配置
     * @return 
     */
    public List<BluemixModel> doquery();

    /** 
     * @Title: queryAllStatus 
     * @Description: 查询所有配置
     * @return 
     */
    public List<BluemixModel> queryAllStatus();

    /** 
     * @Title: saveInfo 
     * @Description: 保存信息
     * @param bm 
     */
    public int saveInfo(BluemixModel bm);

    /** 
     * @Title: updateInfo 
     * @Description: 更新信息
     * @param bm 
     */
    public int updateInfo(BluemixModel bm);

    /** 
     * @Title: deleteInfo 
     * @Description: 删除信息 
     * @param id 
     */
    public int deleteInfo(String id);

    /** 
     * @Title: deleteById 
     * @Description: 删除信息 
     * @param id 
     */
    public int deleteById(String id);

    /** 
     * @Title: changeStatus 
     * @Description: 改变状态 
     * @param id
     * @param status 
     */
    public int changeStatus(@Param("id")String id, @Param("status")String status);
}
