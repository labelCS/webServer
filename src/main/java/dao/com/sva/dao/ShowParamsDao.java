/**   
 * @Title: ShowParamsDao.java 
 * @Package com.sva.dao 
 * @Description: 局点演示dao
 * @author labelCS   
 * @date 2016年12月19日 下午2:59:59 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;

import com.sva.model.ShowParamsModel;

/** 
 * @ClassName: ShowParamsDao 
 * @Description: 局点演示dao
 * @author labelCS 
 * @date 2016年12月19日 下午2:59:59 
 *  
 */
public interface ShowParamsDao {
    /** 
     * @Title: getTableData 
     * @Description: 获取所有的局点配置信息
     * @return 
     */
    public List<ShowParamsModel> getTableData();
    
    /** 
     * @Title: addData 
     * @Description: 添加新的局点配置
     * @param model
     * @return 
     */
    public int addData(ShowParamsModel model);
    
    /** 
     * @Title: updateData 
     * @Description: 更新配置
     * @param model
     * @return 
     */
    public int updateData(ShowParamsModel model);
    
    /** 
     * @Title: deleteData 
     * @Description: 删除指定id的局点配置
     * @param id
     * @return 
     */
    public int deleteData(String id);
    
    /** 
     * @Title: getParamsByShowId 
     * @Description: 获取指定局点的配置
     * @param showId
     * @return 
     */
    public ShowParamsModel getParamsByShowId(String showId);
}
