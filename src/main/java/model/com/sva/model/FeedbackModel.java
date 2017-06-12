package com.sva.model;

public class FeedbackModel {
    
    /** 
     * @Fields platform :手机版本
     */ 
    private String platform;
    /** 
     * @Fields carrier : 运营商
     */ 
    private String carrier;
    /** 
     * @Fields phoneModel : 手机型号 
     */ 
    private String phoneModel;
    /** 
     * @Fields user : 反馈人
     */ 
    private String user;
    /** 
     * @Fields content : 反馈内容 
     */ 
    private String content;
    
    private String creatTime;

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
