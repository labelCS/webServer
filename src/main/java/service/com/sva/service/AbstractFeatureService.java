/**   
 * @Title: AbstractFeatureService.java 
 * @Package com.sva.service 
 * @Description: 特征库定位抽象服务
 * @author labelCS   
 * @date 2017年9月26日 下午5:51:06 
 * @version V1.0   
 */
package com.sva.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.sva.common.ConvertUtil;
import com.sva.dao.PrruSignalDao;
import com.sva.model.PrruFeatureDetailModel;
import com.sva.model.PrruFeatureModel;
import com.sva.model.PrruSignalModel;

/** 
 * @ClassName: AbstractFeatureService 
 * @Description: 特征库定位抽象服务
 * @author labelCS 
 * @date 2017年9月26日 下午5:51:06 
 *  
 */
public abstract class AbstractFeatureService
{
    /** 
     * @Title: getDao 
     * @Description: 获取dao句柄，由子类实现
     * @return 
     */
    protected abstract PrruSignalDao getDao();
    
    /** 
     * @Title: getBlueSignals 
     * @Description: 获取用户当前的蓝牙信号
     * @param userId
     * @param blueCount
     * @return 
     */
    protected List<PrruSignalModel> getBlueSignals(String userId, String blueCount){
        List<PrruSignalModel> signals = getDao().getAverageSignalsByUserId(ConvertUtil.convertMacOrIp(userId), blueCount, "3");
        return signals;
    }
    
    /** 
     * @Title: getGpps 
     * @Description: 获取用户当前蓝牙的id列表
     * @param signals
     * @return 
     */
    protected List<String> getGpps(List<PrruSignalModel> signals){
        List<String> gpps = new ArrayList<String>();
        for(PrruSignalModel item : signals){
            gpps.add(item.getGpp());
        }
        return gpps;
    }
    
    /** 
     * @Title: getRelativeFeaturePoint 
     * @Description: 获取用户信号相关的特征点信息
     * @param gpps
     * @param floorNo
     * @return 
     */
    protected Map<Integer,PrruFeatureModel> getRelativeFeaturePoint(List<String> gpps, String userId, String floorNo){
        // 获取楼层号
        if(floorNo == null || "null".equals(floorNo)){
            floorNo = getDao().queryFloorNoByUseId(ConvertUtil.convertMacOrIp(userId));
        }
        // 取出与用户信号prru有交集的特征库
        List<PrruFeatureModel> prruFeatures = getDao().getRelativeFeature(gpps,floorNo);
        // 如果没有匹配的特征库，返回错误信息
        if(prruFeatures.isEmpty()){
            return new HashMap<Integer,PrruFeatureModel>();
        }
        
        // 格式转换
        return formatFeature(prruFeatures);
    }
    
    /** 
     * @Title: formatFeature 
     * @Description: 将特征库数据转换格式，便于接下来计算 
     * @param datas：源数据
     * @return 
     */
    protected Map<Integer,PrruFeatureModel> formatFeature(List<PrruFeatureModel> datas){
        // 转换中间格式
        Map<Integer,PrruFeatureModel> result = new HashMap<Integer,PrruFeatureModel>();
        // 遍历list，格式化
        for(PrruFeatureModel item:datas){
            // 需要整合的特征值
            PrruFeatureDetailModel detail = new PrruFeatureDetailModel();
            detail.setGpp(item.getGpp());
            detail.setFeatureValue(item.getFeatureValue());
            // 如果结构里已存在该定位点的特征信息，则将特征值加入
            if(result.get(item.getId())!=null){
                result.get(item.getId()).getFeatureValues().add(detail);
            }
            // 否则，初始化该定位点的特征信息
            else{                
                List<PrruFeatureDetailModel> features = new ArrayList<PrruFeatureDetailModel>();
                features.add(detail);
                item.setFeatureValues(features);
                result.put(item.getId(), item);
            }
        }
        return result;
    }
}
