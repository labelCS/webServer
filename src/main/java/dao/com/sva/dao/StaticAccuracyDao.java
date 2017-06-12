/**   
 * @Title: StaticAccuracyDao.java 
 * @Package com.sva.dao 
 * @Description: StaticAccuracyDao接口类
 * @author labelCS   
 * @date 2016年10月13日 下午2:12:30 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.sva.model.StaticAccuracyModel;

/** 
 * @ClassName: StaticAccuracyDao
 * @Description: StaticAccuracyDao接口类
 * @author labelCS 
 * @date 2016年10月13日 下午2:12:30 
 *  
 */
public interface StaticAccuracyDao {
    public List<StaticAccuracyModel> getData(@Param("start")int start, @Param("length")int length, 
            @Param("sortCol")String sortCol, @Param("sortDir")String sortDir);
    
    public List<StaticAccuracyModel> getDataByPlaceIdTime(@Param("startTime")String startTime, 
            @Param("endTime")String endTime, @Param("placeId")String placeId);
    
    public List<StaticAccuracyModel> getAllDataByPlaceIdTime(@Param("placeId")String placeId, 
            @Param("startTime")String startTime, @Param("endTime")String endTime);
    
    public List<StaticAccuracyModel> getAllData(@Param("startTime")String startTime, @Param("endTime")String endTime);
    
    /** 
     * @Title: getStaticDataByStoreid 
     * @Description: 通过用户名获取相对应的商场权限
     * @param start
     * @param length
     * @param sortCol
     * @param sortDir
     * @param storeid
     * @return 
     */
    public List<StaticAccuracyModel> getStaticDataByStoreid(@Param("start")int start, @Param("length")int length, 
            @Param("sortCol")String sortCol, @Param("sortDir")String sortDir, @Param("storeid")int storeid);
    
    public int getDataLength();
    
    public int staticSaveTestInfo(StaticAccuracyModel aam);
    
    /** 
     * @Title: allQueryStaicAccuracy 
     * @Description: 查询所有的信息
     * @return 
     */
    public List<StaticAccuracyModel> allQueryStaicAccuracy();
}
