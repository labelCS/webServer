/**   
 * @Title: CollectThread.java 
 * @Package com.sva.service 
 * @Description: 采集特征值线程 
 * @author labelCS   
 * @date 2016年9月27日 下午2:56:58 
 * @version V1.0   
 */
package com.sva.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.lang.Math;

import org.apache.log4j.Logger;

import com.sva.common.conf.GlobalConf;
import com.sva.dao.PrruSignalDao;
import com.sva.model.PrruFeatureDetailModel;
import com.sva.model.PrruFeatureModel;
import com.sva.model.PrruSignalModel;
import com.sva.web.models.PrruFeatureApiModel;

/** 
 * @ClassName: CollectThread 
 * @Description: 采集特征值线程 
 * @author labelCS 
 * @date 2016年9月27日 下午2:56:58 
 *  
 */
public class CollectThread extends Thread {
    /** 
     * @Fields log : 日志处理类 
     */ 
    private static final Logger LOG = Logger.getLogger(CollectThread.class);
    
    /** 
     * @Fields params : 客户端传入的参数bean
     */ 
    private PrruFeatureApiModel params;
    
    /** 
     * @Fields prruSignalDao : prru相关数据表操作句柄
     */ 
    private PrruSignalDao prruSignalDao;
    
    /** 
     * @Fields isStop : 判断线程是否被终止
     */ 
    private boolean isStop = false;
    
    
    /** 
     * @Fields isLineFinished : 线路采集停止
     */ 
    private boolean isLineFinished = false;
    
    /** 
     * @Fields runState : 采集状态
     */ 
    private int runState = 0;
    
    /** 
     * @Fields lineBeginTime : 线路采集开始时间
     */ 
    private long lineBeginTime = 0L;
    
    
    /** 
     * @Fields DEFAULT_VALUE : 计算特征值时的默认值
     */ 
    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(-1400);
    
    /** 
     * @Fields DEFAULT_VALUE : 首条LTE信号超时时间的默认值
     */ 
    private static final long FIRST_TIME_OUT = 20L;
    
    /** 
     * @Fields DEFAULT_VALUE : 首条LTE信号超时时间的默认值
     */ 
    private static final long LINE_TIME_OUT = 120L;
    
    /** 
     * @Fields DEFAULT_VALUE : 首条信号融合时间门限默认值
     */ 
    private static final long MERGE_TIME_DIFF = 1L;
    
    /** 
     * @Fields THRESHOLD : 特征值的阈值
     */ 
    private static final BigDecimal THRESHOLD = new BigDecimal(-4500);
    
    public CollectThread(PrruFeatureApiModel bean, PrruSignalDao dao){
        this.params = bean;
        this.prruSignalDao = dao;
    }
    
    /** 
     * @Title: stopThread 
     * @Description: 中断线程
     */
    public void stopThread(){
        // 中断阻塞线程
        this.interrupt();
        // 中断正在运行的线程
        this.isStop = true;
    }
    
    @Override
    public void run(){
        // 开始之前将任务线程加入线程池
    	isLineFinished = false;
        GlobalConf.addPrruThreadPool(params.getUserId(), this);
        
        // 取得当前时间戳，作为任务起始时间
        long beginTimestamp = System.currentTimeMillis();
        lineBeginTime = beginTimestamp;
        
        try{
        	if(params.getSwitchLTE()!=0){
        		List<PrruSignalModel> prruSignals;
        		int count = 0;
        		do {
        			prruSignals = prruSignalDao.getSignalByUserIdTime(params.getUserId(), beginTimestamp, "1");
        			sleep(2000);
        			count++;
				} while (prruSignals.isEmpty() && count < 10);
        		
        		 // 如果未找到prru信号数据，说明对接AE出现问题
                if(prruSignals.isEmpty()){
                    LOG.error("未收到prru信号数据，请确定sva通道是否正常!");
                    runState = 1;
                    return;
                }
        	}
        	boolean isFinish = true;
        	if(params.getType()==1){
        		isFinish = samplingCertainPoint(beginTimestamp);
        	}
        	else{
        		isFinish = samplingLine(beginTimestamp);
        	}
        	if(!isFinish){
        		waitForStop();
        	}
        }catch(InterruptedException e){
            // 线程中断
            LOG.error("CollectThread:" + e.getMessage());            
        }catch(Exception e){
        	LOG.error("CollectThread:" + e.getMessage());
        }finally{
            // 任务线程最终要从线程池中移除
            GlobalConf.removePrruThreadPool(params.getUserId());
        }
    }
    /** 
     * @Title: waitForStop 
     * @Description: 等待结束
     */
    private void waitForStop() throws InterruptedException{
    	isStop = true;
    	sleep(2*LINE_TIME_OUT*1000);
    	stopThread();
    }
    public void runBak(){
        // 开始之前将任务线程加入线程池
        GlobalConf.addPrruThreadPool(params.getUserId(), this);
        
        // 取得当前时间戳，作为任务起始时间
        long beginTimestamp = System.currentTimeMillis();
        // 计数器
        int counter = 0;
        // 遍历时间戳
        long lastTimestamp = 0L;
        try{
            // 任务挂起一段时间，等待prruSignal信息采集入库
            sleep(2*params.getLength()*1000);
            // 从数据库取出已收集到的prru信号信息
            List<PrruSignalModel> prruSignals = prruSignalDao.getSignalByUserIdTime(params.getUserId(), beginTimestamp, "1");
            // 如果未找到prru信号数据，说明对接AE出现问题
            if(prruSignals.isEmpty()){
                LOG.error("未收到prru信号数据，请确定sva通道是否正常!");
                return;
            }
            // 进行格式转换
            List<Map<String,Object>> formatedList = formatPrruSignals(prruSignals,beginTimestamp);
            // 遍历数据，计算dataCheckValue
            Iterator<Map<String,Object>> it = formatedList.iterator();
            while(it.hasNext())  
            {  
                Map<String, Object> entity = it.next();
                long timestampTmp = (long)entity.get("time");
                @SuppressWarnings("unchecked")
                Map<String, BigDecimal> tempData = (Map<String, BigDecimal>) entity.get("data");
                // 如果信号数据有效，计算dataCheckValue，并写入计算list，计数器+1，更新最后一次时间戳
                if(checkSignalIsValid(tempData)){
                    BigDecimal dataCheckValue = calcDataCheckValue(tempData);
                    entity.put("dataCheckValue", dataCheckValue);
                    counter++;
                }
                // 否则，删除该元素
                else
                {
                    it.remove();
                }
                lastTimestamp = timestampTmp;
            } 
            
            // 如果数据条数不够length，且运算时间未达到上限，继续获取信号数据，直至满足2个条件的任意一个
            while(counter<params.getLength() && 
                    (System.currentTimeMillis()-beginTimestamp)<params.getTimeInSeconds()*1000 && !isStop){
                // 每取一条，等待2s，等待信号数据入库
                sleep(2*1000);
                List<PrruSignalModel> onePrruSignal = 
                        prruSignalDao.getOneSignalByUserIdTime(params.getUserId(), lastTimestamp, "1");
                // 如果未取到数据，说明信号数据未入库，再等待2s
                if(!onePrruSignal.isEmpty()){
                    // 进行格式转换
                    List<Map<String, Object>> formatedMapOne = formatPrruSignals(onePrruSignal,beginTimestamp);
                    Map<String, Object> tempMap = formatedMapOne.get(0);
                    long timestampTmp = (long)tempMap.get("time");
                    @SuppressWarnings("unchecked")
                    Map<String, BigDecimal> tempData = (Map<String, BigDecimal>) tempMap.get("data");
                    if(checkSignalIsValid(tempData)){
                        BigDecimal dataCheckValue = calcDataCheckValue(tempData);
                        tempMap.put("dataCheckValue", dataCheckValue);
                        // 加入计算list
                        formatedList.add(tempMap);
                        counter++;
                    }
                    lastTimestamp = timestampTmp;
                }
            }
            
            // 如果线程未被终止，执行特征值解析入库操作
            if(!isStop){
                //saveFeautreValue(formatedList, beginTimestamp, "1");
            }
        }catch(InterruptedException e){
            // 线程中断
            LOG.info(e);            
        }catch(Exception e){
            LOG.error(e);
        }finally{
            // 任务线程最终要从线程池中移除
            GlobalConf.removePrruThreadPool(params.getUserId());
        }
    }
    public int getRunState(){
    	return runState;
    }
    
    /** 
     * @Title: finishLineSampling 
     * @Description: 线路采集结束
     * @param finishTimestamp：采集结束的时间 
     */
    public int finishLineSampling(long finishTimestamp)
    {
    	if(lineBeginTime<=0L || finishTimestamp<lineBeginTime || isLineFinished){
    		LOG.error("Time传输错误，请检查开始时间和结束时间!");
    		return runState;
    	}
    	isLineFinished = true;
    	// 从数据库取出已收集到的prru信号信息
        List<PrruSignalModel> prruSignals = prruSignalDao.getSignalByUserIdTime(params.getUserId(), lineBeginTime, "1");
        // 从数据库取出已收集到的wifi信号信息
        List<PrruSignalModel> wifiSignals = prruSignalDao.getSignalByUserIdTime(params.getUserId(), lineBeginTime, "2");
        // 从数据库取出已收集到的蓝牙信号信息
        List<PrruSignalModel> blueSignals = prruSignalDao.getSignalByUserIdTime(params.getUserId(), lineBeginTime, "3");
        editGpp(prruSignals);
        List<Map<String,Object>> wifiFormatedList = formatPrruSignals(wifiSignals,lineBeginTime);
        List<Map<String,Object>> blueFormatedList = formatPrruSignals(blueSignals,lineBeginTime);
        List<Map<String,Object>> formatedList = formatPrruSignals(prruSignals,lineBeginTime);
        boolean contiueSignal = Math.abs((finishTimestamp-lineBeginTime)/2-prruSignals.size())<=2;
        // 进行格式转换
        if(params.getSwitchLTE()!=0){
            // 遍历数据，计算dataCheckValue
            Iterator<Map<String,Object>> it = formatedList.iterator();
            Map<String,Object> lastEntity = null;
            while(it.hasNext())  
            {  
                Map<String, Object> entity = it.next();
                @SuppressWarnings("unchecked")
                Map<String, BigDecimal> tempData = (Map<String, BigDecimal>) entity.get("data");
                if(!contiueSignal && !(continueCheck(lastEntity,entity))){
                	runState = 2;
                	return runState;
                }
                lastEntity = entity;
                // 如果信号数据有效，计算dataCheckValue，并写入计算list，计数器+1，更新最后一次时间戳
                if(checkSignalIsValid(tempData)){
                    BigDecimal dataCheckValue = calcDataCheckValue(tempData);
                    entity.put("dataCheckValue", dataCheckValue);
                    // 修正rsrp，获取Data
                    editRsrp(tempData);
                }
                // 否则，删除该元素
                else
                {
                    it.remove();
                }            
            } 
        }
        else{
        	formatedList.clear();
        }
        boolean merged = mergeSignals(formatedList, wifiFormatedList, blueFormatedList);
        if(!merged){
        	runState = 4;
        	return runState;
        }
     // 如果线程未被终止，执行特征值解析入库操作
        if(!isStop){
        	int mixLength = getLineLength(formatedList, wifiFormatedList, blueFormatedList);
        	if(mixLength>1){
        		saveLineFeautreValue(formatedList, wifiFormatedList,blueFormatedList, mixLength);
        	}
        }
		return runState;
    }
    /** 
     * @Title: getAllFeatureMap 
     * @Description: 获取线路采集各种信号的特征值
     * @param datas，wifiDatas, blueDatas 
     * @param mixLength
     */
    private List<Map<String, BigDecimal>> getAllFeatureMap(List<Map<String, Object>> datas, List<Map<String, Object>> wifiDatas, List<Map<String, Object>> blueDatas, int index){
    	List<Map<String, BigDecimal>> allfeatureValueMap = new ArrayList<Map<String, BigDecimal>>(); 
        Map<String, BigDecimal> emptyMap = new HashMap<String, BigDecimal>();
    	if(datas.size()>=index){
    		Map<String, Object> entity = datas.get(index-1);
    		@SuppressWarnings("unchecked")
    		Map<String, BigDecimal> rsrps = (Map<String, BigDecimal>) entity.get("data");
    		allfeatureValueMap.add(rsrps);
    	}
    	else{
    		allfeatureValueMap.add(emptyMap);
    	}
    	if(wifiDatas.size()>=index){
    		Map<String, Object> entity = wifiDatas.get(index-1);
    		@SuppressWarnings("unchecked")
    		Map<String, BigDecimal> rsrps = (Map<String, BigDecimal>) entity.get("data");
    		allfeatureValueMap.add(rsrps);
    	}
    	else{
    		allfeatureValueMap.add(emptyMap);
    	}
    	if(blueDatas.size()>=index){
    		Map<String, Object> entity = blueDatas.get(index-1);
    		@SuppressWarnings("unchecked")
    		Map<String, BigDecimal> rsrps = (Map<String, BigDecimal>) entity.get("data");
    		allfeatureValueMap.add(rsrps);
    	}
    	else{
    		allfeatureValueMap.add(emptyMap);
    	}
    	return allfeatureValueMap;
    }
    
    /** 
     * @Title: getBaseSignals 
     * @Description: 获取线路采集的基准信号
     * @param datas，wifiDatas, blueDatas 
     * @param mixLength
     */
    private List<Map<String, Object>> getBaseSignals(List<Map<String, Object>> datas, List<Map<String, Object>> wifiDatas, List<Map<String, Object>> blueDatas, int mixLength){
    	if(datas.size()==mixLength){
    		return datas;
    	}
    	if(wifiDatas.size()==mixLength){
    		return wifiDatas;
    	}
    	return blueDatas;
    }
    /** 
     * @Title: saveFeautreValue 
     * @Description: 计算特征值并入库
     * @param ：三种信号数据 
     * @param timestamp
     */
    private void saveLineFeautreValue(List<Map<String, Object>> datas, List<Map<String, Object>> wifiDatas, List<Map<String, Object>> blueDatas, int mixLength){
        int i = 1;
        BigDecimal oriX = params.getX();
        BigDecimal oriY = params.getY();
        BigDecimal finalX = params.getFinalx();
        BigDecimal finalY = params.getFinaly();
        List<Map<String, Object>> baseDatas = getBaseSignals(datas, wifiDatas, blueDatas, mixLength);
        while(i<=mixLength){
        	//计算每个时间点的坐标
        	BigDecimal x = oriX;
        	BigDecimal y = oriY;
        	if(i==mixLength){
        		x = finalX;
        		y = finalY;
        	}
        	else if(i>1){
        		double left = (double)(mixLength-i)/(double)(mixLength-1);
            	double right = (double)(i-1)/(double)(mixLength-1);
            	BigDecimal x1 = oriX.multiply(new BigDecimal(left));
            	BigDecimal x2 = finalX.multiply(new BigDecimal(right));
            	x = (x1.add(x2)).setScale(2, BigDecimal.ROUND_HALF_UP);
            	BigDecimal y1 = oriY.multiply(new BigDecimal(left));
            	BigDecimal y2 = finalY.multiply(new BigDecimal(right));
            	y = (y1.add(y2)).setScale(2, BigDecimal.ROUND_HALF_UP);
        	}
        	
        	Map<String, Object> entity = baseDatas.get(i-1);
        	long timeStamp = (long) entity.get("time");
        	// 数据入库
            PrruFeatureModel featureModel = new PrruFeatureModel();
            featureModel.setFeatureRadius(new BigDecimal(0));
            featureModel.setCheckValue(new BigDecimal(0));
            featureModel.setX(x.toString());
            featureModel.setY(y.toString());
            featureModel.setFloorNo(params.getFloorNo().toString());
            featureModel.setTimestamp(timeStamp);
            featureModel.setUserId(params.getUserId());
            prruSignalDao.savePrruFeature(featureModel);
            int id = featureModel.getId();
            List<Map<String, BigDecimal>> allfeaturemap = getAllFeatureMap(datas, wifiDatas, blueDatas, i);
            // 生成特征值bean，然后入库
            List<PrruFeatureDetailModel> detailList = generatePrruFeatureDetail(allfeaturemap,id);
            prruSignalDao.savePrruFeatureDetail(detailList);
            i++;
        }      
    }
    /** 
     * @Title: getLineLength 
     * @Description: 线路采集信号条数获取
     * @param ：三种信号数据
     * @return 
     */
    private int getLineLength(List<Map<String, Object>> lteDatas, List<Map<String, Object>> wifiDatas, List<Map<String, Object>> bluedatas){
    	if(params.getSwitchLTE()!=0){
    		return lteDatas.size();
    	}
    	if(params.getSwitchWIFI()!=0){
    		return wifiDatas.size();
    	}
    	return bluedatas.size();
    }
    
    private boolean samplingLine(long beginTimestamp) throws InterruptedException{
    	sleep(LINE_TIME_OUT*1000);
    	if(!isLineFinished){
    		runState = 6;
    	}
    	return false;
    }
    /** 
     * @Title: continueCheck 
     * @Description: 线路采集连续性判断
     * @param lastEntity，curEntity ：前后两次数据
     * @return 
     */
    private boolean continueCheck(Map<String, Object> lastEntity, Map<String, Object> curEntity){
    	if(lastEntity == null){
    		return true;
    	}
    	long curTimestampTmp = (long)curEntity.get("time");
    	long lastTimestampTmp = (long)lastEntity.get("time");
    	//小于3S直接返回连续
    	if(curTimestampTmp-lastTimestampTmp<=3000){
    		return true;
    	}
    	//大于4s直接返回不连续
    	if(curTimestampTmp-lastTimestampTmp>4000){
    		return false;
    	}
    	//小于等于4S查看是否是发生了小区切换
    	@SuppressWarnings("unchecked")
		Map<String, BigDecimal> curData = (Map<String, BigDecimal>) curEntity.get("data");
    	@SuppressWarnings("unchecked")
		Map<String, BigDecimal> lastData = (Map<String, BigDecimal>) lastEntity.get("data");
    	Iterator<Entry<String, BigDecimal>> it = lastData.entrySet().iterator();  
        while(it.hasNext()){
            Entry<String,BigDecimal> entity = it.next();
            String gpp = entity.getKey();
            if(curData.containsKey(gpp)){
            	return false;
            }
        }
    	return true;
    }
    /** 
     * @Title: samplingCertainPoint 
     * @Description: 定点采集流程
     * @param beginTimestamp 开始时间
     * @return 
     */
    private boolean samplingCertainPoint(long beginTimestamp) throws InterruptedException{
    	// 计数器
        int counter = 0;
    	// 遍历时间戳
        long lastTimestamp = 0L;
    	// 任务挂起一段时间，等待prruSignal信息采集入库
        long sleepTime = 2*params.getLength()*1000;
        sleep(sleepTime);
        // 从数据库取出已收集到的prru信号信息
        List<PrruSignalModel> prruSignals = prruSignalDao.getSignalByUserIdTime(params.getUserId(), beginTimestamp, "1");
        editGpp(prruSignals);
        List<Map<String,Object>> formatedList = formatPrruSignals(prruSignals,beginTimestamp);
        // 进行格式转换
        if(params.getSwitchLTE()!=0){
            // 遍历数据，计算dataCheckValue
            Iterator<Map<String,Object>> it = formatedList.iterator();
            while(it.hasNext())  
            {  
                Map<String, Object> entity = it.next();
                long timestampTmp = (long)entity.get("time");
                @SuppressWarnings("unchecked")
                Map<String, BigDecimal> tempData = (Map<String, BigDecimal>) entity.get("data");
                // 如果信号数据有效，计算dataCheckValue，并写入计算list，计数器+1，更新最后一次时间戳
                if(checkSignalIsValid(tempData)){
                    BigDecimal dataCheckValue = calcDataCheckValue(tempData);
                    entity.put("dataCheckValue", dataCheckValue);
                    // 修正rsrp，获取Data
                    editRsrp(tempData);
                    counter++;
                }
                // 否则，删除该元素
                else
                {
                    it.remove();
                }
                lastTimestamp = timestampTmp;
            } 
            
            // 如果数据条数不够length，且运算时间未达到上限，继续获取信号数据，直至满足2个条件的任意一个
            while(counter<params.getLength() && 
                    (System.currentTimeMillis()-beginTimestamp)<params.getTimeInSeconds()*1000 && !isStop){
                // 每取一条，等待2s，等待信号数据入库
                sleep(2*1000);
                List<PrruSignalModel> onePrruSignal = 
                        prruSignalDao.getOneSignalByUserIdTime(params.getUserId(), lastTimestamp, "1");
                editGpp(onePrruSignal);
                // 如果未取到数据，说明信号数据未入库，再等待2s
                if(!onePrruSignal.isEmpty()){
                    // 进行格式转换
                    List<Map<String, Object>> formatedMapOne = formatPrruSignals(onePrruSignal,beginTimestamp);
                    Map<String, Object> tempMap = formatedMapOne.get(0);
                    long timestampTmp = (long)tempMap.get("time");
                    @SuppressWarnings("unchecked")
                    Map<String, BigDecimal> tempData = (Map<String, BigDecimal>) tempMap.get("data");
                    if(checkSignalIsValid(tempData)){
                        BigDecimal dataCheckValue = calcDataCheckValue(tempData);
                        tempMap.put("dataCheckValue", dataCheckValue);
                        // 修正rsrp，获取Data
                        editRsrp(tempData);
                        // 加入计算list
                        formatedList.add(tempMap);
                        counter++;
                    }
                    lastTimestamp = timestampTmp;
                }
            }
        }
        // 从数据库取出已收集到的wifi信号信息
        List<PrruSignalModel> wifiSignals = prruSignalDao.getSignalByUserIdTime(params.getUserId(), beginTimestamp, "2");
        // 从数据库取出已收集到的蓝牙信号信息
        List<PrruSignalModel> blueSignals = prruSignalDao.getSignalByUserIdTime(params.getUserId(), beginTimestamp, "3");
        List<Map<String,Object>> wifiFormatedList = formatPrruSignals(wifiSignals,beginTimestamp);
        List<Map<String,Object>> blueFormatedList = formatPrruSignals(blueSignals,beginTimestamp);
        if(params.getSwitchLTE()==0){
        	formatedList.clear();
        	if(wifiFormatedList.size()>params.getLength()){
        		wifiFormatedList = wifiFormatedList.subList(0, params.getLength());
        	}
        	if(params.getSwitchWIFI()==0 && blueFormatedList.size()>params.getLength()){
        		blueFormatedList = blueFormatedList.subList(0, params.getLength());
        	}
        }
        if(formatedList.size() > params.getLength()){
        	formatedList = formatedList.subList(0, params.getLength());
        }
        boolean merged = mergeSignals(formatedList, wifiFormatedList, blueFormatedList);
        //融合失败
        if(!merged){
        	runState = 4;
            return false;
        }
        // 如果线程未被终止，执行特征值解析入库操作
        if(!isStop){
        	saveFeautreValue(formatedList, wifiFormatedList, blueFormatedList, beginTimestamp);     	
        }
        return true;
    }
    /** 
     * @Title: mergeSignals 
     * @Description: 融合三种信号
     * @param ：LTE信号，WIFI信号，蓝牙信号
     * @return 
     */
    private boolean mergeSignals(List<Map<String, Object>> lteDatas, List<Map<String, Object>> wifiDatas, List<Map<String, Object>> bluedatas){
    	boolean wifiMerged = true;
    	if(params.getSwitchLTE()!=0){
    		wifiMerged = mergeTwoSignals(lteDatas, wifiDatas);
    		mergeTwoSignals(lteDatas, bluedatas);
    	}
    	else {
    		if(params.getSwitchWIFI()!=0 && !wifiDatas.isEmpty()){
    			mergeTwoSignals(wifiDatas, bluedatas);
    		}
    	}
    	return wifiMerged;
    	
    }
    
    /** 
     * @Title: mergeTwoSignals 
     * @Description: 融合两种信号
     * @param datas: 基准信号
     * @param beMergedDatas：待融合信号
     * @return 
     */
    private boolean mergeTwoSignals(List<Map<String, Object>> baseDatas, List<Map<String, Object>> beMergedDatas){
    	if(!baseDatas.isEmpty() && beMergedDatas.isEmpty()){
    		return true;
    	}
    	else {
    		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    		Iterator<Map<String,Object>> itBase = baseDatas.iterator();
    		BigDecimal dataCheckValue = new BigDecimal(0);
    		// 遍历原始数据，套用公式
            while(itBase.hasNext()){
            	Map<String, Object> baseEntity = itBase.next();
                long timestampBase = (long)baseEntity.get("time");
                long MinTimeDiff = MERGE_TIME_DIFF*1000;
                Map<String, Object> foundEntity = null;
                if(!baseEntity.containsKey("dataCheckValue")){
                	baseEntity.put("dataCheckValue", dataCheckValue);
                }
                Iterator<Map<String,Object>> itMerge = beMergedDatas.iterator();
                while(itMerge.hasNext()){
                	Map<String, Object> mergeEntity = itMerge.next();
                	long timestampMerge = (long)mergeEntity.get("time");
                	if(Math.abs(timestampMerge-timestampBase)<=MinTimeDiff){
                		foundEntity = mergeEntity;
                		MinTimeDiff = (timestampMerge-timestampBase);
                	}
                	else if(foundEntity!=null){
                		foundEntity.put("dataCheckValue", dataCheckValue);
                		result.add(foundEntity);
                		break;
                	}
                }
            }
            beMergedDatas.clear();
            beMergedDatas.addAll(result);
            return result.size()==baseDatas.size();
    	}
    	
    }
    /** 
     * @Title: formatPrruSignals 
     * @Description: 格式转换，便于接下来计算
     * @param datas 源数据格式
     * @return 
     */
    private List<Map<String, Object>> formatPrruSignals(List<PrruSignalModel> datas,long beginTimestamp){
        // 结果
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        // 过度数据格式
        Map<Long,Map<String,BigDecimal>> tempFormat = new LinkedHashMap<Long,Map<String,BigDecimal>>();
        // 遍历数据
        for(PrruSignalModel p: datas){
            LOG.fatal("pruu data:"+p.toString()+",beginTimestamp:"+beginTimestamp);
            // 柜框槽号
            String gpp = p.getGpp();
            // 信号值
            BigDecimal rsrp = p.getRsrp();
            // 时间戳
            long timestamp = p.getTimestamp();
            // 如果结果map里已有该时间戳的数据，则将信号数据添加仅map里
            if(tempFormat.containsKey(timestamp)){
                Map<String,BigDecimal> temp = tempFormat.get(timestamp);
                temp.put(gpp, rsrp);
            }
            // 否则，将时间戳作为key，信号map作为value，加入结果map里
            else
            {
                Map<String,BigDecimal> temp = new HashMap<String,BigDecimal>();
                temp.put(gpp, rsrp);
                tempFormat.put(timestamp, temp);
            }
        }
        
        // 遍历map，转换为list
        Iterator<Entry<Long, Map<String, BigDecimal>>> it = tempFormat.entrySet().iterator();  
        while(it.hasNext())  
        {
            Map.Entry<Long, Map<String, BigDecimal>> entity = (Entry<Long, Map<String, BigDecimal>>) it.next();
            long timestampLoop = entity.getKey();
            Map<String, BigDecimal> dataLoop = entity.getValue();
            Map<String, Object> tempLoop = new HashMap<String, Object>();
            tempLoop.put("time", timestampLoop);
            tempLoop.put("data", dataLoop);
            result.add(tempLoop);
        }
        
        return result;
    }
    
    /** 
     * @Title: calcDataCheckValue 
     * @Description: 计算一条信号数据的dataCheckValue 
     * @param datas 计算的对象
     * @return 
     */
    private BigDecimal calcDataCheckValue(Map<String, BigDecimal> datas){
        // 结果
        BigDecimal result = new BigDecimal(0);
        // 计数器
        int count = 0;
        // 对于datas的每一个数据
        Iterator<Entry<String, BigDecimal>> it = datas.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<String, BigDecimal> entity = (Entry<String, BigDecimal>) it.next();
            BigDecimal item = entity.getValue();
            // 如果大于阈值，求和，计数器加1
            if(item.compareTo(THRESHOLD) > 0){
                result = result.add(item);
                count++;
            }
        }
        // 如果有效值的个数不为0
        if(count > 0){
            result = result.divide(new BigDecimal(count),2);
        }
        
        return result;
    }
    
    /** 
     * @Title: checkSignalIsValid 
     * @Description: 判断信号数据是否有效，如果全部<-1300，则无效 
     * @param datas 判断对象
     * @return 
     */
    private boolean checkSignalIsValid(Map<String, BigDecimal> datas){
        // 结果
        boolean result = false;
        
        // 对于datas的每一个数据
        Iterator<Entry<String, BigDecimal>> it = datas.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<String, BigDecimal> entity = (Entry<String, BigDecimal>) it.next();
            BigDecimal item = entity.getValue();
            // 如果大于默认值，该数据有效，推出判断
            if(item.compareTo(THRESHOLD) > 0){
                result = true;
                break;
            }
        }
        
        return result;
    }
    
    /** 
     * @Title: saveFeautreValue 
     * @Description: 计算特征值并入库
     * @param datas 
     * @param timestamp
     */
    private void saveFeautreValue(List<Map<String, Object>> datas,List<Map<String, Object>> wifiDatas,List<Map<String, Object>> blueDatas, long timestamp){
        
        // 计算特征值
        Map<String, BigDecimal> featureValueMap = calcFeautreValue(datas);
        // 计算特征值
        Map<String, BigDecimal> wififeatureValueMap = calcFeautreValue(wifiDatas);
        // 计算特征值
        Map<String, BigDecimal> bluefeatureValueMap = calcFeautreValue(blueDatas);
        // 计算特征半径
        BigDecimal featureRadius = new BigDecimal(0); 
        if(!datas.isEmpty()){
        	featureRadius = calcFeatureRadius(datas,featureValueMap);
        }
        else if(!wifiDatas.isEmpty()){
        	featureRadius = calcFeatureRadius(wifiDatas,wififeatureValueMap);
        }
        else if(!blueDatas.isEmpty()){
        	featureRadius = calcFeatureRadius(blueDatas,bluefeatureValueMap);
        }
        List<Map<String, BigDecimal>> allfeatureValueMap = new ArrayList<Map<String, BigDecimal>>(); 
        allfeatureValueMap.add(featureValueMap);
        allfeatureValueMap.add(wififeatureValueMap);
        allfeatureValueMap.add(bluefeatureValueMap);
        // 数据入库
        PrruFeatureModel featureModel = new PrruFeatureModel();
        featureModel.setCheckValue(new BigDecimal(0));
        featureModel.setFeatureRadius(featureRadius);
        featureModel.setX(params.getX().toString());
        featureModel.setY(params.getY().toString());
        featureModel.setFloorNo(params.getFloorNo().toString());
        featureModel.setTimestamp(timestamp);
        featureModel.setUserId(params.getUserId());
        prruSignalDao.savePrruFeature(featureModel);
        int id = featureModel.getId();
        // 生成特征值bean，然后入库
        List<PrruFeatureDetailModel> detailList = generatePrruFeatureDetail(allfeatureValueMap,id);
        prruSignalDao.savePrruFeatureDetail(detailList);  
    }
    
    /** 
     * @Title: saveFeautreValue 
     * @Description: 计算特征值并入库
     * @param datas 
     * @param timestamp
     */
    private void saveFeautreValueBak(List<Map<String, Object>> datas, long timestamp){
        // 如果数据list为空，说明所有数据均无效，需要重测
        if(datas.isEmpty()){
            LOG.error("未找到有效的prru信号数据");
            return;
        }
        
        // 计算特征值
        Map<String, BigDecimal> featureValueMap = calcFeautreValue(datas);
        // 计算校验值
        BigDecimal checkValue = calcCheckValue(featureValueMap);
        // 计算特征半径
        BigDecimal featureRadius = calcFeatureRadiusBak(datas,featureValueMap,checkValue);
        // 数据入库
        PrruFeatureModel featureModel = new PrruFeatureModel();
        featureModel.setCheckValue(checkValue);
        featureModel.setFeatureRadius(featureRadius);
        featureModel.setX(params.getX().toString());
        featureModel.setY(params.getY().toString());
        featureModel.setFloorNo(params.getFloorNo().toString());
        featureModel.setTimestamp(timestamp);
        featureModel.setUserId(params.getUserId());
        int id = prruSignalDao.savePrruFeature(featureModel);
        // 生成特征值bean，然后入库
        //List<PrruFeatureDetailModel> detailList = generatePrruFeatureDetail(featureValueMap,id);
        //prruSignalDao.savePrruFeatureDetail(detailList);
        
    }
    
    /** 
     * @Title: generatePrruFeatureDetail 
     * @Description: 生成用于插入数据库的bean 
     * @param featureValues
     * @param featureId
     * @return 
     */
    private List<PrruFeatureDetailModel> generatePrruFeatureDetail(List<Map<String, BigDecimal>> allfeatureValues, 
            int featureId){
        // 结果
        List<PrruFeatureDetailModel> list = new ArrayList<PrruFeatureDetailModel>();
        int i = 1;
        for(Map<String,BigDecimal> featureValues : allfeatureValues){
        	// 遍历特征值，生成bean
        	String type = Integer.toString(i);
        	Iterator<Entry<String, BigDecimal>> it = featureValues.entrySet().iterator();  
        	while(it.hasNext()){
        		Entry<String,BigDecimal> entity = it.next();
        		String gpp = entity.getKey();
        		BigDecimal featureValue = entity.getValue();
        		PrruFeatureDetailModel temp = new PrruFeatureDetailModel();
        		temp.setFeatureId(featureId);
        		temp.setFeatureValue(featureValue);
        		temp.setGpp(gpp);
        		temp.setType(type);
        		list.add(temp);
        	}
        	i++;
        }
        
        return list;
    }
    
    /** 
     * @Title: calcFeautreValue 
     * @Description: 计算特征值
     * @param datas 
     */
    private Map<String, BigDecimal> calcFeautreValue(List<Map<String, Object>> datas){
        // 存放各个prru对应的特征值
        Map<String, BigDecimal> featureValueMap = new HashMap<String, BigDecimal>();
        // 存放各个prru对应的有效rsrp的个数
        Map<String, Integer> featureCount = new HashMap<String, Integer>();
        // 遍历数据，统计特征值
        for(Map<String,Object> item : datas){
            @SuppressWarnings("unchecked")
            Map<String, BigDecimal> rsrps = (Map<String, BigDecimal>) item.get("data");
            Iterator<Entry<String, BigDecimal>> it = rsrps.entrySet().iterator();  
            while(it.hasNext()){
                Entry<String,BigDecimal> entity = it.next();
                String gpp = entity.getKey();
                BigDecimal rsrp = entity.getValue();
                // 已有该gpp的数据则执行相加
                if(featureValueMap.get(gpp)!=null ){
                    featureValueMap.put(gpp, featureValueMap.get(gpp).add(rsrp));
                    featureCount.put(gpp, featureCount.get(gpp)+1);
                }
                // 否则初始化该gpp的数据
                else{
                    featureValueMap.put(gpp, rsrp);
                    featureCount.put(gpp, 1);
                }
            }
        }
        
        // 取平均，生成特征值
        Iterator<Entry<String, BigDecimal>> itFeature = featureValueMap.entrySet().iterator();  
        while(itFeature.hasNext()){
            Entry<String,BigDecimal> entity = itFeature.next();
            String gpp = entity.getKey();
            BigDecimal rsrp = entity.getValue();
            int count = featureCount.get(gpp);
            BigDecimal featureValue = rsrp.divide(new BigDecimal(count),2);
            featureValueMap.put(gpp,featureValue);
        }
        
        return featureValueMap;
    }
    
    /** 
     * @Title: calcFeautreValue 
     * @Description: 计算特征值
     * @param datas 
     */
    private Map<String, BigDecimal> calcFeautreValueBak(List<Map<String, Object>> datas){
        // 存放各个prru对应的特征值
        Map<String, BigDecimal> featureValueMap = new HashMap<String, BigDecimal>();
        // 存放各个prru对应的有效rsrp的个数
        Map<String, Integer> featureCount = new HashMap<String, Integer>();
        // 遍历数据，统计特征值
        for(Map<String,Object> item : datas){
            @SuppressWarnings("unchecked")
            Map<String, BigDecimal> rsrps = (Map<String, BigDecimal>) item.get("data");
            Iterator<Entry<String, BigDecimal>> it = rsrps.entrySet().iterator();  
            while(it.hasNext()){
                Entry<String,BigDecimal> entity = it.next();
                String gpp = entity.getKey();
                BigDecimal rsrp = entity.getValue();
                // 略去无效的数据
                if(rsrp.compareTo(DEFAULT_VALUE)<=0){
                    continue;
                }
                // 已有该gpp的数据则执行相加
                if(featureValueMap.get(gpp)!=null ){
                    featureValueMap.put(gpp, featureValueMap.get(gpp).add(rsrp));
                    featureCount.put(gpp, featureCount.get(gpp)+1);
                }
                // 否则初始化该gpp的数据
                else{
                    featureValueMap.put(gpp, rsrp);
                    featureCount.put(gpp, 1);
                }
            }
        }
        
        // 取平均，生成特征值
        Iterator<Entry<String, BigDecimal>> itFeature = featureValueMap.entrySet().iterator();  
        while(itFeature.hasNext()){
            Entry<String,BigDecimal> entity = itFeature.next();
            String gpp = entity.getKey();
            BigDecimal rsrp = entity.getValue();
            int count = featureCount.get(gpp);
            BigDecimal featureValue = rsrp.divide(new BigDecimal(count),2);
            featureValueMap.put(gpp,featureValue);
        }
        
        return featureValueMap;
    }
    
    /** 
     * @Title: calcCheckValue 
     * @Description: 计算checkValue
     * @param featureValues：特征值
     * @return 
     */
    private BigDecimal calcCheckValue(Map<String, BigDecimal> featureValues){
     // 取平均，生成特征值，并统计checkValue
        BigDecimal checkValue = new BigDecimal(0);
        int checkCount = 0;
        Iterator<Entry<String, BigDecimal>> it = featureValues.entrySet().iterator();  
        while(it.hasNext()){
            Entry<String,BigDecimal> entity = it.next();
            BigDecimal featureValue = entity.getValue();
            // 判断对于计算checkValue是否有效
            if(featureValue.compareTo(THRESHOLD)>0){
                checkValue = checkValue.add(featureValue);
                checkCount++;
            }
        }
        
        // 计算checkValue
        if(checkCount != 0){
            checkValue = checkValue.divide(new BigDecimal(checkCount),2);
        }
        
        return checkValue;
    }
    
    /** 
     * @Title: calcFeatureRadius 
     * @Description: 计算特征半径
     * @param datas rsrp：原始数据
     * @param featureValues：特征值
     * @param checkValue：校验值
     * @return 
     */
    private BigDecimal calcFeatureRadius(List<Map<String, Object>> datas, 
            Map<String, BigDecimal> featureValues){
        // rsrp距离
        BigDecimal rsrpDistance = new BigDecimal(0);
        // 遍历原始数据，套用公式
        for(Map<String, Object> item : datas){
            @SuppressWarnings("unchecked")
            Map<String, BigDecimal> rsrps = (Map<String, BigDecimal>) item.get("data");
            Iterator<Entry<String, BigDecimal>> it = rsrps.entrySet().iterator();  
            while(it.hasNext()){
                Entry<String,BigDecimal> entity = it.next();
                String gpp = entity.getKey();
                BigDecimal rsrp = entity.getValue();
                BigDecimal featureValue = featureValues.get(gpp);
                // (data-featureValue)^2
                BigDecimal temp = (rsrp.subtract(featureValue)).pow(2);
                rsrpDistance = rsrpDistance.add(temp);
            }
        }
        // 返回特征半径
        if(datas.size()>0){
        	return rsrpDistance.divide(new BigDecimal(datas.size()),2);
        }
        return rsrpDistance;
        
    }
    
    /** 
     * @Title: calcFeatureRadius 
     * @Description: 计算特征半径
     * @param datas rsrp：原始数据
     * @param featureValues：特征值
     * @param checkValue：校验值
     * @return 
     */
    private BigDecimal calcFeatureRadiusBak(List<Map<String, Object>> datas, 
            Map<String, BigDecimal> featureValues, 
            BigDecimal checkValue){
        // rsrp距离
        BigDecimal rsrpDistance = new BigDecimal(0);
        // 遍历原始数据，套用公式
        for(Map<String, Object> item : datas){
            @SuppressWarnings("unchecked")
            Map<String, BigDecimal> rsrps = (Map<String, BigDecimal>) item.get("data");
            BigDecimal dataCheckValue = (BigDecimal) item.get("dataCheckValue");
            Iterator<Entry<String, BigDecimal>> it = rsrps.entrySet().iterator();  
            while(it.hasNext()){
                Entry<String,BigDecimal> entity = it.next();
                String gpp = entity.getKey();
                BigDecimal rsrp = entity.getValue();
                BigDecimal featureValue = featureValues.get(gpp);
                // 略去无效的数据
                if(rsrp.compareTo(DEFAULT_VALUE)<=0){
                    continue;
                }
                BigDecimal temp = rsrp.subtract(dataCheckValue).subtract(featureValue.subtract(checkValue)).pow(2);
                rsrpDistance = rsrpDistance.add(temp);
            }
        }
        // 返回特征半径
        return rsrpDistance.divide(new BigDecimal(datas.size()),2);
        
    }
    
    /** 
     * @Title: editRsrp 
     * @Description: 对rsrp进行修正 
     * @param datas 
     */
    private void editRsrp(Map<String, BigDecimal> datas){
        // 如果是空数据，直接返回
        if(datas.isEmpty()){
            return;
        }
        // 单条rsrp信息的总和
        BigDecimal total = new BigDecimal(0);
        // 平均值
        BigDecimal average = new BigDecimal(0);
        // 循环，求所有rsrp总和
        Iterator<Entry<String, BigDecimal>> it = datas.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<String, BigDecimal> entity = (Entry<String, BigDecimal>) it.next();
            // 相加
            total = total.add(entity.getValue());
        }
        // 取平均
        average = total.divide(new BigDecimal(datas.size()),2);
        
        // 循环，修正rsrp，所有的rsrp减去平均值
        it = datas.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<String, BigDecimal> entity = (Entry<String, BigDecimal>) it.next();
            entity.setValue(entity.getValue().subtract(average));
        }
    }
    
    /** 
     * @Title: editGpp 
     * @Description: gpp添加enbId标识 
     * @param data 
     */
    private void editGpp(List<PrruSignalModel> data){
        for(PrruSignalModel p:data){
            p.setGpp(p.getEnbid() + "__" + p.getGpp());
        }
    }
}
