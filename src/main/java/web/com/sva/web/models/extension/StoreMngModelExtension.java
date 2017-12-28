/**   
 * @Title: StoreMngModelExtension.java 
 * @Package com.sva.web.models.extension 
 * @Description: 商场页面model转化为持久层model
 * @author labelCS   
 * @date 2017年12月28日 下午4:16:59 
 * @version V1.0   
 */
package com.sva.web.models.extension;

import java.util.Date;
import com.sva.model.StoreModel;
import com.sva.web.models.StoreMngModel;

/** 
 * @ClassName: StoreMngModelExtension 
 * @Description: 商场页面model转化为持久层model
 * @author labelCS 
 * @date 2017年12月28日 下午4:16:59 
 *  
 */
public class StoreMngModelExtension
{
    public static StoreModel toStore(StoreMngModel smm){
        StoreModel sm = new StoreModel();
        Date current = new Date();
        sm.setName(smm.getName());
        sm.setCreateTime(current);
        sm.setUpdateTime(current);
        
        return sm;
    }
}
