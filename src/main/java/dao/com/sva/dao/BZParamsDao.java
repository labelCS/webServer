/**   
 * @Title: BZParamsDao.java 
 * @Package com.sva.dao 
 * @Description: BZParamsDao接口类 
 * @author labelCS   
 * @date 2016年9月19日 上午10:16:39 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;
import java.util.Map;

import com.sva.model.BZPramesModel;
import com.sva.model.BZPramesModel1;

/** 
 * @ClassName: BZParamsDao 
 * @Description: BZParamsDao接口类 
 * @author labelCS 
 * @date 2016年9月19日 上午10:16:39 
 *  
 */
public interface BZParamsDao {
    public List<BZPramesModel> doquery();
    
    public List<BZPramesModel> doquery3();
    
    public List<BZPramesModel> doquery4();
    
    public List<BZPramesModel> doquery1();

    public int updateBZInfo(BZPramesModel mmm);
    
    public int updateSHInfo(BZPramesModel mmm);
    
    /** 
     * @Title: updateSHInfoJing 
     * @Description: 菁蓉镇参数配置sql
     * @param mmm
     * @throws SQLException void       
     * @throws 
     */
    public int updateSHInfoJing(BZPramesModel mmm);

    public List<Map<String, Object>> getBzData(String placeId);

    public List<Map<String, Object>> getBzAllData(String placeId);
    
    public List<Map<String, Object>> getAllFloorNo(String id);
    
    public List<Map<String, Object>> getAllFloorNo2(String id);
    
    /** 
     * @Title: getAllFloorNoJing 
     * @Description: 菁蓉镇数据sql
     * @param id
     * @return List<Map<String,Object>>       
     * @throws 
     */
    public List<Map<String, Object>> getAllFloorNoJing(String id);
    
    public List<BZPramesModel1> doquery2(); 

    public int updateBZInfo1(BZPramesModel1 mmm);
}
