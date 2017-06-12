/**   
 * @Title: MediaModel.java 
 * @Package com.sva.model 
 * @Description: 多媒体文件model
 * @author labelCS   
 * @date 2017年9月6日 下午4:28:34 
 * @version V1.0   
 */
package com.sva.model;

/** 
 * @ClassName: MediaModel 
 * @Description: 多媒体文件model
 * @author labelCS 
 * @date 2017年9月6日 下午4:28:34 
 *  
 */
public class MediaModel
{
    
    /** 
     * @Fields id : 文件id
     */ 
    private Integer id;
    
    /** 
     * @Fields filePath : 文件路径
     */ 
    private String filePath;
    
    /** 
     * @Fields fileName : 文件名
     */ 
    private String fileName;
    
    /** 
     * @Fields purpose : 文件用途
     */ 
    private Integer purpose;
    
    /** 
     * @Fields groupId : 分组Id
     */ 
    private Integer groupId;

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
     * @return the filePath
     */
    public String getFilePath()
    {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    /**
     * @return the fileName
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * @return the purpose
     */
    public Integer getPurpose()
    {
        return purpose;
    }

    /**
     * @param purpose the purpose to set
     */
    public void setPurpose(Integer purpose)
    {
        this.purpose = purpose;
    }

    /**
     * @return the groupId
     */
    public Integer getGroupId()
    {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(Integer groupId)
    {
        this.groupId = groupId;
    }
}
