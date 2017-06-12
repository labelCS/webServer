/**   
 * @Title: DynamicAccuracyDao.java 
 * @Package com.sva.dao 
 * @Description: DynamicAccuracyDao接口类 
 * @author labelCS   
 * @date 2016年9月20日 上午9:29:52 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sva.model.DynamicAccuracyModel;

/** 
 * @ClassName: DynamicAccuracyDao 
 * @Description: DynamicAccuracyDao接口类
 * @author labelCS 
 * @date 2016年9月20日 上午9:29:52 
 *  
 */
public interface DynamicAccuracyDao {
    public List<DynamicAccuracyModel> getData(@Param("start")int start, @Param("length")int length,
            @Param("sortCol")String sortCol, @Param("sortDir")String sortDir);

    public List<DynamicAccuracyModel> getDataByPlaceIdTime(@Param("startTime")String startTime, 
            @Param("endTime")String endTime, @Param("placeId")String placeId);
    
    public List<DynamicAccuracyModel> getAllDataByPlaceIdTime(@Param("placeId")String placeId, 
            @Param("startTime")String startTime, @Param("endTime")String endTime);
    
    public List<DynamicAccuracyModel> getAllData(@Param("startTime")String startTime, @Param("endTime")String endTime);
    
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
    public List<DynamicAccuracyModel> getStaticDataByStoreid(@Param("start")int start,@Param("length")int length, 
            @Param("sortCol")String sortCol, @Param("sortDir")String sortDir, @Param("placeId")int storeid);

    public int getDataLength();

    public int dynamicSaveTestInfo(DynamicAccuracyModel aam);

    /** 
     * @Title: allQueryDynamicAccuracy 
     * @Description: 查询所有的信息
     * @return 
     */
    public List<DynamicAccuracyModel> allQueryDynamicAccuracy();
}
