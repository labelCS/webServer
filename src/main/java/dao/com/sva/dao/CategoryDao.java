/**   
 * @Title: CategoryDao.java 
 * @Package com.sva.dao 
 * @Description: CategoryDao接口类 
 * @author labelCS   
 * @date 2016年9月19日 下午3:20:04 
 * @version V1.0   
 */
package com.sva.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sva.model.CategoryModel;

/** 
 * @ClassName: CategoryDao 
 * @Description: CategoryDao接口类 
 * @author labelCS 
 * @date 2016年9月19日 下午3:20:04 
 *  
 */
public interface CategoryDao {
    /** 
     * @Title: doquery 
     * @Description: 查询所有配置 
     * @return 
     */
    public List<CategoryModel> doquery();

    /** 
     * @Title: selectCategorySame 
     * @Description: 查询类别名称是否相同
     * @param name
     * @param id
     * @return 
     */
    public int selectCategorySame(@Param("name")String name, @Param("id")int id);

    /** 
     * @Title: saveInfo 
     * @Description: 保存信息
     * @param sm 
     */
    public int saveInfo(CategoryModel sm);

    /** 
     * @Title: updateInfo 
     * @Description: 更新信息
     * @param sm 
     */
    public int updateInfo(CategoryModel sm);

    /** 
     * @Title: deleteById 
     * @Description: 删除信息 
     * @param id 
     */
    public int deleteById(String id);
}
