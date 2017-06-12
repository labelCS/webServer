/**   
 * @Title: LocationDelayDao.java 
 * @Package com.sva.dao 
 * @Description: LocationDelayDao接口类 
 * @author labelCS   
 * @date 2016年9月22日 下午4:50:51 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.sva.model.LocationDelayModel;

/** 
 * @ClassName: LocationDelayDao 
 * @Description: LocationDelayDao接口类
 * @author labelCS 
 * @date 2016年9月22日 下午4:50:51 
 *  
 */
public interface LocationDelayDao {
    public List<LocationDelayModel> getAllData();
    
    public List<LocationDelayModel> getAllDatas(@Param("startTime")long startTime, @Param("endTime")long endTime);
    
    public List<LocationDelayModel> getDataByPlaceIdTime(@Param("startTime")long startTime, @Param("endTime")long endTime, 
            @Param("placeId")String placeId);
    
    public List<LocationDelayModel> getAllDataByStoreid(int storeid);

    public int savaMessagePush(LocationDelayModel aam);
}
