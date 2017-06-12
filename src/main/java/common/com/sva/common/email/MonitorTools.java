package com.sva.common.email;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class MonitorTools {

	private static Logger Log = Logger.getLogger(MonitorTools.class);
	
	private static String systemName = System.getProperty("os.name").trim().toLowerCase();

	/**
	 * 查看已经使用的磁盘空间占比
	 * @param path
	 * 		要查看的盘符("C:" or "/root")
	 * @return
	 * 		如果盘符（目录）不存在，则返回Double.NaN
	 */
	public static double getDiskSpace(String path) {
		try {
			if (systemName.startsWith("win")) {
				return getDiskSpaceByWindows(path);
			} else {
				return getDiskSpaceByLinux(path);
			}
		} catch (IOException e) {
			Log.error("", e);
		}
		return Double.NaN;
	}

	/**
	 * 查看windows系统下磁盘空间使用占比
	 * @param path
	 * 		盘符（例如：C:）
	 * @return
	 */
	private static double getDiskSpaceByWindows(String path) {

		File file = new File(path);
		long totalSpace = file.getTotalSpace();
		long freeSpace = file.getFreeSpace();
		if(totalSpace == 0){
			return Double.NaN;
		}
		
		return (1 - freeSpace * 1.0 / totalSpace) * 100;
	}

	/**
	 * 查看linux系统下磁盘空间使用占比
	 * @param path
	 * 		目录（例如：/root）
	 * @return
	 * @throws IOException
	 */
	private static double getDiskSpaceByLinux(String path) throws IOException {
		double usedHD = Double.NaN;
		StringBuffer result = new StringBuffer();
		Runtime rt = Runtime.getRuntime();

		// df -hl 查看硬盘空间
		Process p = rt.exec("df -hl " + path);

		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str = null;
			String[] strArray = null;
			while ((str = in.readLine().toLowerCase()) != null) {
				result.append(str);
				// 只关注跟path相关的信息
				if(!str.contains(path)){
					continue;
				}
				strArray = str.split(" ");
				for (String tmp : strArray) {
					// 找到使用占比数据
					if (tmp.indexOf("%") != -1) {
						usedHD = Double.parseDouble(tmp.substring(0, tmp.length() - 1));
					}
				}
			}
		} catch (Exception e) {
			Log.error("", e);
		} finally {
			in.close();
			Log.debug(result);
		}
		return usedHD;
	}
	
	public static void main(String[] args) {
		Double result = MonitorTools.getDiskSpace("D:");
		System.out.println("result = " + result + "%");
		System.out.println(result.equals(Double.NaN));
	}

}
