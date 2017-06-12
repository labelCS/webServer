package com.sva.model;

import java.math.BigDecimal;
import java.util.List;

public class MessageModel
{
    private int timeInterval;

    private BigDecimal xSpot;

    private BigDecimal ySpot;

    private BigDecimal x1Spot;

    private BigDecimal y1Spot;

    private BigDecimal rangeSpot;

    private String pictruePath;

    private String moviePath;

    private String ticketPath;
    
    private String shopName;

    private String id;
    
    private String messageId;
    
    private StoreModel store;
    
    private MapsModel maps;
    
    private AreaModel area;
    
    private List<TicketModel> tickets;
    
    public String getMessageId()
    {
        return messageId;
    }

    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }

    public String getTicketPath()
    {
        return ticketPath;
    }

    public void setTicketPath(String ticketPath)
    {
        this.ticketPath = ticketPath;
    }

    public BigDecimal getX1Spot()
    {
        return x1Spot;
    }

    public void setX1Spot(BigDecimal x1Spot)
    {
        this.x1Spot = x1Spot;
    }

    public BigDecimal getY1Spot()
    {
        return y1Spot;
    }

    public void setY1Spot(BigDecimal y1Spot)
    {
        this.y1Spot = y1Spot;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getPictruePath()
    {
        return pictruePath;
    }

    public void setPictruePath(String pictruePath)
    {
        this.pictruePath = pictruePath;
    }

    public String getMoviePath()
    {
        return moviePath;
    }

    public void setMoviePath(String moviePath)
    {
        this.moviePath = moviePath;
    }

    public BigDecimal getRangeSpot()
    {
        return rangeSpot;
    }

    public void setRangeSpot(BigDecimal rangeSpot)
    {
        this.rangeSpot = rangeSpot;
    }

    private String message;

    private String isEnable;

    public BigDecimal getxSpot()
    {
        return xSpot;
    }

    public void setxSpot(BigDecimal xSpot)
    {
        this.xSpot = xSpot;
    }

    public BigDecimal getySpot()
    {
        return ySpot;
    }

    public void setySpot(BigDecimal ySpot)
    {
        this.ySpot = ySpot;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getIsEnable()
    {
        return isEnable;
    }

    public void setIsEnable(String isEnable)
    {
        this.isEnable = isEnable;
    }

    public int getTimeInterval()
    {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval)
    {
        this.timeInterval = timeInterval;
    }

    public StoreModel getStore() {
        return store;
    }

    public void setStore(StoreModel store) {
        this.store = store;
    }

    public MapsModel getMaps() {
        return maps;
    }

    public void setMaps(MapsModel maps) {
        this.maps = maps;
    }

    public AreaModel getArea() {
        return area;
    }

    public void setArea(AreaModel area) {
        this.area = area;
    }

    public List<TicketModel> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketModel> tickets) {
        this.tickets = tickets;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

}
