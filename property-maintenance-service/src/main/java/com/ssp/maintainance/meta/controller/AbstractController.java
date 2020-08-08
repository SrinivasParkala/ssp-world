package com.ssp.maintainance.meta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ssp.maintainance.beans.IGenericBean;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.finance.beans.IReadOnlyBean;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.RefMasterRootEntity;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.services.MessageByLocaleService;
import com.ssp.maintainance.util.GenLogUtility;
import com.ssp.maintainance.util.PerfLogUtility;
import com.ssp.maintainance.util.SspConstants;

public abstract class AbstractController {
	
	@Autowired
    MessageByLocaleService messageByLocaleService;
	
	protected MasterControl initControl(){
		MasterControl control = new MasterControl();
		control.addToControl(MasterControl.CONTROLLER, this.getClass().getSimpleName());
		return control;
	}
	
	protected void preExecute(MasterControl control, String method) throws InvalidInputException{
		parseAndSetAuthToken(control);
		
		GenLogUtility.logMessage(control, GenLogUtility.LogLevel.DEBUG, this.getClass().getSimpleName(), method, "Enter method");
		control.setStartTime(this.getClass().getSimpleName()+"."+method, System.currentTimeMillis());
		GenLogUtility.logMessage(control, GenLogUtility.LogLevel.DEBUG, this.getClass().getSimpleName(), method, "Exiting method");
	}
	
	protected void postExecute(MasterControl control, String method){
		GenLogUtility.logMessage(control, GenLogUtility.LogLevel.DEBUG, this.getClass().getSimpleName(), method, "Enter method");
		control.setEndTime(this.getClass().getSimpleName()+"."+method, System.currentTimeMillis());
		
		logTimeStreamForService(control);
		GenLogUtility.logMessage(control, GenLogUtility.LogLevel.DEBUG, this.getClass().getSimpleName(), method, "Exiting method");
	}
	
	private void parseAndSetAuthToken(MasterControl control){
		control.addToControl(MasterControl.MESSAGE_SERVICE, messageByLocaleService);
		
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if( user instanceof String ){
			String[] userTokes = user.toString().split(MasterControl.TOKEN_SEPERATOR);
			control.addToControl(MasterControl.USER, userTokes[0]);
			control.addToControl(MasterControl.TENANT, userTokes[1]);
		}
		
		Object currentObject = control.getFromControl(MasterControl.CURRENT_OBJECT);
		if( currentObject != null ){
			if( currentObject instanceof RefMasterRootEntity<?>){
				RefMasterRootEntity<RefEntityIntKey> refType = (RefMasterRootEntity<RefEntityIntKey>)currentObject;
				
				RefEntityIntKey entityIntKey = refType.getUnitId();
				if( entityIntKey == null ){
					entityIntKey = new RefEntityIntKey();
					refType.setUnitId(entityIntKey);
				}
				
				if(entityIntKey.getLangId() < 1 ){
					entityIntKey.setLangId(SspConstants.DEFAULT_LANG);
				}
				
				entityIntKey.setTenantId(control.getFromControl(MasterControl.TENANT).toString());
				refType.setLastUpdatedUser(control.getFromControl(MasterControl.USER).toString());
			}
		}
		
		currentObject = control.getFromControl(MasterControl.CURRENT_BEAN);
		if( currentObject != null ){
			if( currentObject instanceof IGenericBean<?>){
				IGenericBean bean =(IGenericBean)currentObject;
				setTenant(bean, control);
			}
			else if( currentObject instanceof IReadOnlyBean ){
				IReadOnlyBean objectType = (IReadOnlyBean)currentObject;
				List<Object> genericObj  = objectType.getChildBeans();
				for( Object bean : genericObj ){
					if( bean instanceof List ){
						List<IGenericBean> beans = (List<IGenericBean>)bean;
						for( IGenericBean b : beans ){
							setTenant((IGenericBean)b, control);
						}
					}
					else if( bean instanceof IGenericBean ){
						setTenant((IGenericBean)bean, control);
					}
				}//for
			}
		}
	}
	
	private void setTenant(IGenericBean bean, MasterControl control){
		bean.setTenantId(control.getFromControl(MasterControl.TENANT).toString());
		bean.setLastUpdatedUser(control.getFromControl(MasterControl.USER).toString());
	}
	
	private void logTimeStreamForService(MasterControl control){
		PerfLogUtility.logMessage(control);
	}
	
}
