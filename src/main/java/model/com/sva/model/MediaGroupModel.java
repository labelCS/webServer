/**   
 * @Title: MediaGroupModel.java 
 * @Package com.sva.model 
 * @Description: 多媒体文件分组model
 * @author labelCS   
 * @date 2017年9月6日 下午4:28:34 
 * @version V1.0   
 */
package com.sva.model;

import java.util.List;

/** 
 * @ClassName: MediaGroupModel 
 * @Description: 多媒体文件分组model
 * @author labelCS 
 * @date 2017年9月6日 下午4:28:34 
 *  
 */
public class MediaGroupModel
{
    /** 
     * @Fields id : 分组id
     */ 
    private Integer id;
    
    /** 
     * @Fields name : 分组名称
     */ 
    private String name;
    
    /** 
     * @Fields plays : 播放量
     */ 
    private Long plays;
    
    /** 
     * @Fields playType : 播放类型1：音频、2：视频
     */ 
    private Integer playType;
    
    /** 
     * @Fields medias : 多媒体文件
     */ 
    private List<MediaModel> medias;

    /**
     * @return the id
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the plays
     */
    public Long getPlays()
    {
        return plays;
    }

    /**
     * @param plays the plays to set
     */
    public void setPlays(Long plays)
    {
        this.plays = plays;
    }

    /**
     * @return the playType
     */
    public Integer getPlayType()
    {
        return playType;
    }

    /**
     * @param playType the playType to set
     */
    public void setPlayType(Integer playType)
    {
        this.playType = playType;
    }

    /**
     * @return the medias
     */
    public List<MediaModel> getMedias()
    {
        return medias;
    }

    /**
     * @param medias the medias to set
     */
    public void setMedias(List<MediaModel> medias)
    {
        this.medias = medias;
    }
}
