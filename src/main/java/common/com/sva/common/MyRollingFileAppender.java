package com.sva.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.qpid.util.FileUtils;

public class MyRollingFileAppender extends RollingFileAppender{

    
//        public static String fileName = null;
        private long nextRollover = 0;
        public static Map<String, BeginFileData> fileMaps = new HashMap<String, BeginFileData>();
        private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        /**
         * 重写父类的方法
         */
        public void rollOver() {
//            String path = System.getProperty("user.dir");
//            path = path+"/mylog.log";
//            fileName = path;
//            System.out.println(fileName);
//            path = path.substring(0, path.indexOf("bin")) +"/logs/run/";
            File target;
            File file;
//            int maxBackupIndex = 10;
            int maxBackupIndexLeng = String.valueOf(maxBackupIndex).length();
            if (qw != null) {
                long size = ((CountingQuietWriter) qw).getCount();
                LogLog.debug("rolling over count=" + size);
                nextRollover = size + maxFileSize;
            }

            LogLog.debug("maxBackupIndex=" + maxBackupIndex);
            String nowDateString = sdf.format(new Date());
            String newFileName = (fileName.indexOf(".") != -1 
                                 ? fileName.substring(0, fileName.lastIndexOf(".")) 
                                 : fileName);

            boolean renameSucceeded = true;
            /** 如果maxBackups <= 0，那么文件不用重命名 **/
            if (maxBackupIndex > 0) {

                /** 删除旧文件 **/
                file = new File(newFileName + '_'
                        + getIndex(maxBackupIndex, maxBackupIndexLeng));
                if (file.exists()) {
                    renameSucceeded = file.delete();
                }

                for (int i = maxBackupIndex - 1; (i >= 1 && renameSucceeded); i--) {
                    file = new File(newFileName + '_'
                            + getIndex(i, maxBackupIndexLeng));

                    if (file.exists()) {
                        target = new File(newFileName + '_'
                                + getIndex(i + 1, maxBackupIndexLeng));

                        LogLog.debug("Renaming file " + file + " to " + target);
                        renameSucceeded = file.renameTo(target);
                    }
                }

                if (renameSucceeded) {
//                    BeginFileData beginFileData = fileMaps.get(fileName);
                    /** 在每天一个日志目录的方式下，检测日期是否变更了，如果变更了就要把变更后的日志文件拷贝到变更后的日期目录下。 **/
//                    if (newFileName.indexOf(nowDateString) == -1
//                            && beginFileData.getFileName().indexOf("yyyyMMdd") != -1) {
//                        newFileName = beginFileData.getFileName().replace(
//                                "yyyyMMdd", nowDateString);

                        newFileName = (newFileName.indexOf(".") != -1 ? newFileName
                                .substring(0, newFileName.lastIndexOf("."))
                                : newFileName);

//                    }
                    target = new File(newFileName + '_'
                            + getIndex(1, maxBackupIndexLeng));
                    this.closeFile();
                    file = new File(fileName);
                    LogLog.debug("Renaming file " + file + " to " + target);

                    renameSucceeded = file.renameTo(target);

                    /** 如果重命名失败，重新打开文件，并在文件上追加 **/
                    if (!renameSucceeded) {
                        try {
                            FileUtils.copy(file, target);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } // 将旧文件拷贝给新文件  
                        emptyFileContent(file); 
                        try {

                            this.setFile(fileName, true, bufferedIO, bufferSize);
//                            nextRollover = 0;
                        } catch (IOException e) {
                            LogLog.error("setFile(" + fileName
                                    + ", true) call failed.", e);
                        }
                    }
                }
            }

            /** 重命名成功 **/
            if (renameSucceeded) {

                try {
                    this.setFile(fileName, false, bufferedIO, bufferSize);
                    nextRollover = 0;
                } catch (IOException e) {
                    LogLog.error("setFile(" + fileName + ", false) call failed.", e);
                }
            }
        }

        /**
         * 文件个数的长度补零，如果文件个数为10那么文件的个数长度就是2位，第一个文件就是01，02，03....
         * 
         * @param i
         * @param maxBackupIndexLeng
         * @return
         */
        private String getIndex(int i, int maxBackupIndexLeng) {
            String index = String.valueOf(i);
            int len = index.length();
            for (int j = len; j < maxBackupIndexLeng; j++) {
                index = "0" + index;
            }
            return index + ".log";
        }

        /**
         * 重写 RollingFileAppender的方法
         * 
         * @since 0.9.0
         */
        protected  void subAppend(LoggingEvent event) {
            super.subAppend(event);
//            System.out.println(event.getLevel());
            String path = System.getProperty("user.dir");
//            path = path+"/";
            path = path.substring(0, path.indexOf("bin")) +"/logs/run/";
            if ("PRRU".equals(event.getLevel().toString())) {
                fileName = path+"prru.log";
//                System.out.println(fileName);
            }
            if ("OTHER".equals(event.getLevel().toString())) {
                fileName = path+"other.log";
//                System.out.println(fileName);
            }
            if ("LOCATION".equals(event.getLevel().toString())) {
                fileName = path+"location.log";
//                System.out.println(fileName);
            }
            if (fileName != null && qw != null) {

//                String nowDate = sdf.format(new Date());
//                /** 检测日期是否已经变更了，如果变更了就要重创建文件 **/
//                if (!fileMaps.get(fileName).getDate().equals(nowDate)) {
//                    rollOver();
//                    return;
//                }
                /** 检测文件大小，超过指定大小重新创建文件 **/
                long size = ((CountingQuietWriter) qw).getCount();
//                System.out.println(size);
//                long size = getFileSize(fileName);
                if (size >= maxFileSize && size >= nextRollover) {
                    rollOver();
                }
            }
        }

//        @Override
//        public synchronized void setFile(String fileName, boolean append,
//                boolean bufferedIO, int bufferSize) throws IOException {
//
//            String nowDate = sdf.format(new Date());
//
//            /** 如果文件路径包含了“yyyyMMdd”就是每天一个日志目录的方式记录日志(第一次的时候) **/
//            if (fileName.indexOf("yyyyMMdd") != -1) {
//                String beginFileName = fileName;
//                fileName = fileName.replace("yyyyMMdd", nowDate);
//                fileMaps.put(fileName, new BeginFileData(beginFileName, nowDate));
//            }
//            BeginFileData beginFileData = fileMaps.get(fileName);
//
//            /** 检测日期是否已经变更了，如果变更了就要把原始的字符串给fileName变量，把变更后的日期做为开始日期 **/
//            if (!beginFileData.getDate().equals(nowDate)) {
//                /** 获取出第一次的文件名 **/
//                beginFileData.setDate(nowDate);
//                fileName = beginFileData.getFileName().replace("yyyyMMdd", nowDate);
//                fileMaps.put(fileName, beginFileData);
//            }
//            super.setFile(fileName, append, this.bufferedIO, this.bufferSize);
//        }

        class BeginFileData {

            public BeginFileData(String fileName, String date) {
                super();
                this.fileName = fileName;
                this.date = date;
            }

            private String fileName;
            private String date;

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
        
        /** 清空文件内容 */  
        public static void emptyFileContent(File file) {  
            FileOutputStream out = null;  
            try {  
                out = new FileOutputStream(file);  
                out.write(new byte[0]);  
            } catch (Exception e) {  
                LogLog.error("Can't not empty " + file.getName());  
            } finally {  
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    LogLog.error("emptyFileContent error:"+e);
                }  
            }  
        }  
        
        public static long getFileSize(String path){
            //传入文件路径
            File file = new File(path);
            long size = 0;
            //测试此文件是否存在
            if(file.exists()){
                //如果是文件夹
                //这里只检测了文件夹中第一层 如果有需要 可以继续递归检测
                if(file.isDirectory()){
                    for(File zf : file.listFiles()){
                        if(zf.isDirectory()) continue;
                        size += zf.length();
                    }
                    System.out.println("文件夹 "+file.getName()+" Size: "+(size/1024f)+"kb");
                }else{
                    System.out.println(file.getName()+" Size: "+(file.length()/1024f)+"kb");
                }
            //如果文件不存在
            }else{
                System.out.println("此文件不存在");
            }
            return size;
        }
}
