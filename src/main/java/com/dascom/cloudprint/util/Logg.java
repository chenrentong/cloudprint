package com.dascom.cloudprint.util;

import org.apache.log4j.Logger;
/**
 * slf4j日志写入
   * 
 */

public class Logg {
		
	 private static final Logger logger=Logger.getLogger("com.dascom.yun");
	
	public static void writeErrorLog(String msg){
		logger.error(msg);
	}
	
	public static  void writeWarnLog(String msg){
		logger.warn(msg);
	}
	
	public  static void writeInfoLog(String msg){
		logger.info(msg);
	}
	
	public static  void writeDebugLog(String msg){
		logger.debug(msg);
	}
	
	public static  void writeTraceLog(String msg){
		logger.trace(msg);
	}
	
	public  void writeException(Exception e){
		logger.error(e.getMessage(), e);
	}
	
}
