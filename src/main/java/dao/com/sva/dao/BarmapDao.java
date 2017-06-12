/**    
 * @Title:  BarmapDao.java   
 * @Package com.sva.dao   
 * @Description:   BarmapDao接口类   
 * @author: LabelCS    
 * @date:   2016年9月1日 下午10:31:19   
 * @version V1.0     
 */  
package com.sva.dao;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.sva.model.StatisticFloorModel;

/**   
 * @ClassName:  IBarmapDao   
 * @Description:BarmapDao接口类   
 * @author: LabelCS  
 * @date:   2016年9月1日 下午10:31:19   
 *      
 */
public interface BarmapDao {
    
    public List<StatisticFloorModel> getData(@Param("placeId")String placeId, @Param("start")String start, 
            @Param("end")String end);
    
    public int getTotalCount(@Param("placeId")String placeId, @Param("start")String start, @Param("end")String end);

}
