package com.sva.common.email;

import org.apache.log4j.Logger;

public class GetProperty {

	private static Logger Log = Logger.getLogger(GetProperty.class);

	// 通过java.util.ResourceBundle读取资源属性文件  
	public static String getPropertyByName(String path, String name) {
		String result = "";
		Log.info("--getPropertyByName--: path = " + path);
		try {
			// 通过java.util.ResourceBundle读取资源属性文件  
			result = java.util.ResourceBundle.getBundle(path).getString(name);
		} catch (Exception e) {
			Log.error("getPropertyByName error", e);
		}
		return result;
	}

}
