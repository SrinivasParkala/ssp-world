package com.ssp.maintainance.meta.services;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.MasterRootEntity;
import com.ssp.maintainance.util.DateUtil;
import com.ssp.maintainance.util.GenLogUtility;

public abstract class AbstractService {

	protected void preExecute(MasterControl control, String method) throws InvalidInputException{
		GenLogUtility.logMessage(control, GenLogUtility.LogLevel.DEBUG, this.getClass().getSimpleName(), method, "Entering method");
		
		Object currentObject = control.getFromControl(MasterControl.CURRENT_OBJECT);
		if( currentObject != null && currentObject instanceof MasterRootEntity){
			((MasterRootEntity)currentObject).validateEntity(control);
			((MasterRootEntity)currentObject).setLastUpdatedDate(DateUtil.getCurrentDateTime());
			((MasterRootEntity)currentObject).setLastUpdatedUser(control.getFromControl(MasterControl.USER).toString());
		}
		
		validateObject(control);
		
		control.setStartTime(this.getClass().getSimpleName()+"."+method, System.currentTimeMillis());
		GenLogUtility.logMessage(control, GenLogUtility.LogLevel.DEBUG, this.getClass().getSimpleName(), method, "Exiting method");
	}
	
	protected void postExecute(MasterControl control, String method){
		GenLogUtility.logMessage(control, GenLogUtility.LogLevel.DEBUG, this.getClass().getSimpleName(), method, "Entering method");
		control.setEndTime(this.getClass().getSimpleName()+"."+method, System.currentTimeMillis());
		
		postTxnAction(control);
		
		GenLogUtility.logMessage(control, GenLogUtility.LogLevel.DEBUG, this.getClass().getSimpleName(), method, "Exiting method");
	}
	
	public abstract void validateObject(MasterControl masterControl) throws InvalidInputException;
	
	public abstract void postTxnAction(MasterControl masterControl);
}
