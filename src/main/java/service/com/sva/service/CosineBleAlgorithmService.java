/**   
 * @Title: CosineBleAlgorithmService.java 
 * @Package com.sva.service 
 * @Description: 余弦相似度算法计算用户位置
 * @author labelCS   
 * @date 2017年9月26日 下午4:50:00 
 * @version V1.0   
 */
package com.sva.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.sva.dao.PrruSignalDao;
import com.sva.model.LocationModel;
import com.sva.model.PrruFeatureDetailModel;
import com.sva.model.PrruFeatureModel;
import com.sva.model.PrruSignalModel;
import com.sva.web.models.BleUserModel;

/** 
 * @ClassName: CosineBleAlgorithmService 
 * @Description: 余弦相似度算法计算用户位置
 * @author labelCS 
 * @date 2017年9月26日 下午4:50:00 
 *  
 */
@Service("cosineBleAlgorithmService")
public class CosineBleAlgorithmService extends AbstractFeatureService implements BleAlgorithm
{
    /** 
     * @Fields MULTIPLE10 : 倍数10
     */ 
    private static final BigDecimal MULTIPLE10 = new BigDecimal(10);
    
    /**
     * @Fields log 输出日志
     */
    private static final Logger LOG = Logger.getLogger(CosineBleAlgorithmService.class);
    
    /** 
     * @Fields prruSignalDao : prruSignal数据库操作DAO
     */ 
    @Autowired
    private PrruSignalDao prruSignalDao;
    
    /** 
     * @Fields blueCount : 确定从数据库取最近多少次的用户蓝牙信号条数作为计算的初始值
     */ 
    @Value("${blue.count}")
    private String blueCount;

    /* (非 Javadoc) 
     * <p>Title: getDao</p> 
     * <p>Description: </p> 
     * @return 
     * @see com.sva.service.AbstractFeatureService#getDao() 
     */
    @Override
    protected PrruSignalDao getDao()
    {
        return prruSignalDao;
    }

    /* (非 Javadoc) 
     * <p>Title: getLocation</p> 
     * <p>Description: </p> 
     * @param userId
     * @return 
     * @see com.sva.service.BleAlgorithm#getLocation(java.lang.String) 
     */
    @Override
    public LocationModel getLocation(BleUserModel model)
    {
        LocationModel result = new LocationModel();
        // 第一步，获取用户信号数据
        List<PrruSignalModel> signals = getBlueSignals(model.getUserId(), blueCount);
        // 如果未找到信号数据，返回错误信息
        if(signals.isEmpty()){
            LOG.error("用户信号数据为空");
            return result;
        }
        
        // 第二步，获取蓝牙id列表
        List<String> gpps = getGpps(signals);
        LOG.debug("用户["+model.getUserId()+"]的信号信息："+gpps.toString());
        
        // 第三步，获取相关的特征点信息
        Map<Integer,PrruFeatureModel> formatedFeatures = getRelativeFeaturePoint(gpps, model.getUserId(), model.getFloorNo());
        // 如果没有匹配的特征库，返回错误信息
        if(formatedFeatures.isEmpty()){
            LOG.error("用户信号没有匹配的特征库");
            return result;
        }
        
        // 第四步，计算最接近的特征点
        PrruFeatureModel closest = calcClosestPoint(signals,formatedFeatures);
        
        // 最后，进行数据格式转换
        LocationModel data = new LocationModel();   
        data.setX(new BigDecimal(closest.getX()).multiply(MULTIPLE10));
        data.setY(new BigDecimal(closest.getY()).multiply(MULTIPLE10));
        data.setZ(new BigDecimal(closest.getFloorNo()));
        data.setUserID(closest.getUserId());
        data.setTimestamp(new BigDecimal(System.currentTimeMillis()));
        data.setTimestampPrru(new BigDecimal(closest.getTimestamp()));
        LOG.debug("用户["+model.getUserId()+"]的定位信息：x-"+closest.getX()+"; y-"+closest.getY());
        return data;
    }
    
    /** 
     * @Title: calcClosestPoint 
     * @Description: 计算出余弦相似度最接近1的特征点
     * @param signals
     * @param datas
     * @return 
     */
    private PrruFeatureModel calcClosestPoint(List<PrruSignalModel> signals, Map<Integer,PrruFeatureModel> datas){
        // 余弦相似度集合
        Map<Integer, BigDecimal> cosineMap = new HashMap<Integer, BigDecimal>();
        
        // 遍历map，计算各个余弦相似度
        Iterator<Entry<Integer, PrruFeatureModel>> it = datas.entrySet().iterator();  
        while(it.hasNext())  
        {
            // 分母X平方和开根
            BigDecimal downX = new BigDecimal(0);
            // 分母Y平方和开根
            BigDecimal downY = new BigDecimal(0);
            // 分子XY之和
            BigDecimal upXY = new BigDecimal(0);
            // 余弦相似度
            BigDecimal cos = new BigDecimal(0);
            
            Entry<Integer, PrruFeatureModel> entity = it.next();
            int id = entity.getKey();
            LOG.debug("特征id-"+id);
            PrruFeatureModel featureModel = entity.getValue();
            // 详细特征值list
            List<PrruFeatureDetailModel> list = featureModel.getFeatureValues();
            // 详细特征值格式转换
            Map<String, BigDecimal> formatedDetail = formatDetail(list);
            
            for(PrruSignalModel p:signals){
                String gpp = p.getGpp();
                BigDecimal rsrp = p.getRsrp();
                if(formatedDetail.containsKey(gpp)){
                    BigDecimal featureRsrp = formatedDetail.get(gpp);
                    upXY = upXY.add(rsrp.multiply(featureRsrp));
                    downX = downX.add(rsrp.pow(2));
                    downY = downY.add(featureRsrp.pow(2));
                    LOG.debug("匹配的特征gpp：gpp-"+gpp+"; rsrp-"+rsrp);
                }
            }
            // 公式：XY之和/(分母X平方和开根*分母Y平方和开根)
            cos = upXY.divide(new BigDecimal(Math.sqrt(downX.doubleValue())).multiply(new BigDecimal(Math.sqrt(downY.doubleValue()))),5,6);
            LOG.debug("余弦相似度："+cos);
            cosineMap.put(id, cos);
        }
        
        // 取余弦相似度最接近1的特征点
        Integer maxKey = getKeyOfMaxValue(cosineMap);
        LOG.debug("最近的点id："+maxKey);
        return datas.get(maxKey);
        
    }
    
    /** 
     * @Title: formatDetail 
     * @Description: 格式化特征详细
     * @param data
     * @return 
     */
    private Map<String, BigDecimal> formatDetail(List<PrruFeatureDetailModel> data){
        Map<String, BigDecimal> result = new HashMap<String, BigDecimal>();
        for(PrruFeatureDetailModel detail : data){
            String gpp = detail.getGpp();
            if(!result.containsKey(gpp)){
                result.put(gpp, detail.getFeatureValue());
            }
        }
        return result;
    }
    
    /** 
     * @Title: getKeyOfMaxValue 
     * @Description: 获取map中value最大值对应的key
     * @param data
     * @return 
     */
    private int getKeyOfMaxValue(Map<Integer, BigDecimal> data){
        // 排序
        List<Map.Entry<Integer, BigDecimal>> entryList = new ArrayList<Map.Entry<Integer, BigDecimal>>(data.entrySet());  
        Collections.sort(entryList, new Comparator<Map.Entry<Integer, BigDecimal>>() {  
            public int compare(Entry<Integer, BigDecimal> entry1,  
                    Entry<Integer, BigDecimal> entry2) {  
                double value1 = entry1.getValue().doubleValue();  
                double value2 = entry2.getValue().doubleValue();  
                double result = value1 - value2;
                if(result>0){
                    return -1;
                }else if(result<0){
                    return 1;
                }else{
                    return 0;
                } 
            }  
        }); 
        
        // 返回最小值
        return entryList.get(0).getKey();
    }

}
