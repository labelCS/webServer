/**   
 * @Title: PrruFeatureModel.java 
 * @Package com.sva.model 
 * @Description: PrruFeatureModel实体类
 * @author labelCS   
 * @date 2016年9月27日 上午10:41:10 
 * @version V1.0   
 */
package com.sva.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * @ClassName: PrruFeatureModel 
 * @Description: PrruFeatureModel实体类
 * @author labelCS 
 * @date 2016年9月27日 上午10:41:10 
 *  
 */
public class PrruFeatureModel {
    
    private int id;
    private String x;
    private String y;
    private String floorNo;
    private BigDecimal checkValue;
    private BigDecimal featureRadius;
    private String userId;
    private String gpp;
    private BigDecimal featureValue;
    private String eNodeBid;
    private long timestamp;
    List<PrruFeatureDetailModel> featureValues;
    List<PrruFeatureDetailModel> featureValuesWifi;
    List<PrruFeatureDetailModel> featureValuesBlue;
    Map<String, BigDecimal> lteGppToFeatureValue;
    Map<String, BigDecimal> wifiGppToFeatureValue;
    Map<String, BigDecimal> blueGppToFeatureValue;
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the x
     */
    public String getX() {
        return x;
    }
    /**
     * @param x the x to set
     */
    public void setX(String x) {
        this.x = x;
    }
    /**
     * @return the y
     */
    public String getY() {
        return y;
    }
    /**
     * @param y the y to set
     */
    public void setY(String y) {
        this.y = y;
    }
    /**
     * @return the floorNo
     */
    public String getFloorNo() {
        return floorNo;
    }
    /**
     * @param floorNo the floorNo to set
     */
    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }
    /**
     * @return the checkValue
     */
    public BigDecimal getCheckValue() {
        return checkValue;
    }
    /**
     * @param checkValue the checkValue to set
     */
    public void setCheckValue(BigDecimal checkValue) {
        this.checkValue = checkValue;
    }
    /**
     * @return the featureRadius
     */
    public BigDecimal getFeatureRadius() {
        return featureRadius;
    }
    /**
     * @param featureRadius the featureRadius to set
     */
    public void setFeatureRadius(BigDecimal featureRadius) {
        this.featureRadius = featureRadius;
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
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }
    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    /**
     * @return the featureValues
     */
    public List<PrruFeatureDetailModel> getFeatureValues() {
        return featureValues;
    }
    
    /**
     * @return the featureValuesLst
     */
    public Map<String, BigDecimal> getFeatureValuesByType(String type) {
        if(type=="1"){
        	return lteGppToFeatureValue;
        }
        else if(type=="2"){
        	return wifiGppToFeatureValue;
        }
        
    	return blueGppToFeatureValue;
    }
    /**
     * @param featureValuesByType the featureValues to set
     */
    public void setFeatureValuesByType(Map<String, BigDecimal> featureValues,String type) {
        if(type=="1"){
        	this.lteGppToFeatureValue = featureValues;
        }
        else if(type=="2"){
        	this.wifiGppToFeatureValue = featureValues;
        }
        else{
        	this.blueGppToFeatureValue = featureValues;
        }
    }
    
    /**
     * @param featureValues the featureValues to set
     */
    public void setFeatureValues(List<PrruFeatureDetailModel> featureValues) {
        this.featureValues = featureValues;
    }
    /**
     * @return the gpp
     */
    public String getGpp() {
        return gpp;
    }
    /**
     * @param gpp the gpp to set
     */
    public void setGpp(String gpp) {
        this.gpp = gpp;
    }
    /**
     * @return the featureValue
     */
    public BigDecimal getFeatureValue() {
        return featureValue;
    }
    /**
     * @param featureValue the featureValue to set
     */
    public void setFeatureValue(BigDecimal featureValue) {
        this.featureValue = featureValue;
    }
	public String geteNodeBid() {
		return eNodeBid;
	}
	public void seteNodeBid(String eNodeBid) {
		this.eNodeBid = eNodeBid;
	}
}
