/**   
 * @Title: PhoneDao.java 
 * @Package com.sva.dao 
 * @Description: PhoneDao接口类 
 * @author labelCS   
 * @date 2016年10月12日 上午11:00:08 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;
import com.sva.model.PhoneModel;

/** 
 * @ClassName: PhoneDao 
 * @Description: PhoneDao接口类
 * @author labelCS 
 * @date 2016年10月12日 上午11:00:08 
 *  
 */
public interface PhoneDao {
    public int savePhone(PhoneModel phoneModel);

    public List<PhoneModel> getAllData();
}
