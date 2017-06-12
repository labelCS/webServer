/**   
 * @Title: PushMsgDao.java 
 * @Package com.sva.dao 
 * @Description: PushMsgDao接口类 
 * @author labelCS   
 * @date 2016年10月12日 上午11:35:04 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/** 
 * @ClassName: PushMsgDao
 * @Description: PushMsgDao接口类 
 * @author labelCS 
 * @date 2016年10月12日 上午11:35:04 
 *  
 */
public interface PushMsgDao {
    /** 
     * @Title: getMessageByUserId 
     * @Description: 通过userid查询出对应的消息
     * @param userId
     * @return
     * @throws 
     */
    public List<Map<String,Object>> getMessageByUserId(String userId);
    
    /** 
     * @Title: saveMessage 
     * @Description: 保存消息
     * @param userId
     * @param content
     * @throws 
     */
    public int saveMessage(@Param("userId")String userId, @Param("content")String content); 
    
    /** 
     * @Title: deleteMessageByUserId 
     * @Description: 根据id删除消息
     * @param userId
     * @throws 
     */
    public int deleteMessageById(String id); 
}
