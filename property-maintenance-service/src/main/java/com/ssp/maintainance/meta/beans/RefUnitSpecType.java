package com.ssp.maintainance.meta.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.services.MessageByLocaleService;
import com.ssp.maintainance.util.MessageConts;
import com.ssp.maintainance.util.StringUtil;

@Entity
@Table(name = "r_unitsp_tp")
public class RefUnitSpecType extends RefMasterRootEntity<RefEntityIntKey>{
		
	private String dataType;

	private int sequence;

	@EmbeddedId
	private RefEntityIntKey unitId;
	
	
	public RefEntityIntKey getUnitId() {
		return unitId;
	}

	public void setUnitId(RefEntityIntKey unitId) {
		this.unitId = unitId;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
	@Override
	public void validateEntity(MasterControl masterControl) throws InvalidInputException{
		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		
		super.validateEntity(masterControl);
		if( StringUtil.isStringNull(dataType) ){
			String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_002.toString());
			throw new InvalidInputException(message);
		}
		
		if( !(dataType.equals(dataT.String.toString()) || dataType.equals(dataT.Numeric.toString()) || dataType.equals(dataT.Date.toString()))){
			String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_003.toString(), new String[]{dataType});
			throw new InvalidInputException(message);
		}
	}
	
	public enum dataT {
		String, Numeric, Date;
	}
}
