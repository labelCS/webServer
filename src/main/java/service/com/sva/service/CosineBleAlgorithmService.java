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
    
    /** 
     * @Fields Q : 预测方差
     */ 
    @Value("${blue.Q}")
    private String strQ;
    
    /** 
     * @Fields R : 测量方差
     */ 
    @Value("${blue.R}")
    private String strR;

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
        //List<PrruSignalModel> signals = getBlueSignalsList(model.getUserId(), blueCount);
        List<PrruSignalModel> signals = getBlueSignals(model.getUserId(), blueCount);
        // 如果未找到信号数据，返回错误信息
        if(signals.isEmpty()){
            LOG.error("用户信号数据为空");
            return result;
        }
        
        // 第二步，对用户信号进行滤波（卡尔曼或均值）
        //kalmanFilter(signals);
        
        // 第三步，获取蓝牙id列表
        List<String> gpps = getGpps(signals);
        LOG.debug("用户["+model.getUserId()+"]的信号信息："+gpps.toString());
        
        // 第四步，获取相关的特征点信息
        Map<Integer,PrruFeatureModel> formatedFeatures = getRelativeFeaturePoint(gpps, model.getUserId(), model.getFloorNo());
        // 如果没有匹配的特征库，返回错误信息
        if(formatedFeatures.isEmpty()){
            LOG.error("用户信号没有匹配的特征库");
            return result;
        }
        
        // 第五步，计算最接近的特征点
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

    /** 
     * @Title: kalmanFilter 
     * @Description: 对原始信号 进行卡尔曼滤波
     * @param data 
     */
    private void kalmanFilter(List<PrruSignalModel> data){
        // 按gpp将用户信号分组
        Map<String, List<Double>> temp = new HashMap<String, List<Double>>();
        for(PrruSignalModel p:data){
            String gpp = p.getGpp();
            if(temp.containsKey(gpp)){
                List<Double> tempList = temp.get(gpp);
                tempList.add(p.getRsrp().doubleValue());
            }else{
                List<Double> tempList = new ArrayList<Double>();
                tempList.add(p.getRsrp().doubleValue());
                temp.put(gpp, tempList);
            }
        }
        
        data.clear();
        // 对于每一组信号，通过卡尔曼滤波算法，获取该gpp的最终值
        Iterator<Entry<String, List<Double>>> it = temp.entrySet().iterator();  
        while(it.hasNext())  
        {
            Entry<String, List<Double>> entity = it.next();
            String tempGpp = entity.getKey();
            List<Double> tempSignal = entity.getValue();
            LOG.debug("滤波前的原始信号-"+tempSignal);
            Double finalVal = kalmanFilterCalc(tempSignal);
            LOG.debug("滤波后的信号-"+finalVal);
            PrruSignalModel m = new PrruSignalModel();
            m.setEnbid(tempGpp);
            m.setGpp(tempGpp);
            m.setRsrp(new BigDecimal(finalVal));
            data.add(m);
        }
    }
    
    /** 
     * @Title: kalmanFilterCalc 
     * @Description: 卡尔曼滤波算法实现
     * @param arrayList
     * @return 
     */
    private Double kalmanFilterCalc(List<Double> arrayList){
        double Q = Double.valueOf(strQ);
        double R = Double.valueOf(strR);
        int length = arrayList.size();
        double[] z = new double[length];
        double[] xhat = new double[length];
        double[] xhatminus = new double[length];
        double[] P = new double[length];
        double[] Pminus = new double[length];
        double[] K = new double[length];
        xhat[0] = 0;
        P[0] = 1.0;
        
        for (int i = 0; i<length; i++) {
            z[i] = (double)arrayList.get(i);
        }
        
        if(arrayList.size()<2){
            return arrayList.get(0);
        }
        for(int k = 1; k < length; k++) {
            // X(k|k-1) = AX(k-1|k-1) + BU(k) + W(k),A=1,BU(k) = 0  
            xhatminus[k] = xhat[k-1];

            // P(k|k-1) = AP(k-1|k-1)A' + Q(k) ,A=1
            Pminus[k] = P[k-1]+Q;
          
            // Kg(k)=P(k|k-1)H'/[HP(k|k-1)H' + R],H=1
            K[k] = Pminus[k]/( Pminus[k]+R);
            
            // X(k|k) = X(k|k-1) + Kg(k)[Z(k) - HX(k|k-1)], H=1
            xhat[k] = xhatminus[k]+K[k]*(z[k]-xhatminus[k]);
            
            //P(k|k) = (1 - Kg(k)H)P(k|k-1), H=1
            P[k] = (1-K[k])*Pminus[k] ;
        }
        return xhat[length-1];
    }
}
