/**   
 * @Title: ParamDao.java 
 * @Package com.sva.dao 
 * @Description: ParamDao接口类 
 * @author labelCS   
 * @date 2016年10月12日 上午10:37:51 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;
import com.sva.model.ParamModel;

/** 
 * @ClassName: ParamDao 
 * @Description: ParamDao接口类 
 * @author labelCS 
 * @date 2016年10月12日 上午10:37:51 
 *  
 */
public interface ParamDao {
    public List<ParamModel> doquery();

    /** 
     * @Title: parJamUpdateTime 
     * @Description: 查询参数更新时间，用于判断是时间是都变化
     * @return 
     */
    public String paramUpdateTime();

    public int saveData(ParamModel sm);
}
