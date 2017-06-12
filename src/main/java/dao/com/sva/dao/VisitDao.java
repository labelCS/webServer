package com.sva.dao;

import java.util.List;
import com.sva.model.VisitModel;

/** 
 * @ClassName: VisitDao 
 * @Description: 统计访问量接口
 * @author JunWang 
 * @date 2016年9月20日 上午10:48:20 
 *  
 */
public interface VisitDao
{
    /** 
     * @Title: getIpExistence 
     * @Description: 通过ip获取访问次数
     * @param ip
     * @return 
     */
    public List<VisitModel> getIpExistence(String ip);
  
    /** 
     * @Title: getAllCount 
     * @Description: 获取所有的访问量 
     * @return 
     */
    public List<String> getAllCount();
    
    /** 
     * @Title: updateData 
     * @Description: 根据ip修改最后一次访问时间 
     * @param visi
     * @return 
     */
    public int updateData(VisitModel visi);
   
    /** 
     * @Title: saveData 
     * @Description:  保存访问量信息
     * @param visi
     * @return 
     */
    public int saveData(VisitModel visi);

}
