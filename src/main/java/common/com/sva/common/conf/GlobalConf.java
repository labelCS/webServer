package com.sva.common.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.sva.service.AmqpThread;

public abstract class GlobalConf
{
    public static String ip;

    public static String user;

    public static String password;

    /**
     * 对接SVA数据线程池
     */
    private static ArrayList<Thread> threadPool = new ArrayList<Thread>(10);
    
    /**
     * 对接SVA数据对接管理表(java)
     */
    private static Map<String,AmqpThread> subscriptionMap = new HashMap<String,AmqpThread>();

    /**
     * 对接IBMBluemixClient线程池
     */
    private static ArrayList<Thread> bluemixClientThreadPool = new ArrayList<Thread>(
            10);
    
    /** 
     * @Fields rssiMapper : rssi到距离的映射关系
     */ 
    private static Map<Integer, Integer> rssiMapper = new LinkedHashMap<Integer, Integer>();

    /**
     * 加入SVA数据线程池
     * 
     * @param e
     */
    public static synchronized void addThreadPool(Thread e)
    {
        threadPool.add(e);
    }

    /**
     * 移除SVA数据线程
     * 
     * @param e
     */
    public static synchronized void removeThreadPool(Thread e)
    {
        threadPool.remove(e);
    }

    /**
     * 获取SVA数据线程池长度
     * 
     * @return
     */
    public static synchronized int getThreadPoolSize()
    {
        return threadPool.size();
    }

    /**
     * 根据索引获取对于线程
     * 
     * @param i
     * @return
     */
    public static synchronized Thread getThreadPool(int i)
    {
        return threadPool.get(i);
    }

    /**
     * 添加对接IBMBluemixClient线程
     */
    public static synchronized void addBluemixClientThreadPool(Thread e)
    {
        bluemixClientThreadPool.add(e);
    }

    /**
     * 移除IBMBluemixClient线程
     */
    public static synchronized void removeBluemixClientThreadPool(Thread e)
    {
        bluemixClientThreadPool.remove(e);
    }

    /**
     * 获取IBMBluemixClient线程池长度
     */
    public static synchronized int getBluemixClientThreadPoolSize()
    {
        return bluemixClientThreadPool.size();
    }

    /**
     * 根据索引获取IBMBluemixClient线程
     */
    public static synchronized Thread getBluemixClientThreadPool(int i)
    {
        return bluemixClientThreadPool.get(i);
    }
    
    /**   
     * @Title: addService   
     * @Description: 加入SVA数据对接管理表   
     * @param svaId
     * @param s   
     * @return: void      
     * @throws   
     */ 
    public static synchronized void addAmqpThread(String svaId, AmqpThread thread)
    {
    	subscriptionMap.put(svaId, thread);
    }

    /**   
     * @Title: removeService   
     * @Description: 移除SVA数据对接管理表  
     * @param svaId      
     * @return: void      
     * @throws   
     */ 
    public static synchronized void removeAmqpThread(String svaId)
    {
    	AmqpThread at = subscriptionMap.get(svaId);
    	if(at != null){
    		at.stopThread();
    	}
        subscriptionMap.remove(svaId);
    }

    /**   
     * @Title: getServiceMapSize   
     * @Description: 获取SVA数据对接管理表的大小  
     * @return      
     * @return: int      
     * @throws   
     */ 
    public static synchronized int getAmqpMapSize()
    {
        return subscriptionMap.size();
    }

    /**   
     * @Title: getService   
     * @Description: 根据键，获取对应的sva数据对接服务  
     * @param svaId
     * @return：SubscriptionService       
     * @throws   
     */ 
    public static synchronized AmqpThread getAmqpThread(String svaId)
    {
        return subscriptionMap.get(svaId);
    }
    
    /** 
     * @Title: getRssiMapper 
     * @Description: 获取rssi映射关系
     * @return 
     */
    public static synchronized Map<Integer, Integer> getRssiMapper()
    {
        if(rssiMapper.isEmpty()){
            rssiMapper.put(-60, 10);
            rssiMapper.put(-65, 25);
            rssiMapper.put(-70, 35);
            rssiMapper.put(-75, 45);
            rssiMapper.put(-80, 60);
            rssiMapper.put(-85, 80);
            rssiMapper.put(-90, 105);
            rssiMapper.put(-95, 135);
            rssiMapper.put(Integer.MIN_VALUE, 170);
        }
        return rssiMapper;
    }
}
