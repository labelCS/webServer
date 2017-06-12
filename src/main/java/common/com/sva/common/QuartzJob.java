package com.sva.common;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.sva.common.conf.Params;
import com.sva.dao.AreaDao;
import com.sva.dao.CommonDao;
import com.sva.dao.LinemapDao;
import com.sva.dao.LocationDao;
import com.sva.dao.MapsDao;
import com.sva.dao.MessageDao;
import com.sva.dao.PetLocationDao;
import com.sva.dao.StoreDao;
import com.sva.model.AreaInputModel;
import com.sva.model.AreaModel;
import com.sva.model.MessageModel;
import com.sva.model.StoreModel;
import com.sva.service.ValidateSVAService;

public class QuartzJob {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private CommonDao dao;

    @Autowired
    private MapsDao daoMaps;

    @Autowired
    private MessageDao daoMsg;

    @Autowired
    private AreaDao daoArea;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private LinemapDao linemapDao;
    
    @Autowired
    private LocationDao locDao;
    
    @Autowired
    private ValidateSVAService validateSVAService;
    
    @Autowired
    private PetLocationDao pet;
    
    @Value("${reflashTime}")
    private String reflashTime;

    @Value("${mysql.db}")
    private String db;

    private static final Logger LOG = Logger.getLogger(QuartzJob.class);

    /** 
     * @Title: doCreateTable 
     * @Description: 动态创建位置数据表任务处理器  
     */
    public void doCreateTable() {
        // 今日表
        String tableName = Params.LOCATION + ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD);
        // 明日表
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        String time = ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDD);
        String tableNameTommorrow = Params.LOCATION + time;
        LOG.info(db + ':' + tableName);
        String sqlDelete = "delete from allpeople";
        String sqlDelete1 = "delete from nowpeople";
        try {
            dao.doUpdate(sqlDelete);
            dao.doUpdate(sqlDelete1);
            // 如果今日表不存在则创建
            if (dao.isTableExist(tableName, db) < 1) {
                LOG.info("create table today:"+tableName);
                // 动态创建表
                dao.createTable(tableName);
            }
            // 如果明日表不存在则创建
            if (dao.isTableExist(tableNameTommorrow, db) < 1) {
                LOG.info("create table tommorrow:"+tableName);
                // 动态创建表
                dao.createTable(tableNameTommorrow);
            }
        } catch (Exception e) {
            LOG.error(e);
        }

    }

    public void doStatisticData() {
        long startTime = System.currentTimeMillis() - 3600000;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String time = ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + time;
        String sqlTest = "select count(*) from statisticday where time = '" + time + '\'';
        String sqlHour = "insert into statistichour "
                + "(SELECT b.placeId placeId,FROM_UNIXTIME(a.timestamp/1000,'%Y%m%d%H0000') time, "
                + "COUNT(distinct a.userID) number FROM "
                + tableName + " a join maps b on a.z = b.floorNo and a.timestamp> " + startTime
                + " GROUP BY b.placeId,time)";

        String sqlDay = "replace into statisticday " + "(SELECT b.placeId placeId,"
                + ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDD) + " time,COUNT(distinct a.userID) number FROM "
                + tableName + " a join maps b on a.z = b.floorNo GROUP BY b.placeId)";

        String sqlFloor = "replace into statisticfloor "
                + "(SELECT userID, FROM_UNIXTIME(timestamp/1000,'%Y%m%d%H0000') time ,z FROM " + tableName
                + " group by userID,time,z) ";
        try {
            int result = dao.doTest(sqlTest);
            LOG.info(result);
            if (result == 0) {
                dao.doUpdate(sqlDay);
                dao.doUpdate(sqlHour);
                dao.doUpdate(sqlFloor);
            }
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    public void doDeleteInfo() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(Params.YYYYMMDD);
        calendar.add(Calendar.MONTH, -6);
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();
        List<String> list = dao.doDeleteInfo("location" + sdf.format(date));
        int si = list.size();
        if (si > 0) {
            for (int i = 0; i < si; i++) {
                dao.deleteTable(list.get(i));
            }
        }
    }

    public void doStatisticOneHour() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -1);
        String time = ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDDHHMM) + "00";
        LOG.info(time);
        String tableName = Params.LOCATION + ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDD);
        String sqlInsert = "insert into locationtemp(x,y,z,idType,dataType,timestamp,userID)"
                + "select a.x,a.y,a.z,a.idType,a.dataType,a.timestamp,a.userID from "
                + "(SELECT x,y,z,idType,dataType,timestamp,userID,"
                + "FROM_UNIXTIME(timestamp/1000,'%Y%m%d%H%i00') time FROM " 
                + tableName + " where FROM_UNIXTIME(timestamp/1000,'%Y%m%d%H%i00') = " + time
                + " GROUP BY userID,time) a;";

        cal.add(Calendar.HOUR, -1);
        time = ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDDHHMM) + "00";
        LOG.info(time);
        LOG.info(sqlInsert);
        String sqlDelete = "delete from locationtemp " + "where FROM_UNIXTIME(timestamp/1000,'%Y%m%d%H%i00') < " + time;

        try {
            dao.doUpdate(sqlInsert);
            dao.doUpdate(sqlDelete);
        } catch (Exception e) {
            LOG.error(e);
        }

    }

    /** 
     * @Title: doStatisticDataPerHalfHour 
     * @Description: 每个小时按小时统计、按天统计、按楼层统计预处理    
     * @throws 
     */
    public void doStatisticDataPerHalfHour() {
        // 获取一个小时之前的时间戳
        long startTime = System.currentTimeMillis() - Params.ONE_HOUR;
        long insertTime = System.currentTimeMillis() - Params.HALF_HOUR;
        //获取前半个小时的小时时间
        String times = ConvertUtil.dateFormat(insertTime,Params.YYYYMMddHH00);
        //获取今天的日期时间
        String time = ConvertUtil.dateFormat(insertTime, Params.YYYYMMDD);
        String tableName = Params.LOCATION + time;
        LOG.info("doStatisticDataPerHalfHour:" + ConvertUtil.dateFormat(System.currentTimeMillis(), "yyyyMMddHHmmSS"));
        // 获取最近一小時各个商场的人流量
        String selectSqlHour = "SELECT b.placeId placeId,'"+times+"' time,COUNT(distinct a.userID) number "
                + "FROM " + tableName + " a join maps b on a.z = b.floorNo and a.timestamp> " + startTime
                + " GROUP BY b.placeId";
        // 获取当天各个商场的人流量
        String selectSqlDay = "SELECT b.placeId placeId,"
                + time + " time,COUNT(distinct a.userID) number "
                + "FROM " + tableName + " a join maps b on a.z = b.floorNo GROUP BY b.placeId";
        // 获取每个楼层、各个用户、每个小时的分布
        String selectSqlFloor = "SELECT userID, FROM_UNIXTIME(timestamp/1000,'%Y%m%d%H0000') time ,z " + "FROM " 
                + tableName + " group by userID,time,z";

        try {
            List<Map<String,Object>> selectSqlHourResult = dao.doTest1(selectSqlHour);
            String sqlHour = "insert into statistichour(placeId,time,number) values";
            if(!selectSqlHourResult.isEmpty()){
                for(Map<String,Object> m:selectSqlHourResult){
                    sqlHour += "('"+m.get("placeId")+ "','"+m.get("time")+"','"+m.get("number")+"'),";
                }
                sqlHour = sqlHour.substring(0, sqlHour.length()-1);
                dao.doUpdate(sqlHour);
            }
            
            List<Map<String,Object>> selectSqlDayResult = dao.doTest1(selectSqlDay);
            String sqlDay = "replace into statisticday(placeId,time,number) values";
            if(!selectSqlDayResult.isEmpty()){
                for(Map<String,Object> m:selectSqlDayResult){
                    sqlDay += "('"+m.get("placeId")+ "','"+m.get("time")+"','"+m.get("number")+"'),";
                }
                sqlDay = sqlDay.substring(0, sqlDay.length()-1);
                dao.doUpdate(sqlDay);
            }
            
            List<Map<String,Object>> selectSqlFloorResult = dao.doTest1(selectSqlFloor);
            String sqlFloor = "replace into statisticfloor(userID,time,z) values";
            if(!selectSqlFloorResult.isEmpty()){
                for(Map<String,Object> m:selectSqlFloorResult){
                    sqlFloor += "('"+m.get("userID")+ "','"+m.get("time")+"',"+m.get("z")+"),";
                }
                sqlFloor = sqlFloor.substring(0, sqlFloor.length()-1);
                dao.doUpdate(sqlFloor);
            }
            
        } catch (Exception e) {
            LOG.error(e);
        }

    }

    public void refreshRangeStat() {
        String sqlDelete = "delete from statistictemp";
        String sqlInsert = "";
        String areaName = "";
        String areaNumber = "";
        String tableName = Params.LOCATION + ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD);
        Collection<MessageModel> resultList = daoMsg.doquery();
        try {
            dao.doUpdate(sqlDelete);
            for (MessageModel l : resultList) {
                areaName = l.getArea().getAreaName();
                areaNumber = String.valueOf(daoMsg.getNumberByArea(l, tableName));
                sqlInsert = "insert into statistictemp(name,number) values ('" + areaName + "'," + areaNumber + ')';
                dao.doUpdate(sqlInsert);
            }
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    public void addLineStat() {
        long time = System.currentTimeMillis() - 30 * 60 * 1000;
        Calendar cal = Calendar.getInstance();
        String time2 = ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDDHHMM) + "00";
        String tableName = Params.LOCATION + ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDD);
        String sqlInsert = "insert into statisticlinetemp(number,time,placeId) "
                + "(select count(distinct a.userID) number," + time2 + " time,b.placeId from " + tableName
                + " a join maps b on a.z = b.floorNo where timestamp > " + time + " group by b.placeId)";
        LOG.info("addLineStat:" + sqlInsert);
        try {
            dao.doUpdate(sqlInsert);
        } catch (Exception e) {
            LOG.error(e);
        }
    }

//    public void doStatisticArea() {
//        // 一小时间之前的时间戳
//        long startTime = System.currentTimeMillis() - 3600000;
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.HOUR, -1);
//        String time = ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDDHH) + "0000";
//        LOG.info("doStatisticArea:" + ConvertUtil.dateFormat(cal.getTime(), "yyyyMMddHHmmSS"));
//        String sqlInsert = "";
//        String areaName = "";
//        int areaNumber = 0;
//        Collection<AreaModel> resultList = daoArea.doquery();
//        try {
//            for (AreaModel l : resultList) {
//                areaName = l.getId();
//                areaNumber = linemapDao.getAreaNumberByHour(areaName, startTime);
//                sqlInsert = "insert into statisticarea(time,areaId,number) values ('" + time + "','" + areaName + "',"
//                        + areaNumber + ')';
//                dao.doUpdate(sqlInsert);
//            }
//        } catch (Exception e) {
//            LOG.error(e);
//        }
//    }
    
    
    public void doStatisticArea() {
        // 一小时间之前的时间戳
        long startTime = System.currentTimeMillis() - 3600000;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -1);
        String tableName = Params.LOCATION + ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDD);
        String time = ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDDHH) + "0000";
        LOG.info("doStatisticArea:" + ConvertUtil.dateFormat(cal.getTime(), "yyyyMMddHHmmSS"));
        String sqlInsert = "";
        String areaName = "";
        int areaNumber = 0;
        Collection<AreaModel> resultList = daoArea.doquery();
        BigDecimal beishu = new BigDecimal(10);
        try {
            for (AreaModel l : resultList) {
                areaName = l.getId();
                BigDecimal x = l.getxSpot().multiply(beishu);
                BigDecimal x1 = l.getX1Spot().multiply(beishu);
                BigDecimal y = l.getySpot().multiply(beishu);
                BigDecimal y1 = l.getY1Spot().multiply(beishu);
                BigDecimal floorNo = l.getMaps().getFloorNo();
                areaNumber = linemapDao.getAreaNumberByHour(floorNo,tableName, startTime,x,x1,y,y1);
                sqlInsert = "insert into statisticarea(time,areaId,number) values ('" + time + "','" + areaName + "',"
                        + areaNumber + ')';
                dao.doUpdate(sqlInsert);
            }
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    public void doStatisticAreaByDay() 
    {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, -1);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        String tableName = Params.LOCATION + ConvertUtil.dateFormat(currentDate.getTime(), Params.YYYYMMDD);
        String time2 = ConvertUtil.dateFormat(currentDate.getTime(), Params.YYYYMMDDHH) + "0000";
        LOG.info("doStatisticAreaByDay:tableName = " + tableName);
        String sqlInsert = "";
        String areaName = "";
        String areaNumber = "";
        Collection<AreaModel> resultList = daoArea.doquery();
        BigDecimal beishu = new BigDecimal(10);
        try {
            for (AreaModel l : resultList) {
                areaName = l.getId();
                BigDecimal x = l.getxSpot().multiply(beishu);
                BigDecimal x1 = l.getX1Spot().multiply(beishu);
                BigDecimal y = l.getySpot().multiply(beishu);
                BigDecimal y1 = l.getY1Spot().multiply(beishu);
                BigDecimal floorNo = l.getMaps().getFloorNo();
                areaNumber = String.valueOf(linemapDao.getAreaNumberByDay(floorNo,tableName,x,x1,y,y1));
                sqlInsert = "insert into statisticareaDay(time,areaId,number) values ('" + time2 + "','" + areaName
                        + "'," + areaNumber + ')';
                dao.doUpdate(sqlInsert);
            }
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    public void doMinuteAllPeople() {
        Collection<StoreModel> stores = storeDao.doStores();
        int placeId;
        Calendar currentDate = new GregorianCalendar();
        String tableName = Params.LOCATION + ConvertUtil.dateFormat(currentDate.getTime(), Params.YYYYMMDD);
        Calendar currentDate1 = Calendar.getInstance();
        currentDate.add(Calendar.DATE, -1);
        currentDate1.set(Calendar.MINUTE, 0);
        currentDate1.set(Calendar.SECOND, 0);
        String yesTableName = Params.LOCATION + ConvertUtil.dateFormat(currentDate.getTime(), Params.YYYYMMDD);
        String nowTime = ConvertUtil.dateFormat(currentDate1.getTime(), Params.YYYYMMDDHHMMSS);
        String yesterDay = ConvertUtil.dateFormat(currentDate.getTime(), Params.YYYYMMDD2);
        String visitDay = ConvertUtil.dateFormat(currentDate1.getTime(), Params.YYYYMMDD2);
        String yestimeDay = yesterDay + ' ' + nowTime.split(" ")[1];
        long yesTimes = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(Params.YYYYMMDDHHMMSS);
        try {
            yesTimes = sdf.parse(yestimeDay).getTime();
            if (dao.isTableExist(yesTableName, db) < 1) {
                Logger.getLogger(QuartzJob.class).info("create");
                // 动态创建表
                dao.createTable(yesTableName);
            }
        } catch (Exception e) {
            LOG.error(e);
        }
        int number;
        int yesNumber;
        int nowNumber;
        String areaName;
        long time;
        int count;
        String areaId;
        AreaModel area;
        int size;
        List<AreaModel> models;
        for (StoreModel store : stores) {
            time = System.currentTimeMillis() - 10 * 1000;
            placeId = store.getId();
            nowNumber = locDao.getNowPeople(tableName, String.valueOf(placeId), String.valueOf(time));
            number = locDao.getNumberByMinute(tableName, String.valueOf(placeId));
            yesNumber = locDao.getYesterdayNumber(yesTableName, String.valueOf(placeId), yesTimes);
            daoMaps.saveAllPeople(placeId, yesNumber, number, nowNumber, visitDay);
            models = areaDao.getAreaByPlaceId(String.valueOf(placeId));
            size = models.size();
            for (int i = 0; i < size; i++) {
                area = models.get(i);
                areaName = area.getAreaName();
                areaId = area.getId();
                count = areaDao.getNumberByFloorNo(area, String.valueOf(time), tableName);
                daoMaps.saveNowPeople(placeId, count, areaName, areaId);
            }
        }
    }

    public static String getMinute(long time, int size) {
        if (size == 0) {
            return "0";
        } else {
            float b = (time / 1000) / 60;
            float averageTime = b / size;
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(averageTime);
        }

    }
    
    /** 
     * @Title: peopleStayCalc 
     * @Description: 统计各个区域人流出现情况
     * @param allPeople
     * @param allArea
     * @param inputList
     * @param updateList
     */
    private void peopleStayCalc(List<Map<String, Object>> allPeople, List<Map<String, Object>> allArea,
            List<AreaInputModel> inputList, List<AreaInputModel> updateList) throws SQLException{
        for (int i = 0; i < allPeople.size(); i++) {
            int x = Integer.parseInt(allPeople.get(i).get("x").toString());
            int y = Integer.parseInt(allPeople.get(i).get("y").toString());
            int z = Integer.parseInt(allPeople.get(i).get("z").toString());
            String idType = (String) allPeople.get(i).get("idType");
            String dataType = (String) allPeople.get(i).get("dataType");
            String userID = (String) allPeople.get(i).get("userID");
            long timestamp = Long.parseLong(allPeople.get(i).get("timestamp").toString());

            for (int j = 0; j < allArea.size(); j++) {
                int floorNo = (int) (Float.parseFloat(allArea.get(j).get("floorNo").toString()));
                int x0 = (int) (Float.parseFloat(allArea.get(j).get("xSpot").toString()) * 10);
                int y0 = (int) (Float.parseFloat(allArea.get(j).get("ySpot").toString()) * 10);
                int x1 = (int) (Float.parseFloat(allArea.get(j).get("x1Spot").toString()) * 10);
                int y1 = (int) (Float.parseFloat(allArea.get(j).get("y1Spot").toString()) * 10);
                int disId = Integer.parseInt(allArea.get(j).get("id").toString());
                // 判断该人是否在该区域内
                boolean isInAreaX = x >= x0 && x <= x1;
                boolean isInAreaY = y >= y0 && y <= y1;
                if(!isInAreaX || !isInAreaY || z != floorNo){
                    continue;
                }
                
                String sqlChk = "select count(*) from district_during where userID = '" + userID
                        + "' and district_id = '" + disId + "'";

                long timeLocal = System.currentTimeMillis();

                // 判断该人是否之前在该区域出现过，是则更新，否则插入
                int result = dao.doTest(sqlChk);
                if (result == 0) {
                    AreaInputModel inputModel = new AreaInputModel();
                    inputModel.setAreaId(disId);
                    inputModel.setTimestamp(timestamp);
                    inputModel.setBigentime(timestamp);
                    inputModel.setDataType(dataType);
                    inputModel.setIdType(idType);
                    inputModel.setUserID(userID);
                    inputModel.setLocaltime(timeLocal);
                    inputList.add(inputModel);
                    LOG.info("insert:start");
                } else {
                    AreaInputModel updateModel = new AreaInputModel();
                    updateModel.setAreaId(disId);
                    updateModel.setTimestamp(timestamp);
                    updateModel.setBigentime(timestamp);
                    updateModel.setDataType(dataType);
                    updateModel.setIdType(idType);
                    updateModel.setUserID(userID);
                    updateModel.setLocaltime(timeLocal);
                    updateList.add(updateModel);
                }                
            }
        }
    }

    public void districtDuringStatistic() {
        // 读取配置文件
        InputStream in = getClass().getClassLoader().getResourceAsStream("sva.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            LOG.error("load properties ERROR.", e);
        }
        List<AreaInputModel> inputList = new ArrayList<>();
        List<AreaInputModel> updateList = new ArrayList<>();
        // 取出1分钟内的人
        long time = System.currentTimeMillis() - 60 * 1000;
        String tableName = Params.LOCATION  + ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD);
        List<Map<String, Object>> allPeople = dao.getAllPeople(tableName, time);
        LOG.info("people:" + allPeople.size());
        // 取出所有的区域信息
        List<Map<String, Object>> allArea = dao.getAllArea();
        LOG.info("area:" + allArea.size());
        // 将人按区域更新到区域统计信息表中
        try {
            peopleStayCalc(allPeople,allArea,inputList,updateList);
        } catch (SQLException e1) {
            LOG.error(e1);
        }
        
        String idType;
        String userID;
        String dataType;
        int locCount = 0;
        long timestamp;
        long timeBegin;
        long timeLocal;
        int areaId;
        int during = 0;
        AreaInputModel model;
        AreaInputModel model1;
        int inputSize = inputList.size();
        int updateSize = updateList.size();
        String sqlIns = "insert into district_during(idType,timestamp,time_begin,time_local,during,dataType,district_id,userID,loc_count) values ";
        String update = null;
        
        List<String> inputTempList = new ArrayList<String>();        
        for (int i = 0; i <inputSize; i++) {
            model = inputList.get(i);
            idType = model.getIdType();
            userID = model.getUserID();
            dataType = model.getDataType();
            timestamp = model.getTimestamp();
            timeBegin = model.getBigentime();
            timeLocal = model.getLocaltime();
            areaId = model.getAreaId();
            
            String temp = "('"+idType+"',"+timestamp+","+timeBegin+","+timeLocal+","+during+",'"+dataType+"',"+areaId+",'"+userID+"',"+locCount+")";
            inputTempList.add(temp);
        }
        
        String sql = sqlIns + StringUtils.join(inputTempList, ",");
        
        if (updateSize>0) {
            update = "update district_during set timestamp="+updateList.get(0).getTimestamp()+" where ";
        }
        List<String> updateTempList = new ArrayList<String>();
        for (int j = 0; j <updateSize; j++) {
            model1 = updateList.get(j);
            userID = model1.getUserID();
            areaId = model1.getAreaId();
            String temp = "(userId = '"+userID+"' and district_id = "+areaId+")";
            updateTempList.add(temp);
        }
        String sqlup = update+StringUtils.join(updateTempList, " or ");
        
        LOG.debug("insertSiz:"+inputList.size()+" updateSiz:"+updateList.size());
        if (updateSize>0) {
            dao.doUpdate(sqlup);
        }
        if (inputSize>0) {
            dao.doUpdate(sql);
        }

        LOG.debug("结束时间戳："+System.currentTimeMillis());
    }
    
    /** 
     * @Title: checkSvaIsEnabled 
     * @Description: 启动数据库中为启动状态的sva  
     * @author gl
     */
    public void checkSvaIsEnabled(){
        validateSVAService.validateActiveSVA();
    }

    public void saveVisitiTime() {
        System.err.println("saveVisitiTime");
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
//        List<Map<String, Object>> allArea = dao.getAllArea();
//        List<Map<String, Object>> lis;
        Collection<AreaModel> resultList = daoArea.doquery();
        String visitDay = ConvertUtil.dateFormat(currentDate.getTime(), Params.YYYYMMDD2);
        String tableName = Params.LOCATION + ConvertUtil.dateFormat(currentDate.getTime(), Params.YYYYMMDD);
        int allAreaNumber = 0;
        int allCount = 0;
        long visitime = 0;
        String areaId = "";
        BigDecimal beishu = new BigDecimal(10);
        for (AreaModel l :resultList) {
            areaId = l.getId();
            BigDecimal x = l.getxSpot().multiply(beishu);
            BigDecimal x1 = l.getX1Spot().multiply(beishu);
            BigDecimal y = l.getySpot().multiply(beishu);
            BigDecimal y1 = l.getY1Spot().multiply(beishu);
            BigDecimal floorNo = l.getMaps().getFloorNo();
            allAreaNumber = linemapDao.getAreaNumberByDay(floorNo,tableName,x,x1,y,y1);
            allCount  = linemapDao.getAllAreaData(floorNo,tableName,x,x1,y,y1);
            visitime = allCount*2000L;
            String insetSql = "REPLACE INTO staticvisit(areaId,time,allTime,number,averageTime) VALUES(" + areaId + ",'"
                    + visitDay + "'," + visitime + "," + allAreaNumber + "," + visitime + ")";
            dao.saveVisitiTime(insetSql);
        }

    }

    public void emptyPhoneData() {
        String sqlDeletePhone = "delete from locationphone";
        String sqlDeleteDistrict = "delete from district_during";

        try {
            dao.doUpdate(sqlDeletePhone);
            dao.doUpdate(sqlDeleteDistrict);
        } catch (Exception e) {
            LOG.error(e);
        }
    } 
    
    /** 
     * @Title: petRefresh 
     * @Description: 宠物位置刷新  
     */
    public void petRefresh()
    {
        //产生随机数，变化宠物位置
        Random random = new Random();
        int randNumberX = random.nextInt(5)+1;
        int positiveOrNegativeX = random.nextInt(2);
        if (positiveOrNegativeX==0) {
            randNumberX = -randNumberX;
        }
        int randNumberY = random.nextInt(5)+1;
        int positiveOrNegativeY = random.nextInt(2);
        if (positiveOrNegativeY==0) {
            randNumberY = -randNumberY;
        }
        //获取系统时间
        
        long petTime = System.currentTimeMillis();
        long tempTiem = 60000*Long.valueOf(reflashTime);
        long updataTime = pet.getMaxPetTime();
        if (updataTime+tempTiem<=petTime) {
            String sql = "update petlocation set status=0, actualPositionX = x+"+randNumberX+",actualPositionY=y+"+randNumberY+",petRefreshTime="+petTime+";";
            dao.doUpdate(sql);
        }
    }   
}
