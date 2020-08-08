package com.ssp.maintainance.meta.beans;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.services.MessageByLocaleService;
import com.ssp.maintainance.util.MessageConts;

@MappedSuperclass
public class MasterRootEntity {
	
	@Column(name = "lastUpdatedUser", nullable = false)
	private String lastUpdatedUser;
	
	@Column(name="lastUpdatedDate", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp lastUpdatedDate;
	
	@Version
    private int version;
	
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	
	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	public void validateEntity(MasterControl masterControl) throws InvalidInputException{
		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		
		if(MasterControl.ActionType.PUT.equals(masterControl.getActionType()) && this.getVersion() == -1){
			String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_001.toString(),new String[]{this.getClass().getName()});
			throw new InvalidInputException(message);
		}
	}
		
}
