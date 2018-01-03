/**   
 * @Title: StoreMngModel.java 
 * @Package com.sva.web.models 
 * @Description: 用户输入的商场信息model
 * @author labelCS   
 * @date 2017年12月28日 下午3:20:10 
 * @version V1.0   
 */
package com.sva.web.models;

import org.hibernate.validator.constraints.NotEmpty;

/** 
 * @ClassName: StoreMngModel 
 * @Description: 用户输入的商场信息model
 * @author labelCS 
 * @date 2017年12月28日 下午3:20:10 
 *  
 */
public class StoreMngModel
{
    private String id;

    @NotEmpty(message="{NotEmpty.storeMngModel.name}")
    private String name;

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
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
    
    /* (非 Javadoc) 
     * <p>Title: toString</p> 
     * <p>Description: </p> 
     * @return 
     * @see java.lang.Object#toString() 
     */
    @Override
    public String toString()
    {
        return "Store [id=" + id + ", name=" + name
                + "]";
    }
}
