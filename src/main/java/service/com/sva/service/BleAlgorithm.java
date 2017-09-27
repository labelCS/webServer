/**   
 * @Title: BleAlgorithm.java 
 * @Package com.sva.service 
 * @Description: 蓝牙定位算法接口
 * @author labelCS   
 * @date 2017年9月26日 下午3:43:44 
 * @version V1.0   
 */
package com.sva.service;

import com.sva.model.LocationModel;
import com.sva.web.models.BleUserModel;

/** 
 * @ClassName: BleAlgorithm 
 * @Description: 蓝牙定位算法接口
 * @author labelCS 
 * @date 2017年9月26日 下午3:43:44 
 *  
 */
public interface BleAlgorithm
{
    /** 
     * @Title: getLocation 
     * @Description: 获取指定用户对应的定位位置
     * @param userId
     * @param floorNo
     * @return 
     */
    LocationModel getLocation(BleUserModel model);
}
