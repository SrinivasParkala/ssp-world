package com.ssp.maintainance.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.services.MessageByLocaleService;

public class GenLogUtility {

	private static final String SEPERATOR = ",";
	
	private static final Logger logger = LoggerFactory.getLogger("general");
	
	@Autowired
	private static MessageByLocaleService messageByLocaleService;
	
	public static void logMessage(MasterControl control, LogLevel logLevel, String className, String methodName, String message){
		StringBuilder sb = new StringBuilder(control.getFromControl(MasterControl.TENANT).toString()).append(SEPERATOR);
		sb.append(control.getFromControl(MasterControl.USER)).append(SEPERATOR);
		sb.append(className).append(".").append(methodName);
		
		log(logLevel, sb.toString());
	}
	
	public static void logMessage(MasterControl control, LogLevel logLevel,String className, String methodName, String message, Throwable throwabe){
		StringBuilder sb = new StringBuilder(control.getFromControl(MasterControl.TENANT).toString()).append(SEPERATOR);
		sb.append(control.getFromControl(MasterControl.USER)).append(SEPERATOR);
		sb.append(className).append(".").append(methodName);
		
		log(logLevel, sb.toString(), throwabe);
	}

	public static void logMessage(MasterControl control, LogLevel logLevel,String className, String methodName, String messageId, String... params){
		StringBuilder sb = new StringBuilder(control.getFromControl(MasterControl.TENANT).toString()).append(SEPERATOR);
		sb.append(control.getFromControl(MasterControl.USER)).append(SEPERATOR);
		sb.append(className).append(".").append(methodName).append(SEPERATOR).append(messageByLocaleService.getMessage(messageId, params));
		
		log(logLevel, sb.toString());
	}
	
	public static void logMessage(MasterControl control, LogLevel logLevel,String className, String methodName, Throwable throwabe, String messageId, String... params){
		StringBuilder sb = new StringBuilder(control.getFromControl(MasterControl.TENANT).toString()).append(SEPERATOR);
		sb.append(control.getFromControl(MasterControl.USER)).append(SEPERATOR);
		sb.append(className).append(".").append(methodName).append(SEPERATOR).append(messageByLocaleService.getMessage(messageId, params));
		
		log(logLevel, sb.toString(), throwabe);
	}
	
	private static void log(LogLevel logLevel, String message){
		switch(logLevel){
			case INFO:{
				logger.info(message);
				break;
			}
			case WARNING:{
				logger.warn(message);
				break;
			}
			case DEBUG:{
				logger.debug(message);
				break;
			}
			case ERROR:{
				logger.error(message);
				break;
			}
		}
	}
	
	private static void log(LogLevel logLevel, String message, Throwable throwabe){
		switch(logLevel){
			case INFO:{
				logger.info(message,throwabe);
				break;
			}
			case WARNING:{
				logger.warn(message,throwabe);
				break;
			}
			case DEBUG:{
				logger.debug(message,throwabe);
				break;
			}
			case ERROR:{
				logger.error(message,throwabe);
				break;
			}
		}
	}
	
	public enum LogLevel {
		INFO, WARNING, DEBUG, ERROR;
	}
}
