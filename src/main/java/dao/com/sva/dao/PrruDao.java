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
    public List<PrruModel> getPrruInfoByflooNo(String mapId);

    public int saveInfo(PrruModel bm);

    public int deleteInfo(int mapId);

    public int checkByFloorNo(@Param("mapId")String mapId, @Param("id")String id);

    public int updateInfo(@Param("mapId")String mapId, @Param("newfloorNo")String newfloorNo);

    public List<Map<String, Object>> getSignal(@Param("userId")String userId,@Param("time")long time);
}
