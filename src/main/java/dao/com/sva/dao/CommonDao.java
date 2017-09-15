/**   
 * @Title: CommonDao.java 
 * @Package com.sva.dao 
 * @Description: CommonDao接口类
 * @author labelCS   
 * @date 2016年9月19日 下午4:25:30 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sva.model.FeedbackModel;

/** 
 * @ClassName: CommonDao 
 * @Description: CommonDao接口类
 * @author labelCS 
 * @date 2016年9月19日 下午4:25:30 
 *  
 */
public interface CommonDao {
    public int createTable(String tableName);

    public int isTableExist(@Param("tableName")String tableName, @Param("schema")String schema);

    public int doUpdate(String sql);

    public int doTest(String sql); 
    
    public List<Map<String, Object>> doTest1(String sql);

    public List<String> doDeleteInfo(String time);

    public int deleteTable(String tableName);

    public List<Map<String, Object>> getStatisticTemp();

    public List<Map<String, Object>> getDataToday(String time);

    public List<Map<String, Object>> getAllPeople(@Param("tableName")String tableName, @Param("time")long time);

    public List<Map<String, Object>> getAllArea();

    public List<Map<String, Object>> getAreaVisitTime(String areaId);

    public int saveVisitiTime(String sql);
    
    public int saveFeedback(FeedbackModel model);
    
    public int countLocationDataNum(@Param("tableName")String tableName, @Param("begin")long begin, @Param("end")long end, @Param("svaid")int svaid);
}