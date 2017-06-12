package com.sva.model;

/** 
 * @ClassName: TicketModel 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author JunWang 
 * @date 2016年11月28日 上午11:06:18 
 *  
 */
public class TicketModel
{   
    //奖券路径
    private String ticketPath;
    //概率
    private String chances;
    //消息Id
    private MessageModel msg;
    
    private int msgId;
    
    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    private String id;

    public MessageModel getMsg() {
        return msg;
    }

    public void setMsg(MessageModel msg) {
        this.msg = msg;
    }

    public String getTicketPath()
    {
        return ticketPath;
    }

    public void setTicketPath(String ticketPath)
    {
        this.ticketPath = ticketPath;
    }

    public String getChances()
    {
        return chances;
    }

    public void setChances(String chances)
    {
        this.chances = chances;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}   
 