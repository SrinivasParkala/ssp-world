package com.ssp.maintainance.meta.beans;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.services.MessageByLocaleService;
import com.ssp.maintainance.util.MessageConts;
import com.ssp.maintainance.util.StringUtil;

@MappedSuperclass
public abstract class RefMasterRootEntity<T> extends MasterRootEntity{
		
	@Column(name = "statusValue", nullable = false)
	protected String statusValue;
	
	@Column(name = "description", nullable = true)
	private String description;
	
	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue){
		this.statusValue = statusValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void validateEntity(MasterControl masterControl) throws InvalidInputException{
		super.validateEntity(masterControl);
		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		
		if( StringUtil.isStringNull(this.statusValue) ){
			String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_000.toString());
			throw new InvalidInputException(message);
		}
	
	}
	
	public abstract T getUnitId();

	public abstract void setUnitId(T unitId);
}
