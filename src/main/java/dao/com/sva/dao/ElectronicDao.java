/**   
 * @Title: ElectronicDao.java 
 * @Package com.sva.dao 
 * @Description: ElectronicDao接口类 
 * @author labelCS   
 * @date 2016年9月20日 下午2:10:32 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.sva.model.ElectronicModel;

/** 
 * @ClassName: ElectronicDao
 * @Description: ElectronicDao接口类 
 * @author labelCS 
 * @date 2016年9月20日 下午2:10:32 
 *  
 */
public interface ElectronicDao {
    public List<ElectronicModel> doquery();

    public List<ElectronicModel> doqueryByStoreid(int storeid);

    public int saveElectronic(ElectronicModel mmm) throws DataAccessException;

    public int deleteMessage(@Param("xSpot")String xSpot, @Param("ySpot")String ySpot, @Param("mapId")String mapId);

    public int updateMsgInfo(ElectronicModel mmm);

    public int updateMsgInfo1(ElectronicModel mmm);

    public int updateMsgInfo2(ElectronicModel mmm);

    public int updateMsgInfo3(ElectronicModel mmm);

    public int deleteElectronic(int id);
}
