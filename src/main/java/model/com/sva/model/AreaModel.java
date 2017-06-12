package com.sva.model;

import java.math.BigDecimal;

public class AreaModel
{
    private String id;
    
    private int areaId;
    
    private String areaName;

    private BigDecimal xSpot;

    private BigDecimal ySpot;

    private BigDecimal x1Spot;

    private BigDecimal y1Spot;
    
    private BigDecimal floorNo;

    private int status;

    private int zoneId;
    
    private int mapId;
    
    private String isVip;
    
    private MapsModel maps;
    
    private StoreModel store;
    
    private CategoryModel category;
    
    
    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public BigDecimal getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(BigDecimal floorNo) {
        this.floorNo = floorNo;

    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getZoneId()
    {
        return zoneId;
    }

    public void setZoneId(int zoneId)
    {
        this.zoneId = zoneId;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

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

    public String getAreaName()
    {
        return areaName;
    }

    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

    public MapsModel getMaps() {
        return maps;
    }

    public void setMaps(MapsModel maps) {
        this.maps = maps;
    }

    public StoreModel getStore() {
        return store;
    }

    public void setStore(StoreModel store) {
        this.store = store;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }
    
}
