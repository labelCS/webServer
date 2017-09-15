/**   
 * @Title: PrruFeatureApiModel.java 
 * @Package com.sva.web.models 
 * @Description: 客户端传入数据model
 * @author labelCS   
 * @date 2016年9月28日 下午5:42:49 
 * @version V1.0   
 */
package com.sva.web.models;

import java.math.BigDecimal;

import com.sva.common.ConvertUtil;

/** 
 * @ClassName: PrruFeatureApiModel 
 * @Description: 客户端传入数据model
 * @author labelCS 
 * @date 2016年9月28日 下午5:42:49 
 *  
 */
public class PrruFeatureApiModel {
    /** 
     * @Fields length : 采集信号的最大条数
     */ 
    private int length;
    
    /** 
     * @Fields timeInSeconds : 采集prru的最长时间 
     */ 
    private int timeInSeconds;
    
    /** 
     * @Fields userId : 执行采集任务的用户id
     */ 
    private String userId;
    
    /** 
     * @Fields type : 采集类型 1:定点采集；2：线路采集
     */ 
    private int type;
    
    /** 
     * @Fields switchLTE : 0关闭，1打开
     */ 
    private int switchLTE;
    
    /** 
     * @Fields switchWIFI : 0关闭，1打开
     */ 
    private int switchWIFI;
    
    /** 
     * @Fields switchBlue : 0关闭，1打开
     */ 
    private int switchBlue;
    
    
    /** 
     * @Fields x : 采集点坐标x
     */ 
    private BigDecimal x;
    
    /** 
     * @Fields y : 采集点坐标y 
     */ 
    private BigDecimal y;
    
    /** 
     * @Fields floorNo : 采集点所在楼层id 
     */ 
    private BigDecimal floorNo;
    
    /** 
     * @Fields radius : 有效半径
     */ 
    private BigDecimal radius;

    
    /** 
     * @Fields x : 采集终点坐标x
     */ 
    private BigDecimal finalx;
    
    /** 
     * @Fields y : 采集终点坐标y 
     */ 
    private BigDecimal finaly;
    
    /**
     * @return the switchLTE
     */
    public int getSwitchLTE() {
        return switchLTE;
    }
    /**
     * @return the switchWIFI
     */
    public int getSwitchWIFI() {
        return switchWIFI;
    }
    /**
     * @return the switchWIFI
     */
    public int getSwitchBlue() {
        return switchBlue;
    }
    
    /**
     * @return the switchLTE
     */
    public void setSwitchLTE(int switchLTE) {
        this.switchLTE = switchLTE;
    }
    /**
     * @return the switchWIFI
     */
    public void setSwitchWIFI(int switchWIFI) {
        this.switchWIFI = switchWIFI;
    }
    /**
     * @return the switchWIFI
     */
    public void setSwitchBlue(int switchBlue) {
        this.switchBlue = switchBlue;
    }
    
    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }
    
    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return the timeInSeconds
     */
    public int getTimeInSeconds() {
        return timeInSeconds;
    }

    /**
     * @param timeInSeconds the timeInSeconds to set
     */
    public void setTimeInSeconds(int timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    /**
     * @return the userId
     */
    public String getUserId() {        
        return ConvertUtil.convertMacOrIp(userId);
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the x
     */
    public BigDecimal getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(BigDecimal x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public BigDecimal getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(BigDecimal y) {
        this.y = y;
    }

    /**
     * @return the x
     */
    public BigDecimal getFinalx() {
        return finalx;
    }

    /**
     * @param x the x to set
     */
    public void setFinalx(BigDecimal x) {
        this.finalx = x;
    }

    /**
     * @return the y
     */
    public BigDecimal getFinaly() {
        return finaly;
    }

    /**
     * @param final_y the y to set
     */
    public void setFinaly(BigDecimal y) {
        this.finaly = y;
    }
    /**
     * @return the floorNo
     */
    public BigDecimal getFloorNo() {
        return floorNo;
    }

    /**
     * @param floorNo the floorNo to set
     */
    public void setFloorNo(BigDecimal floorNo) {
        this.floorNo = floorNo;
    }

    /**
     * @return the radius
     */
    public BigDecimal getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(BigDecimal radius) {
        this.radius = radius;
    }
    
}
