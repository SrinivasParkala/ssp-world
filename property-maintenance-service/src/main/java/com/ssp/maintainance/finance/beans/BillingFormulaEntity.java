package com.ssp.maintainance.finance.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.RefMasterRootEntity;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

@Entity
@Table(name = "t_bill_formula")
public class BillingFormulaEntity extends RefMasterRootEntity<RefEntityIntKey>{
	@EmbeddedId
	private RefEntityIntKey unitId;
	
	@Override
	public void validateEntity(MasterControl masterControl) throws InvalidInputException{
		super.validateEntity(masterControl);
	}

	@Override
	public RefEntityIntKey getUnitId() {
		return unitId;
	}

	@Override
	public void setUnitId(RefEntityIntKey unitId) {
		this.unitId = unitId;
	}

}
