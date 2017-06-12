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
    
    private String id;
    
    private String msgId;
    
    private String phoneNumber;
    
    private String catchTime;
    
    private String ticketType;
    
    
    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getCatchTime() {
        return catchTime;
    }

    public void setCatchTime(String catchTime) {
        this.catchTime = catchTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

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
 