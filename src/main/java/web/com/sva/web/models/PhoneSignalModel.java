/**   
 * @Title: PhoneSignalModel.java 
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
public class PhoneSignalModel {
    
    /** 
     * @Fields userId : 执行采集任务的用户id
     */ 
    private String userId;
    
    /** 
     * @Fields signal : 信号wifi
     */ 
    private String signalWIFI;
    
    
    /** 
     * @Fields signal : 信号蓝牙
     */ 
    private String signalBlue;
    
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
    public String getSignalWIFI() {
        return signalWIFI;
    }

    /**
     * @param x the x to set
     */
    public void setSignalWIFI(String signal) {
        this.signalWIFI = signal;
    }
    
    /**
     * @return the x
     */
    public String getSignalBlue() {
        return signalBlue;
    }

    /**
     * @param x the x to set
     */
    public void setSignalBlue(String signal) {
        this.signalBlue = signal;
    }

    
}
