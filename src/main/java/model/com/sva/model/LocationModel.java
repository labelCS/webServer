package com.sva.model;

import java.math.BigDecimal;

public class LocationModel
{
    private String idType;

    private BigDecimal timestamp;
    
    private BigDecimal timestampSva;
    
    private BigDecimal timestampPrru;

    private String dataType;

    private BigDecimal x;

    private BigDecimal y;

    private BigDecimal z;

    private String userID;
    
    private String svaId;

    private MapsModel maps;

    public String getIdType()
    {
        return idType;
    }

    public void setIdType(String idType)
    {
        this.idType = idType;
    }

    public BigDecimal getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(BigDecimal timestamp)
    {
        this.timestamp = timestamp;
    }

    /**
     * @return the timestampSva
     */
    public BigDecimal getTimestampSva()
    {
        return timestampSva;
    }

    /**
     * @param timestampSva the timestampSva to set
     */
    public void setTimestampSva(BigDecimal timestampSva)
    {
        this.timestampSva = timestampSva;
    }

    public String getDataType()
    {
        return dataType;
    }

    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }

    public BigDecimal getX()
    {
        return x;
    }

    public void setX(BigDecimal x)
    {
        this.x = x;
    }

    public BigDecimal getY()
    {
        return y;
    }

    public void setY(BigDecimal y)
    {
        this.y = y;
    }

    public BigDecimal getZ()
    {
        return z;
    }

    public void setZ(BigDecimal z)
    {
        this.z = z;
    }

    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    /**
     * @return the svaId
     */
    public String getSvaId()
    {
        return svaId;
    }

    /**
     * @param svaId the svaId to set
     */
    public void setSvaId(String svaId)
    {
        this.svaId = svaId;
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
     * @return the timeLocal
     */
    public BigDecimal getTimestampPrru() {
        return timestampPrru;
    }

    /**
     * @param timeLocal the timeLocal to set
     */
    public void setTimestampPrru(BigDecimal timestampPrru) {
        this.timestampPrru = timestampPrru;
    }

}
