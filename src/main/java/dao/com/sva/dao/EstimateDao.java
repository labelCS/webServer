/**   
 * @Title: EstimateDao.java 
 * @Package com.sva.dao 
 * @Description: EstimateDao接口类 
 * @author labelCS   
 * @date 2016年9月20日 下午5:02:21 
 * @version V1.0   
 */
package com.sva.dao;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.sva.model.EstimateModel;

/** 
 * @ClassName: EstimateDao 
 * @Description: EstimateDao接口类 
 * @author labelCS 
 * @date 2016年9月20日 下午5:02:21 
 *  
 */
public interface EstimateDao {
    /** 
     * @Title: doquery 
     * @Description: 此方法把表对应的字段查询出来依次放入model中
     * @return 
     */
    public List<EstimateModel> doquery();

    /** 
     * @Title: doqueryByStoreid 
     * @Description: 通过用户名获取相对应的商场权限 
     * @param storeid
     * @return 
     */
    public List<EstimateModel> doqueryByStoreid(int storeid);

    /** 
     * @Title: getEstimate 
     * @Description: 此方法把表对应的字段查询出来依次放入model中 
     * @param floorNo
     * @return 
     */
    public BigDecimal getEstimate(String floorNo);

    public int saveInfo(EstimateModel em);

    public int deleteInfo(String id);

    public int updateInfo(EstimateModel em);

    public List<EstimateModel> selectID1(@Param("floorNo")BigDecimal floorNo, @Param("id")String id);

    public String getFloorByFloorNo(String floorNo);

    public int checkByFloorNo(String floorNo);

    public int checkByFloorNo1(@Param("floorNo")String floorNo, @Param("id")String id);
}
