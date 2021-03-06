package com.sva.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.sva.common.conf.Params;
import com.sva.model.DynamicAccuracyModel;
import com.sva.model.LocationDelayModel;
import com.sva.model.MessagePushModel;
import com.sva.model.StaticAccuracyModel;

public class Util
{
    
    private Util(){        
    }
    
    /**
     * 测试是否能ping通
     * 
     * @param server
     * @param timeout
     * @return
     */
    public static Map<String, Object> ping(String ip, int pingnumber,
            int packtsize, int timeOut)
    {
        List<String> out = new ArrayList<String>(10);
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("ip", ip);
        if ("".equals(ip))
        {
            result.put(Params.RETURN_KEY_ERROR, true);
            result.put(Params.RETURN_KEY_DATA, "remoteIpAddress is empty!");
            return result;
        }

        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();
        // 将要执行的ping命令,此命令是windows格式的命令
        String pingCommand = "ping " + ip + " -n " + pingnumber + "-l"
                + packtsize + " -w " + timeOut;
        try
        {
            // 执行命令并获取输出
            Process p = r.exec(pingCommand);
            if (p == null)
            {
                result.put(Params.RETURN_KEY_ERROR, true);
                result.put(Params.RETURN_KEY_DATA, "exec command failed!");
                return result;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream(),
                    "gbk"));
            // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            int connectedCount = 0;
            String line = null;
            while ((line = in.readLine()) != null)
            {
                out.add(line);
                connectedCount += getCheckResult(line);
            }

            result.put(Params.RETURN_KEY_ERROR, true);
            result.put(Params.RETURN_KEY_DATA, out);
            // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            if (connectedCount == pingnumber)
            {
                result.put(Params.RETURN_KEY_ERROR, false);
            }
            
            in.close();
            
            return result;
        }
        catch (Exception ex)
        {
            Logger.getLogger(Util.class).info(ex);
            // 出现异常则返回假
            result.put(Params.RETURN_KEY_ERROR, true);
            result.put(Params.RETURN_KEY_DATA, ex.toString());
            return result;
        }
    }

    /**
     * 若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
     * 
     * @param line
     * @return
     */
    private static int getCheckResult(String line)
    {
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        boolean b = matcher.find();
        if (b)
        {
            return 1;
        }
        return 0;
    }

    /**
     * 
     * @param startTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @param flag
     *            时间段标识，1：hour，2：day
     * @return
     */
    public static Map<String, Object> getPeriodList(String startTime,
            String endTime, int flag)
    {
        Map<String, Object> result = new HashMap<String, Object>(2);

        Date start = ConvertUtil.dateStringFormat(startTime,
                Params.YYYYMMDDHHMMSS);
        Date end = ConvertUtil.dateStringFormat(endTime, Params.YYYYMMDDHHMMSS);

        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        Date tmpDate = cal.getTime();
        long endTimeInSec = end.getTime();

        if (flag == 1)
        {
            long time = tmpDate.getTime();
            String keyTime;
            Boolean b = tmpDate.before(end);
            while (b || time == endTimeInSec)
            {
                b = tmpDate.before(end);
                keyTime = ConvertUtil.dateFormat(cal.getTime(),
                        Params.YYYYMMDDHHMMSS);
                result.put(keyTime, "");
                cal.add(Calendar.HOUR, 1);
                tmpDate = cal.getTime();
            }
        }
        else if (flag == 2)
        {
            long time = tmpDate.getTime();
            String keyTime;
            Boolean b = tmpDate.before(end);
            while (b || time == endTimeInSec)
            {
                b = tmpDate.before(end);
                keyTime = ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDD2);
                result.put(keyTime, "");
                cal.add(Calendar.DATE, 1);
                tmpDate = cal.getTime();
            }
        }
        return result;
    }
    
    /** 
     * @Title: getCDF 
     * @Description: 封装CDF图数据结构
     * @param listName 曲线图名称
     * @param res  原始数据
     * @param total 曲线图Y中英文
     * @return Map<String,Object>       
     * @throws 
     */
    public static Map<String, Object> getCDF(List<String> listName,
        Collection<StaticAccuracyModel> res,String total)
    {
        Map<String, Object> map  = new HashMap<>();
        List<Map<String , Object>> allData = new ArrayList<>();
        List<BigDecimal> avgeOffsetList = new ArrayList<>();
        //最大误差集合
        List<BigDecimal> maxOffsetList = new ArrayList<>();
        //静态精度集合
        List<BigDecimal> staicAccuracyList = new ArrayList<>();
        //偏移重心集合
        List<BigDecimal> offsetCenterList = new ArrayList<>();
        //偏移量集合
        List<BigDecimal> offsetNumberList = new ArrayList<>();
        //稳定度集合
        List<BigDecimal> stabilityList = new ArrayList<>();
        
        List<BigDecimal> allDataList = new ArrayList<>();
        
        List<Integer>  listx = new ArrayList<>();
        
        List<Integer> newAvgeOffsetList = new ArrayList<>();
        List<Integer> newMaxOffsetList = new ArrayList<>();
        List<Integer> newStaicAccuracyList = new ArrayList<>();
        List<Integer> newOffsetCenterList = new ArrayList<>();
        List<Integer> newOffsetNumberList = new ArrayList<>();
        List<Integer> newStabilityList = new ArrayList<>();
        
        int maxNumber = 0;
        
        for (StaticAccuracyModel staModel:res)
        {
            avgeOffsetList.add(staModel.getAvgeOffset());
            maxOffsetList.add(staModel.getMaxOffset());
            staicAccuracyList.add(staModel.getStaicAccuracy());
            offsetCenterList.add(staModel.getOffsetCenter());
            offsetNumberList.add(staModel.getOffsetNumber());
            stabilityList.add(staModel.getStability());
            
            allDataList.add(staModel.getAvgeOffset());
            allDataList.add(staModel.getMaxOffset());
            allDataList.add(staModel.getStaicAccuracy());
            allDataList.add(staModel.getOffsetCenter());
            allDataList.add(staModel.getOffsetNumber());
            allDataList.add(staModel.getStability());
        }
        Collections.sort(allDataList);
        Collections.sort(avgeOffsetList);
        Collections.sort(maxOffsetList);
        Collections.sort(staicAccuracyList);
        Collections.sort(offsetCenterList);
        Collections.sort(offsetNumberList);
        Collections.sort(stabilityList);
        
        String avgeOffsetName = listName.get(0);
        String maxOffsetName = listName.get(1);
        String staicAccuracyName = listName.get(2);
        String offsetCenterName = listName.get(3);
        String offsetNumberName = listName.get(4);
        String stabilityName = listName.get(5);
        if (allDataList.size()>2)
        {
            maxNumber = allDataList.get(allDataList.size()-1).intValue();
        }
        int xSize = avgeOffsetList.size();
        int avgeValue;
        if (maxNumber>15)
        {
             avgeValue =  maxNumber/15;
        }else
        {
            avgeValue = 1;
        }
        for (int i = 0; i < 16; i++)
        {
            listx.add(i*avgeValue);
        }
        allData.add(getCDFDate(listx, xSize, avgeOffsetList,
            newAvgeOffsetList, avgeOffsetName, total));
        allData.add(getCDFDate(listx, xSize, maxOffsetList,
            newMaxOffsetList, maxOffsetName, total));
        allData.add(getCDFDate(listx, xSize, staicAccuracyList,
            newStaicAccuracyList, staicAccuracyName, total));
        allData.add(getCDFDate(listx, xSize, offsetCenterList,
            newOffsetCenterList, offsetCenterName, total));
        allData.add(getCDFDate(listx, xSize, offsetNumberList,
            newOffsetNumberList, offsetNumberName, total));
        allData.add(getCDFDate(listx, xSize, stabilityList,
            newStabilityList, stabilityName, total));              
      
        map.put(Params.CDF_KEY_DATA,allData);
        map.put(Params.CDF_KEY_XVALUE, listx);
        return map;
        
    }
    
    public static Map<String, Object> getCDFDynamicAccuracy(List<String> listName,
        Collection<DynamicAccuracyModel> res,String total)
    {
        Map<String, Object> map  = new HashMap<>();
        List<Map<String , Object>> allData = new ArrayList<>();
        List<BigDecimal> avgeOffsetList = new ArrayList<>();
        //平均误差集合
        List<BigDecimal> maxOffsetList = new ArrayList<>();
        //最大误差集合
        List<BigDecimal> offsetList = new ArrayList<>();
        //误差集合        
        List<BigDecimal> allDataList = new ArrayList<>();
        
        List<Integer>  listx = new ArrayList<>();
        
        List<Integer> newAvgeOffsetList = new ArrayList<>();
        List<Integer> newMaxOffsetList = new ArrayList<>();
        List<Integer> newOffsetList = new ArrayList<>();

        int maxNumber = 0;
        
        for (DynamicAccuracyModel staModel:res)
        {
            avgeOffsetList.add(staModel.getAvgeOffset());
            maxOffsetList.add(staModel.getMaxOffset());
            offsetList.add(staModel.getOffset());

            allDataList.add(staModel.getAvgeOffset());
            allDataList.add(staModel.getMaxOffset());
            allDataList.add(staModel.getOffset());
        }
        Collections.sort(allDataList);
        Collections.sort(avgeOffsetList);
        Collections.sort(maxOffsetList);
        Collections.sort(offsetList);
        
        String avgeOffsetName = listName.get(0);
        String maxOffsetName = listName.get(1);
        String offsetName = listName.get(2);
        if (allDataList.size()>2)
        {
            maxNumber = allDataList.get(allDataList.size()-1).intValue();
        }
        int xSize = avgeOffsetList.size();
        int avgeValue;
        if (maxNumber>15)
        {
             avgeValue =  maxNumber/15;
        }else
        {
            avgeValue = 1;
        }
        for (int i = 0; i < 16; i++)
        {
            listx.add(i*avgeValue);
        }
        allData.add(getCDFDate(listx, xSize, avgeOffsetList,
            newAvgeOffsetList, avgeOffsetName, total));
        allData.add(getCDFDate(listx, xSize, maxOffsetList,
            newMaxOffsetList, maxOffsetName, total));
        allData.add(getCDFDate(listx, xSize, offsetList,
            newOffsetList, offsetName, total));
      
        map.put(Params.CDF_KEY_DATA,allData);
        map.put(Params.CDF_KEY_XVALUE, listx);
        return map;
        
    }
    
    public static Map<String, Object> getCDFLocationDelay(List<String> listName,
        Collection<LocationDelayModel> res,String total)
    {
        Map<String, Object> map  = new HashMap<>();
        List<Map<String , Object>> allData = new ArrayList<>();
        //数据延时集合
        List<Double> dataDelayList = new ArrayList<>();
        //定位延时集合   
        List<Double> positionDelayList = new ArrayList<>();

        
        List<Double> allDataList = new ArrayList<>();
        
        List<Integer>  listx = new ArrayList<>();
        
        List<Integer> newDataDelayList = new ArrayList<>();
        List<Integer> newPositionDelayList = new ArrayList<>();

        int maxNumber = 0;
        
        for (LocationDelayModel staModel:res)
        {
            dataDelayList.add(staModel.getDataDelay());
            positionDelayList.add(staModel.getPositionDelay());

            allDataList.add(staModel.getDataDelay());
            allDataList.add(staModel.getPositionDelay());
        }
        Collections.sort(allDataList);
        Collections.sort(dataDelayList);
        Collections.sort(positionDelayList);

        
        String dataDelayName = listName.get(0);
        String positionDelayName = listName.get(1);
        if (allDataList.size()>2)
        {
            maxNumber = allDataList.get(allDataList.size()-1).intValue();
        }
        int xSize = dataDelayList.size();
        int avgeValue;
        if (maxNumber>15)
        {
             avgeValue =  maxNumber/15;
        }else
        {
            avgeValue = 1;
        }
        for (int i = 0; i < 16; i++)
        {
            listx.add(i*avgeValue);
        }
        allData.add(getCDFDate1(listx, xSize, dataDelayList,
            newDataDelayList, dataDelayName, total));
        allData.add(getCDFDate1(listx, xSize, positionDelayList,
            newPositionDelayList, positionDelayName, total));

              
      
        map.put(Params.CDF_KEY_DATA,allData);
        map.put(Params.CDF_KEY_XVALUE, listx);
        return map;
        
    }
     
    
    public static Map<String, Object> getCDFMessagePush(List<String> listName,
        Collection<MessagePushModel> res,String total)
    {
        Map<String, Object> map  = new HashMap<>();
        List<Map<String , Object>> allData = new ArrayList<>();
        //正确推送次数集合
        List<Double> pushRightList = new ArrayList<>();
        //错误推送次数集合
        List<Double> notPushList = new ArrayList<>();
        //为推送次数集合
        List<Double> pushWrongList = new ArrayList<>();

        
        List<Double> allDataList = new ArrayList<>();
        
        List<Integer>  listx = new ArrayList<>();
        
        List<Integer> newPushRightList = new ArrayList<>();
        List<Integer> newNotPushList = new ArrayList<>();
        List<Integer> newPushWrongList = new ArrayList<>();

        int maxNumber = 0;
        
        for (MessagePushModel staModel:res)
        {
            pushRightList.add(staModel.getPushRight());
            notPushList.add(staModel.getNotPush());
            pushWrongList.add(staModel.getPushWrong());

            allDataList.add(staModel.getPushRight());
            allDataList.add(staModel.getNotPush());
            allDataList.add(staModel.getPushWrong());
        }
        Collections.sort(allDataList);
        Collections.sort(pushRightList);
        Collections.sort(notPushList);
        Collections.sort(pushWrongList);

        
        String pushRightName = listName.get(0);
        String notPushName = listName.get(1);
        String pushWrongName = listName.get(2);
        if (allDataList.size()>2)
        {
            maxNumber = allDataList.get(allDataList.size()-1).intValue();
        }
        int xSize = pushRightList.size();
        int avgeValue;
        if (maxNumber>15)
        {
             avgeValue =  maxNumber/15;
        }else
        {
            avgeValue = 1;
        }
        for (int i = 0; i < 16; i++)
        {
            listx.add(i*avgeValue);
        }
        allData.add(getCDFDate1(listx, xSize, pushRightList,
            newPushRightList, pushRightName, total));
        allData.add(getCDFDate1(listx, xSize, notPushList,
            newNotPushList, notPushName, total));
        allData.add(getCDFDate1(listx, xSize, pushWrongList,
            newPushWrongList, pushWrongName, total));

      
        map.put(Params.CDF_KEY_DATA,allData);
        map.put(Params.CDF_KEY_XVALUE, listx);
        return map;
        
    }
        
    
    private static Map<String, Object> getCDFDate1(List<Integer> listx, int xSize, List<Double> dataDelayList,
        List<Integer> newDataDelayList, String dataDelayName, String total)
    {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        List<Object> list1 = new ArrayList<>();
        Map<String, Object> map2 = new HashMap<>();
        List<Object> list2 = new ArrayList<>();
        list1.add("type : 'max', name: 'max'");
        list1.add("type : 'min', name: 'min'");
        map1.put("data", list1);
        list2.add("type : 'average', name: 'average'");
        map2.put("data", list2);
        for (int j = 0; j < listx.size(); j++)
       {
            int avgeValue1 = 0;
            int valuex = listx.get(j);
            for (int i = 0; i < xSize; i++)
            {
                int valuey = dataDelayList.get(i).intValue();
                    //判断数据是否小于等于X坐标，如果小于则新值加1
                    if (valuey<=valuex)
                    {
                        avgeValue1++;
                    }
            }
            newDataDelayList.add(avgeValue1);

        }
        map.put("name",dataDelayName);
        map.put("type","line");
        map.put("smooth","true");
        map.put("tiled",total);
        map.put("data",newDataDelayList);
        map.put("markPoint", map1);
        map.put("markLine", map2);
        
        return map;
    }

    /** 
     * @Title: getCDFDate 
     * @Description: 封装CDF曲线数据结构
     * @param listx X坐标集合
     * @param xSize 原始数据大小
     * @param setList 原始数据集合
     * @param newSetList 新数据集合
     * @param name  曲线图名称   
     * @param total 曲线图Y中英文
     * @return Map<String,Object>       
     * @throws 
     */
    public static Map<String, Object> getCDFDate( List<Integer>  listx,int xSize,
        List<BigDecimal> setList,List<Integer> newSetList,String name,String total)
    {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        List<Object> list1 = new ArrayList<>();
        Map<String, Object> map2 = new HashMap<>();
        List<Object> list2 = new ArrayList<>();
        list1.add("type : 'max', name: 'max'");
        list1.add("type : 'min', name: 'min'");
        map1.put("data", list1);
        list2.add("type : 'average', name: 'average'");
        map2.put("data", list2);
        for (int j = 0; j < listx.size(); j++)
       {
            int avgeValue1 = 0;
            int valuex = listx.get(j);
            for (int i = 0; i < xSize; i++)
            {
                int valuey = setList.get(i).intValue();
                    //判断数据是否小于等于X坐标，如果小于则新值加1
                    if (valuey<=valuex)
                    {
                        avgeValue1++;
                    }
            }
            newSetList.add(avgeValue1);

        }
        map.put("smooth","true");
        map.put("name",name);
        map.put("type","line");
        map.put("tiled",total);
        map.put("data",newSetList);
        map.put("markPoint", map1);
        map.put("markLine", map2);
        
        return map;
        
    }
    
    public static String getIpAddr(HttpServletRequest request) {      
        String ip = request.getHeader("x-forwarded-for");      
       if(checkIpIsNotExisted(ip)) {      
           ip = request.getHeader("Proxy-Client-IP");      
       }      
       if(checkIpIsNotExisted(ip)) {      
           ip = request.getHeader("WL-Proxy-Client-IP");      
        }      
      if(checkIpIsNotExisted(ip)) {      
            ip = request.getRemoteAddr();      
       }      
      return ip;      
 }
    
    public static String getRemortIP(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");
        if (checkIpIsNotExisted(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    } 
    
    /** 
     * @Title: checkIpIsNotExisted 
     * @Description: 检查ip是否不存在 
     * @param ip
     * @return 
     */
    private static boolean checkIpIsNotExisted(String ip)
    {
        boolean result;
        // 如果ip为空或是unknown，则返回true；否则返回false
        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip))
        {
            result = true;
        }
        else
        {
            result = false;
        }
        
        return result;
    }
    
}
