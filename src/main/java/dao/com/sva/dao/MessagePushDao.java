/**   
 * @Title: MessagePushDao.java 
 * @Package com.sva.dao 
 * @Description: MessagePushDao接口类  
 * @author labelCS   
 * @date 2016年10月12日 上午9:21:40 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sva.model.MessagePushModel;

/** 
 * @ClassName: MessagePushDao 
 * @Description: MessagePushDao接口类 
 * @author labelCS 
 * @date 2016年10月12日 上午9:21:40 
 *  
 */
public interface MessagePushDao {
    public List<MessagePushModel> getDataByPlaceIdTime(@Param("startTime")long startTime, @Param("endTime")long endTime, 
            @Param("placeId")String placeId);
    
    public List<MessagePushModel> getAllDatas(@Param("startTime")long startTime, @Param("endTime")long endTime);
    
    public List<MessagePushModel> getAllData();

    public List<MessagePushModel> getAllDataByStoreId(int storeid);

    public int savaMessagePush(MessagePushModel aam);
}
