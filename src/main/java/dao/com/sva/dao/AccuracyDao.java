/**   
 * @Title: AccuracyDao.java 
 * @Package com.sva.dao 
 * @Description: AccuracyDao接口类  
 * @author labelCS   
 * @date 2016年9月14日 下午3:09:13 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sva.model.AccuracyModel;

/**
 * @ClassName: AccuracyDao
 * @Description: AccuracyDao接口类
 * @author labelCS
 * @date 2016年9月14日 下午3:09:13
 * 
 */
public interface AccuracyDao {

    public List<AccuracyModel> getData(@Param("start") int start, @Param("length") int length,
            @Param("sortCol") String sortCol, @Param("sortDir") String sortDir);

    /**
     * @Title: getDataByStoreid
     * @Description: 通过用户名获取相对应的商场权限
     * @param start
     * @param length
     * @param sortCol
     * @param sortDir
     * @param storeid
     * @return
     */
    public List<AccuracyModel> getDataByStoreid(@Param("start") int start, @Param("length") int length,
            @Param("sortCol") String sortCol, @Param("sortDir") String sortDir, @Param("storeid") int storeid);

    public int getDataLength();

    public int saveTestInfo(AccuracyModel aam);

    /**
     * @Title: allQueryAccuracy
     * @Description: 查询所有的信息
     * @return
     */
    public List<AccuracyModel> allQueryAccuracy();
}
