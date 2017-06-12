package com.sva.model;

/**
 * 
 * @author wwx283823
 * @version iSoftStone 2016-3-16
 * @since iSoftStone
 */
public class MessagePushModel
{
    private int id;

    private double pushRight;

    private double pushWrong;

    private double notPush;

    private String centerRadius;

    private String centerReality;

    private int isRigth;

    private long updateTime;
    
    private MapsModel map;
    
    private StoreModel store;

    public String getCenterRadius()
    {
        return centerRadius;
    }

    public void setCenterRadius(String centerRadius)
    {
        this.centerRadius = centerRadius;
    }

    public long getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(long updateTime)
    {
        this.updateTime = updateTime;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public double getPushRight()
    {
        return pushRight;
    }

    public void setPushRight(double pushRight)
    {
        this.pushRight = pushRight;
    }

    public double getPushWrong()
    {
        return pushWrong;
    }

    public void setPushWrong(double pushWrong)
    {
        this.pushWrong = pushWrong;
    }

    public double getNotPush()
    {
        return notPush;
    }

    public void setNotPush(double notPush)
    {
        this.notPush = notPush;
    }

    public String getCenterReality()
    {
        return centerReality;
    }

    public void setCenterReality(String centerReality)
    {
        this.centerReality = centerReality;
    }

    public int getIsRigth()
    {
        return isRigth;
    }

    public void setIsRigth(int isRigth)
    {
        this.isRigth = isRigth;
    }

    /**
     * @return the map
     */
    public MapsModel getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(MapsModel map) {
        this.map = map;
    }

    /**
     * @return the store
     */
    public StoreModel getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(StoreModel store) {
        this.store = store;
    }

}
