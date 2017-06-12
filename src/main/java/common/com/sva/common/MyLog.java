package com.sva.common;

import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.net.SyslogAppender;

/** 
 * @ClassName: MyLog 
 * @Description: 自定义log日志
 * @author JunWang 
 * @date 2016年12月1日 上午9:36:01 
 *  
 */
public class MyLog {
        private static Logger prruLog = null;
        private static Logger prruLocation = null;
        private static Logger prruOther = null;
        private static MyLog log = new MyLog();  
        private MyLog(){}  
        public static MyLog getInstance() {   
            prruLog = log.createLogger("prru", false);
            prruLocation = log.createLogger("location", false);
            prruOther = log.createLogger("other", false);
            return log;    
        }   

        /**  
         * 继承Level自定义级别 
         */   
        private static class MyLogLevel extends Level{  
            private static final long serialVersionUID = 1L;  
            protected MyLogLevel(int level, String levelStr, int syslogEquivalent) {  
                super(level, levelStr, syslogEquivalent);  
            }  
        }  
        /**  
         * 自定义级别名称，以及级别范围  
         */    
        private static final Level CustomerLevelPrru = new MyLogLevel(20100, "PRRU", SyslogAppender.LOG_LOCAL0);
        
        private static final Level CustomerLevelLocation = new MyLogLevel(20150, "LOCATION", SyslogAppender.LOG_LOCAL1);
        
        private static final Level CustomerLevelOther = new MyLogLevel(20200, "OTHER", SyslogAppender.LOG_LOCAL2);
          
        /**  
         * 生成日志对象 
         * @param filePath 日志输出路径 
         * @param fileName 日志文件名 
         * @param conversionPattern log的输出形式   
         * @param flag true:在已存在log文件后面追加 false:新log覆盖以前的log 
         */    
        public  Logger createLogger(String fileName, boolean flag){
            //获取路径
            String path = System.getProperty("user.dir");
//            path = path+"/";
//            System.out.println(path);
            path = path.substring(0, path.indexOf("bin")) +"/logs/run/";
            //获取时间
//            Date d = new Date(); 
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//            String dateNowStr = sdf.format(d);  
            // 生成新的Logger    
            // 如果已经有了一个Logger实例则返回    
            Logger logger = Logger.getLogger(fileName);   
            // 清空Appender。特別是不想使用现存实例时一定要初期化    
//            logger.removeAllAppenders();    
            // 设计定Logger级别。    
            logger.setLevel(Level.INFO);    
            // 设定是否继承父Logger。    
            // 默认为true。继承root输出。    
            // 设定false后将不输出root。    
            logger.setAdditivity(false);    
            // 生成新的Appender    
            MyRollingFileAppender appender = new MyRollingFileAppender(); 
//            appender.setMaxFileSize("10MB");
            appender.setMaxBackupIndex(10);
            // log的输出形式    
            PatternLayout layout = new PatternLayout();     
            layout.setConversionPattern( "%d{yyyy/MM/dd HH:mm:ss,SSS} %m%n");    
            appender.setLayout(layout);    
            // log输出路径      
            appender.setFile(path+ fileName + ".log");
//            MyRollingFileAppender.fileName = path+ fileName + ".log";
//            MyRollingFileAppender.fileMaps.put(fileName, new BeginFileData(fileName, )) = path+ fileName + ".log";
            // log的文字码    
            appender.setEncoding("UTF-8"); 
            // true:在已存在log文件后面追加 false:新log覆盖以前的log    
            appender.setAppend(false); 
            // 适用当前配置    
            appender.activateOptions();    
            // 将新的Appender加到Logger中    
            logger.addAppender(appender); 
            return logger;  
        }  
        /**  
         * 使用自定义pruu日志打印logger中的log方法 
         * @param logger 日志对象 
         * @param objLogInfo：日志内容 
         */    
        public void prru(Object objLogInfo){
            prruLog.log(CustomerLevelPrru, objLogInfo);
        }  
                
        
        /**  
         * 使用自定义location日志打印logger中的log方法 
         * @param logger 日志对象 
         * @param objLogInfo：日志内容 
         */    
        public void location(Object objLogInfo){
            prruLocation.log(CustomerLevelLocation, objLogInfo);
        }  

        /**  
         * 使用自定义other日志打印logger中的log方法 
         * @param logger 日志对象 
         * @param objLogInfo：日志内容 
         */    
        public void other(Object objLogInfo){
            prruOther.log(CustomerLevelOther, objLogInfo);  
        } 
        
        
        /** 
         * 关闭自定义log 
         * @param logger 日志对象 
         */  
        @SuppressWarnings("unchecked")  
        public void closeMyLog(Logger logger){  
            for (Enumeration<Appender> appenders=logger.getAllAppenders(); appenders.hasMoreElements();) {  
                Appender appender=appenders.nextElement();  
            appender.close();  
        }  
        }  

}
