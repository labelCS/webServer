package com.sva.model;

import java.math.BigDecimal;
import java.util.Date;

public class AccuracyModel
{
    private int id;

    private String origin;

    private String destination;

    private Date startdate;

    private Date enddate;

    private String triggerIp;

    private BigDecimal offset;

    private BigDecimal variance;

    private int count3;

    private int count5;

    private int count10;

    private int count10p;

    private String detail;

    private String type;

    private BigDecimal averDevi;
    
    private MapsModel maps;
    
    private StoreModel store;
    
    private EstimateModel estimate;

    public Date getStartdate()
    {
        return startdate;
    }

    public void setStartdate(Date startdate)
    {
        this.startdate = startdate;
    }

    public Date getEnddate()
    {
        return enddate;
    }

    public void setEnddate(Date enddate)
    {
        this.enddate = enddate;
    }

    public int getCount3()
    {
        return count3;
    }

    public void setCount3(int count3)
    {
        this.count3 = count3;
    }

    public int getCount5()
    {
        return count5;
    }

    public void setCount5(int count5)
    {
        this.count5 = count5;
    }

    public int getCount10()
    {
        return count10;
    }

    public void setCount10(int count10)
    {
        this.count10 = count10;
    }

    public int getCount10p()
    {
        return count10p;
    }

    public void setCount10p(int count10p)
    {
        this.count10p = count10p;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getOrigin()
    {
        return origin;
    }

    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public String getDestination()
    {
        return destination;
    }

    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    public String getTriggerIp()
    {
        return triggerIp;
    }

    public void setTriggerIp(String triggerIp)
    {
        this.triggerIp = triggerIp;
    }

    public BigDecimal getOffset()
    {
        return offset;
    }

    public void setOffset(BigDecimal offset)
    {
        this.offset = offset;
    }

    public BigDecimal getVariance()
    {
        return variance;
    }

    public void setVariance(BigDecimal variance)
    {
        this.variance = variance;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public BigDecimal getAverDevi()
    {
        return averDevi;
    }

    public void setAverDevi(BigDecimal averDevi)
    {
        this.averDevi = averDevi;
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

    /**
     * @return the estimate
     */
    public EstimateModel getEstimate() {
        return estimate;
    }

    /**
     * @param estimate the estimate to set
     */
    public void setEstimate(EstimateModel estimate) {
        this.estimate = estimate;
    }
    
}
