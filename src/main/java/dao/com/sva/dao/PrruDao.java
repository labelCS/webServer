/**   
 * @Title: PrruDao.java 
 * @Package com.sva.dao 
 * @Description: PrruDao接口类
 * @author labelCS   
 * @date 2016年10月12日 上午11:09:54 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sva.model.PrruModel;

/** 
 * @ClassName: PrruDao
 * @Description: PrruDao接口类
 * @author labelCS 
 * @date 2016年10月12日 上午11:09:54 
 *  
 */
public interface PrruDao {
    public List<PrruModel> getPrruInfoByflooNo(String floorNo);

    public int saveInfo(PrruModel bm);

    public int deleteInfo(@Param("floorNo")String floorNo, @Param("eNodeBid")String eNodeBid);

    public int checkByFloorNo(@Param("floorNo")String floorNo, @Param("id")String id);

    public int updateInfo(@Param("floorNo")String floorNo, @Param("newfloorNo")String newfloorNo);

    public List<Map<String, Object>> getSignal(@Param("userId")String userId,@Param("time")long time);
    
    public List<PrruModel> getPrruInfoByfloorNo(@Param("floorNo")String floorNo, @Param("eNodeBid")String eNodeBid, @Param("cellId")String cellId);
    
    public List<PrruModel> getPrruInfo(@Param("floorNo")String floorNo, @Param("eNodeBid")String eNodeBid);
}
