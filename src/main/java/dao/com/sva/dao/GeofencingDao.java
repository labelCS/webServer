/**   
 * @Title: GeofencingDao.java 
 * @Package com.sva.dao 
 * @Description: GeofencingDao接口类 
 * @author labelCS   
 * @date 2016年9月21日 上午9:55:30 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.sva.model.GeofencingModel;

/** 
 * @ClassName: GeofencingDao 
 * @Description: GeofencingDao接口类
 * @author labelCS 
 * @date 2016年9月21日 上午9:55:30 
 *  
 */
public interface GeofencingDao {
    public List<GeofencingModel> getGeofencing(@Param("zoneid")String zoneid, @Param("userid")String userid);
    
    public List<GeofencingModel> getGeofencingByZoneId(String zoneid);
}
