package com.ssp.maintainance.meta.beans;

import java.util.HashMap;
import java.util.Map;

public class MasterControl {
	public enum ActionType {
		GET, POST, PUT, DELETE;
	}
	
	public static final String TOKEN_SEPERATOR = "@";
	public static final String SEPERATOR = ",";
	public static final String TENANT = "TENANT";
	public static final String USER = "USER";
	public static final String MESSAGE_SERVICE ="messageByLocaleService";
	public static final String CONTROLLER = "CONTROLLER";
	public static final String CONTROLLER_METHOD = "CONTROLLER_METHOD";
	public static final String SERVICE_METHOD = "SERVICE_METHOD";
	public static final String CURRENT_OBJECT = "CURRENT_OBJECT";
	public static final String CURRENT_BEAN = "CURRENT_BEAN";
	public static final String LANG_ID = "LANG_ID";
	
	private Map<String,Object> controlObject = new HashMap<String,Object>();
	private MasterTimeCapture masterTimeCapture = new MasterTimeCapture();
	private ActionType actionType;
	
	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public void addToControl(String key, Object value){
		controlObject.put(key, value);
	}
	
	public Object getFromControl(String key){
		return controlObject.get(key);
	}
	
	public void setStartTime(String objectKey, long startTime){
		masterTimeCapture.setStartTime(objectKey, startTime);
	}
	
	public void setEndTime(String objectKey, long endTime){
		masterTimeCapture.setEndTime(objectKey, endTime);
	}
	
	public String buildTimeStreamForService(){
		StringBuilder stringBuilder = new StringBuilder(masterTimeCapture.buildTimeStreamForService());
		return stringBuilder.toString();
	}
}
