package com.sva.model;

import java.math.BigDecimal;

public class EstimateModel
{
    private String id;

    private BigDecimal a;

    private BigDecimal b;

    private int n;

    private BigDecimal type;

    private BigDecimal d;

    private BigDecimal deviation;
    
    private MapsModel maps;
    
    private StoreModel store;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public BigDecimal getA()
    {
        return a;
    }

    public void setA(BigDecimal a)
    {
        this.a = a;
    }

    public BigDecimal getB()
    {
        return b;
    }

    public void setB(BigDecimal b)
    {
        this.b = b;
    }

    public int getN()
    {
        return n;
    }

    public void setN(int n)
    {
        this.n = n;
    }

    public BigDecimal getType()
    {
        return type;
    }

    public void setType(BigDecimal type)
    {
        this.type = type;
    }

    public BigDecimal getD()
    {
        return d;
    }

    public void setD(BigDecimal d)
    {
        this.d = d;
    }

    public BigDecimal getDeviation()
    {
        return deviation;
    }

    public void setDeviation(BigDecimal deviation)
    {
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

}
