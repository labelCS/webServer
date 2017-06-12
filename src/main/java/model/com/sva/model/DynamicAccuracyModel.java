package com.sva.model;

import java.math.BigDecimal;
import java.util.Date;

public class DynamicAccuracyModel
{
    private int id;

    private String origin;// 起点

    private String destination; // 终点

    private Date startDate;

    private Date endDate;

    private BigDecimal avgeOffset; // 平均误差

    private BigDecimal maxOffset; // 最大误差

    private BigDecimal offset; // 误差

    private String triggerIp;

    private int count3;

    private int count5;

    private int count10;

    private int count10p;
    
    private BigDecimal deviation;

    private String detail;
    
    private MapsModel maps;
    
    private StoreModel store;
    
    private EstimateModel estimate;

    public String getTriggerIp()
    {
        return triggerIp;
    }

    public void setTriggerIp(String triggerIp)
    {
        this.triggerIp = triggerIp;
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

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startdate)
    {
        this.startDate = startdate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date enddate)
    {
        this.endDate = enddate;
    }

    public BigDecimal getAvgeOffset()
    {
        return avgeOffset;
    }

    public void setAvgeOffset(BigDecimal avgeOffset)
    {
        this.avgeOffset = avgeOffset;
    }

    public BigDecimal getMaxOffset()
    {
        return maxOffset;
    }

    public void setMaxOffset(BigDecimal maxOffset)
    {
        this.maxOffset = maxOffset;
    }

    public BigDecimal getOffset()
    {
        return offset;
    }

    public void setOffset(BigDecimal offset)
    {
        this.offset = offset;
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

    /**
     * @return the deviation
     */
    public BigDecimal getDeviation() {
        return deviation;
    }

    /**
     * @param deviation the deviation to set
     */
    public void setDeviation(BigDecimal deviation) {
        this.deviation = deviation;
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
