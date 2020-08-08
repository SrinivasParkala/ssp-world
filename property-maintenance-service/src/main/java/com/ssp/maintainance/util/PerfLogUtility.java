package com.ssp.maintainance.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.services.MessageByLocaleService;

public class PerfLogUtility {

	private static final String SEPERATOR = ",";
	
	private static final Logger logger = LoggerFactory.getLogger("performance");
	
	@Autowired
	private static MessageByLocaleService messageByLocaleService;
	
	public static void logMessage(MasterControl control){
		StringBuilder sb = new StringBuilder(control.getFromControl(MasterControl.TENANT).toString()).append(SEPERATOR);
		sb.append(control.getFromControl(MasterControl.USER)).append(SEPERATOR);
		sb.append(control.buildTimeStreamForService());
		
		logger.info(sb.toString());
	}
	

}
