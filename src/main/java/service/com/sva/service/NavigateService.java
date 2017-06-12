/**   
 * @Title: NavigateService.java 
 * @Package com.sva.service 
 * @Description: HTML5移动端页面导航路径生成逻辑
 * @author labelCS   
 * @date 2017年2月7日 上午10:00:29 
 * @version V1.0   
 */
package com.sva.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.sva.common.conf.GlobalConf;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * @ClassName: NavigateService 
 * @Description: HTML5移动端页面导航路径生成逻辑
 * @author labelCS 
 * @date 2017年2月7日 上午10:00:29 
 *  
 */
@Service
public class NavigateService
{
    /**
     * @Fields log ： 输出日志
     */
    private static final Logger LOG = Logger.getLogger(NavigateService.class);
    
    /** 
     * @Fields INF : 代表无穷大值
     */ 
    private static final double INF = Integer.MAX_VALUE;
    
    /** 
     * @Title: calcNavigatePath 
     * @Description: 根据路径规划数据，计算导航信息
     * @param pointArray 点数组
     * @param dataArray 线段数组
     * @param fileName 路径规划文件名，此处用作全局变量的键值
     */
    public void calcNavigatePath(JSONArray pointArray, JSONArray dataArray, String fileName){
        LOG.info("start calculate navigate path:"+fileName);
        // 距离矩阵表，保存各点之间的最短距离
        Map<Integer,Map<Integer,Double>> distanceMap = new HashMap<Integer,Map<Integer,Double>>();
        // 路径矩阵表，保存各点之间最短距离对应的路径
        Map<Integer,Map<Integer,List<Integer>>> routeMap = new HashMap<Integer,Map<Integer,List<Integer>>>();
        // 矩阵初始化
        for(int i = 0; i<dataArray.size(); i++){
            JSONArray lineArray = dataArray.getJSONArray(i);
            int fromId = lineArray.getInt(0);
            int toId = lineArray.getInt(1);
            // 计算2点间的距离
            double distance = calcDistanceBetweenPoints(fromId, toId, pointArray);
            // 从from到to，初始化距离矩阵和路径矩阵
            if(distanceMap.get(fromId) != null){
                Map<Integer, Double> tmp1 = distanceMap.get(fromId);
                tmp1.put(toId, distance);
                
                Map<Integer, List<Integer>> tmp2 = routeMap.get(fromId);
                List<Integer> temp = new ArrayList<Integer>();
                temp.add(toId);
                tmp2.put(toId, temp);
            }else{
                Map<Integer, Double> tmp1 = new HashMap<Integer, Double>();
                tmp1.put(fromId, 0d);
                tmp1.put(toId, distance);
                distanceMap.put(fromId, tmp1);
                
                Map<Integer, List<Integer>> tmp2 = new HashMap<Integer, List<Integer>>();
                List<Integer> temp = new ArrayList<Integer>();
                temp.add(toId);
                tmp2.put(toId, temp);
                tmp2.put(fromId, new ArrayList<Integer>());
                routeMap.put(fromId, tmp2);
            }
            // 双向路径，从to到from
            if(distanceMap.get(toId) != null){
                Map<Integer, Double> tmp1 = distanceMap.get(toId);
                tmp1.put(fromId, distance);
                
                Map<Integer, List<Integer>> tmp2 = routeMap.get(toId);
                List<Integer> temp = new ArrayList<Integer>();
                temp.add(fromId);
                tmp2.put(fromId, temp);
            }else{
                Map<Integer, Double> tmp1 = new HashMap<Integer, Double>();
                tmp1.put(toId, 0d);
                tmp1.put(fromId, distance);
                distanceMap.put(toId, tmp1);
                
                Map<Integer, List<Integer>> tmp2 = new HashMap<Integer, List<Integer>>();
                List<Integer> temp = new ArrayList<Integer>();
                temp.add(fromId);
                tmp2.put(fromId, temp);
                tmp2.put(toId, new ArrayList<Integer>());
                routeMap.put(toId, tmp2);
            }
        }
        
        // floyd算法，求各点之间的最短路径
        for(int k=0; k<pointArray.size(); k++){
            JSONObject pointK = (JSONObject) pointArray.get(k);
            int pointIdK = pointK.getInt("id");
            for(int i=0; i<pointArray.size(); i++){
                JSONObject pointI = (JSONObject) pointArray.get(i);
                int pointIdI = pointI.getInt("id");
                for(int j=0; j<pointArray.size(); j++){
                    JSONObject pointJ = (JSONObject) pointArray.get(j);
                    int pointIdJ = pointJ.getInt("id");
                    // 不可直接到达的路径记为无穷大（使用integer的最大值代替）
                    if(distanceMap.get(pointIdI).get(pointIdJ) == null){
                        distanceMap.get(pointIdI).put(pointIdJ,INF);
                        List<Integer> tempRoute = new ArrayList<Integer>();
                        tempRoute.add(pointIdJ);
                        routeMap.get(pointIdI).put(pointIdJ,tempRoute);
                    }
                    if(distanceMap.get(pointIdI).get(pointIdK) == null){
                        distanceMap.get(pointIdI).put(pointIdK,INF);
                        List<Integer> tempRoute = new ArrayList<Integer>();
                        tempRoute.add(pointIdK);
                        routeMap.get(pointIdI).put(pointIdK,tempRoute);
                    }
                    if(distanceMap.get(pointIdK).get(pointIdJ) == null){
                        distanceMap.get(pointIdK).put(pointIdJ,INF);
                        List<Integer> tempRoute = new ArrayList<Integer>();
                        tempRoute.add(pointIdJ);
                        routeMap.get(pointIdK).put(pointIdJ,tempRoute);
                    }
                    // 更新距离和路径
                    if(distanceMap.get(pointIdI).get(pointIdJ) > distanceMap.get(pointIdI).get(pointIdK) + distanceMap.get(pointIdK).get(pointIdJ)){
                        distanceMap.get(pointIdI).put(pointIdJ,distanceMap.get(pointIdI).get(pointIdK) + distanceMap.get(pointIdK).get(pointIdJ));
                        List<Integer> tempRoute = routeMap.get(pointIdI).get(pointIdJ);
                        tempRoute.clear();
                        tempRoute.addAll(routeMap.get(pointIdK).get(pointIdJ));
                        tempRoute.addAll(routeMap.get(pointIdI).get(pointIdK));
                    }
                }
            }
        }
        // JSONArray转List
        @SuppressWarnings({ "unchecked" })
        List<Map<String, Object>> pointData = pointArray;
        
        // 将计算后的路径矩阵保存至系统
        GlobalConf.addNavigateData(fileName, routeMap, pointData);
        LOG.info("navigate path refreshed:"+fileName);
    };

    /** 
     * @Title: calcDistanceBetweenPoints 
     * @Description: 计算2个给定点之间的距离
     * @param fromId
     * @param toId
     * @param pointArray
     * @return 
     */
    private double calcDistanceBetweenPoints(int fromId, int toId, JSONArray pointArray){
        // from点的坐标
        double x1 = 0;
        double y1 = 0;
        // to点的坐标
        double x2 = 0;
        double y2 = 0;
        // 遍历数据，找出from和to对应的坐标
        for(int i = 0; i<pointArray.size(); i++){
            JSONObject p = (JSONObject) pointArray.get(i);
            int id = p.getInt("id");
            if(id == fromId){
                x1 = Double.parseDouble(p.getString("x"));
                y1 = Double.parseDouble(p.getString("y"));
            }else if(id == toId){
                x2 = Double.parseDouble(p.getString("x"));
                y2 = Double.parseDouble(p.getString("y"));
            }
        }
        // 计算距离
        double result = Math.sqrt(Math.pow((x1 - x2),2) + Math.pow((y1 - y2),2));
        // 返回字符串
        return result;
    }
}
