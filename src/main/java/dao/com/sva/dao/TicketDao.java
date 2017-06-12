package com.sva.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sva.model.TicketModel;


/** 
 * @ClassName: TicketDao 
 * @Description: 奖券接口
 * @author JunWang 
 * @date 2016年9月21日 上午9:55:38 
 *  
 */
public interface TicketDao
{

    /** 
     * @Title: getAllTicket 
     * @Description: 获取所有的奖券信息 
     * @param msgId
     * @return 
     */
    public List<TicketModel> getAllTicket(String msgId);

    /** 
     * @Title: getAllTicketById 
     * @Description: 根据消息id获取奖券信息 
     * @param placeId
     * @param msgId
     * @return 
     */
    public List<TicketModel> getAllTicketById(@Param("placeId")int placeId, @Param("msgId")String msgId);

    /** 
     * @Title: saveTicket 
     * @Description: 保存奖券 信息
     * @param model
     * @return 
     */
    public int saveTicket(TicketModel model);
 
    /** 
     * @Title: updataTicket 
     * @Description: 修改奖券信息
     * @param model
     * @return 
     */
    public int updataTicket(TicketModel model);

    /** 
     * @Title: deleteTicket 
     * @Description: 删除指定的奖券信息
     * @param id
     * @return 
     */
    public int deleteTicket(String id);

    /** 
     * @Title: getTiketPathByMsgId 
     * @Description: 通过消息id获取所有的奖券
     * @param areaId
     * @return List<String>       
     * @throws 
     */
    public  List<Map<String, Object>>  getTiketPathByMsgId(String msgId);
}
