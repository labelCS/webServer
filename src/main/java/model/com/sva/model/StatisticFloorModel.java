package com.sva.model;

import java.math.BigDecimal;
import java.util.Date;

public class StatisticFloorModel
{
    private BigDecimal z;
    
    private String userId;
    
    private Date time;
    
    private int number;
    
    private MapsModel maps;

    public BigDecimal getZ()
    {
        return z;
    }

    public void setZ(BigDecimal z)
    {
        this.z = z;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the maps
     */
    public MapsModel getMaps() {
        return maps;
    }

    /**
     * @param maps the maps to set
     */
    public void setMaps(MapsModel maps) {
        this.maps = maps;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

}
