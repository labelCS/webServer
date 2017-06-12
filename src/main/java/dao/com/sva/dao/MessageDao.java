/**    
 * @Title:  MessageDao.java   
 * @Package com.sva.dao   
 * @Description:  MessageDao接口类   
 * @author: LabelCS    
 * @date:   2016年9月25日 下午5:58:19   
 * @version V1.0     
 */  
package com.sva.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.sva.model.LocationModel;
import com.sva.model.MediaGroupModel;
import com.sva.model.MessageModel;

/**   
 * @ClassName:  MessageDao   
 * @Description: MessageDao接口类 
 * @author: LabelCS  
 * @date:   2016年9月25日 下午5:58:19   
 *      
 */
public interface MessageDao {
    public List<MessageModel> doquery();

    /*
     * 通过用户名获取相对应的商场权限
     */
    public List<MessageModel> doqueryByStoreid(int storeId);

    // 此方法把表对应的字段查询出来依次放入model中
    public List<MessageModel> queryByLocation(LocationModel loc);

    public List<MessageModel> queryByLocation1(LocationModel loc);

    public List<MessageModel> queryByLocation3(LocationModel loc);

    public int saveMsgInfo(MessageModel mmm);

    public int deleteMessage(@Param("xSpot")String xSpot, @Param("ySpot")String ySpot, 
            @Param("floorNo")String floorNo);

    public int updateMsgInfo(MessageModel mmm);

    public Integer getNumberByArea(MessageModel mm, String tableName);

    public List<MessageModel> getAllMessageData();
    
    public List<MessageModel> getAllMessageDataByAreaId(String areaId);

    public List<MessageModel> getAllMessageDataByAreaIdNew(String areaId);
    //
    public String getAreaIdByMessage(String messageId);
    
    public List<String> getTiketPathByAreaId(String areaId);

    public List<String> getMsgId(String areaId);
    
    /** 
     * @Title: getMediaByAreaId 
     * @Description: 根据区域id，获取对应的多媒体文件列表
     * @param areaId
     * @return 
     */
    public List<MediaGroupModel> getMediaByAreaId(@Param("areaId")String areaId);
}
