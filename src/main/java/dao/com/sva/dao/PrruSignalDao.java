/**   
 * @Title: PrruSignalDao.java 
 * @Package com.sva.dao 
 * @Description: PrruSignalDao数据库操作类
 * @author labelCS   
 * @date 2016年9月27日 上午10:49:15 
 * @version V1.0   
 */
package com.sva.dao;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.sva.model.FeatureBaseExportModel;
import com.sva.model.PrruFeatureDetailModel;
import com.sva.model.PrruFeatureModel;
import com.sva.model.PrruSignalModel;

/** 
 * @ClassName: PrruSignalDao 
 * @Description: PrruSignalDao数据库操作类
 * @author labelCS 
 * @date 2016年9月27日 上午10:49:15 
 * 
 */
public interface PrruSignalDao {

    /** 
     * @Title: getSignalByUserIdTime 
     * @Description: 获取符合条件的信号数据
     * @param userId
     * @param timestamp
     * @return 
     */
    public List<PrruSignalModel> getSignalByUserIdTime(
            @Param("userId")String userId, @Param("timestamp")long timestamp);
    
    /** 
     * @Title: getOneSignalByUserIdTime 
     * @Description: 获取符合条件的一个时间戳的数据
     * @param userId
     * @param timestamp
     * @return 
     */
    public List<PrruSignalModel> getOneSignalByUserIdTime(
            @Param("userId")String userId, @Param("timestamp")long timestamp);
    
    /** 
     * @Title: getCurrentSignalByUserId 
     * @Description: 获取当前指定用户的信号数据
     * @param userId
     * @return 
     */
    public List<PrruSignalModel> getCurrentSignalByUserId(@Param("userId")String userId);
    
    /** 
     * @Title: getCurrentSignalByUserId 
     * @Description: 获取当前指定用户的2次信号数据
     * @param userId
     * @return 
     */
    public List<PrruSignalModel> getTwoSignalByUserId(@Param("userId")String userId);
    
    /** 
     * @Title: getRelativeFeature 
     * @Description: 获取相关的特征库 
     * @param gpps
     * @return 
     */
    public List<PrruFeatureModel> getRelativeFeature(@Param("gpps")List<String> gpps, @Param("floorNo")String floorNo);
    
    /** 
     * @Title: deleteSignal 
     * @Description: 清空信号表
     * @return 
     */
    public int deleteSignal();
    
    /** 
     * @Title: savePrruFeature 
     * @Description: 特征库入库 
     * @param pm
     * @return 
     */
    public int savePrruFeature(PrruFeatureModel pm);
    
    /** 
     * @Title: savePrruFeatureDetail 
     * @Description: 详细特征值数据入库
     * @param pdm 
     */
    public void savePrruFeatureDetail(List<PrruFeatureDetailModel> pdm);
    
    /** 
     * @Title: getFeatureByPosition 
     * @Description: 查询指定位置是否已存在特征库信息
     * @param x
     * @param y
     * @param floorNo
     * @return 
     */
    public PrruFeatureModel getFeatureByPosition(
            @Param("x")BigDecimal x, @Param("y")BigDecimal y, @Param("floorNo")BigDecimal floorNo);
    
    /** 
     * @Title: getFeatureBaseData 
     * @Description: 获取导出用特征库数据
     * @return 
     */
    public List<FeatureBaseExportModel> getFeatureBaseData(String placeId);
    
    /** 
     * @Title: deletFeatureById 
     * @Description: 删除指定id的特征库信息 
     * @param id
     * @return 
     */
    public int deletFeatureById(int id);
    
    /** 
     * @Title: deleteFeatureDetailByFeatureId 
     * @Description: 删除指定特征库对应的特征值信息 
     * @param featureId
     * @return 
     */
    public int deleteFeatureDetailByFeatureId(int featureId);
    
    /** 
     * @Title: getAllFeaturePostion 
     * @Description: 获取指定楼层的特征点
     * @param floorNo
     * @return 
     */
    public List<PrruFeatureModel> getAllFeaturePostion(String floorNo);
    
    /** 
     * @Title: queryLocationByUseId 
     * @Description: 获取用户所在的楼层
     * @param userId
     * @return 
     */
    public String queryFloorNoByUseId(String userId);

    
    
    /**
     * 根据楼层关联删除模拟点（userid=‘simulate’）数据
     * 
     * @param floorNo
     * @return
     */
    public int delSimulateDataByFloorNo(@Param("floorNo")String floorNo,@Param("eNodeBid")String eNodeBid);
}
