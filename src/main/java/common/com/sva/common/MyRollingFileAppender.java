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
         * 閲嶅啓鐖剁被鐨勬柟娉�
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
            /** 濡傛灉maxBackups <= 0锛岄偅涔堟枃浠朵笉鐢ㄩ噸鍛藉悕 **/
            if (maxBackupIndex > 0) {

                /** 鍒犻櫎鏃ф枃浠� **/
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
                    /** 鍦ㄦ瘡澶╀竴涓棩蹇楃洰褰曠殑鏂瑰紡涓嬶紝妫�娴嬫棩鏈熸槸鍚﹀彉鏇翠簡锛屽鏋滃彉鏇翠簡灏辫鎶婂彉鏇村悗鐨勬棩蹇楁枃浠舵嫹璐濆埌鍙樻洿鍚庣殑鏃ユ湡鐩綍涓嬨�� **/
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

                    /** 濡傛灉閲嶅懡鍚嶅け璐ワ紝閲嶆柊鎵撳紑鏂囦欢锛屽苟鍦ㄦ枃浠朵笂杩藉姞 **/
                    if (!renameSucceeded) {
                        try {
                            FileUtils.copy(file, target);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } // 灏嗘棫鏂囦欢鎷疯礉缁欐柊鏂囦欢  
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

            /** 閲嶅懡鍚嶆垚鍔� **/
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
         * 鏂囦欢涓暟鐨勯暱搴﹁ˉ闆讹紝濡傛灉鏂囦欢涓暟涓�10閭ｄ箞鏂囦欢鐨勪釜鏁伴暱搴﹀氨鏄�2浣嶏紝绗竴涓枃浠跺氨鏄�01锛�02锛�03....
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
         * 閲嶅啓 RollingFileAppender鐨勬柟娉�
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
//                /** 妫�娴嬫棩鏈熸槸鍚﹀凡缁忓彉鏇翠簡锛屽鏋滃彉鏇翠簡灏辫閲嶅垱寤烘枃浠� **/
//                if (!fileMaps.get(fileName).getDate().equals(nowDate)) {
//                    rollOver();
//                    return;
//                }
                /** 妫�娴嬫枃浠跺ぇ灏忥紝瓒呰繃鎸囧畾澶у皬閲嶆柊鍒涘缓鏂囦欢 **/
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
//            /** 濡傛灉鏂囦欢璺緞鍖呭惈浜嗏�測yyyMMdd鈥濆氨鏄瘡澶╀竴涓棩蹇楃洰褰曠殑鏂瑰紡璁板綍鏃ュ織(绗竴娆＄殑鏃跺��) **/
//            if (fileName.indexOf("yyyyMMdd") != -1) {
//                String beginFileName = fileName;
//                fileName = fileName.replace("yyyyMMdd", nowDate);
//                fileMaps.put(fileName, new BeginFileData(beginFileName, nowDate));
//            }
//            BeginFileData beginFileData = fileMaps.get(fileName);
//
//            /** 妫�娴嬫棩鏈熸槸鍚﹀凡缁忓彉鏇翠簡锛屽鏋滃彉鏇翠簡灏辫鎶婂師濮嬬殑瀛楃涓茬粰fileName鍙橀噺锛屾妸鍙樻洿鍚庣殑鏃ユ湡鍋氫负寮�濮嬫棩鏈� **/
//            if (!beginFileData.getDate().equals(nowDate)) {
//                /** 鑾峰彇鍑虹涓�娆＄殑鏂囦欢鍚� **/
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
        
        /** 娓呯┖鏂囦欢鍐呭 */  
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
            //浼犲叆鏂囦欢璺緞
            File file = new File(path);
            long size = 0;
            //娴嬭瘯姝ゆ枃浠舵槸鍚﹀瓨鍦�
            if(file.exists()){
                //濡傛灉鏄枃浠跺す
                //杩欓噷鍙娴嬩簡鏂囦欢澶逛腑绗竴灞� 濡傛灉鏈夐渶瑕� 鍙互缁х画閫掑綊妫�娴�
                if(file.isDirectory()){
                    for(File zf : file.listFiles()){
                        if(zf.isDirectory()) continue;
                        size += zf.length();
                    }
                    System.out.println("鏂囦欢澶� "+file.getName()+" Size: "+(size/1024f)+"kb");
                }else{
                    System.out.println(file.getName()+" Size: "+(file.length()/1024f)+"kb");
                }
            //濡傛灉鏂囦欢涓嶅瓨鍦�
            }else{
                System.out.println("姝ゆ枃浠朵笉瀛樺湪");
            }
            return size;
        }
}
