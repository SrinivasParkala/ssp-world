package com.ssp.maintainance.finance.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.RefMasterRootEntity;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

@Entity
@Table(name = "r_billing_tp")
public class RefBillingType extends RefMasterRootEntity<RefEntityIntKey> {
	@EmbeddedId
	private RefEntityIntKey unitId;

	//Fixed or SqrFt based
	private int fixed;
		
	private int billingCatType;
		
	private boolean appliableOnce;

	public int getFixed() {
		return fixed;
	}

	public void setFixed(int fixed) {
		this.fixed = fixed;
	}

	public int getBillingCatType() {
		return billingCatType;
	}

	public void setBillingCatType(int billingCatType) {
		this.billingCatType = billingCatType;
	}

	public boolean isAppliableOnce() {
		return appliableOnce;
	}

	public void setAppliableOnce(boolean appliableOnce) {
		this.appliableOnce = appliableOnce;
	}

	@Override
	public RefEntityIntKey getUnitId() {
		return unitId;
	}

	@Override
	public void setUnitId(RefEntityIntKey unitId) {
		this.unitId = unitId;		
	}
	
	@Override
	public void validateEntity(MasterControl masterControl) throws InvalidInputException{
		super.validateEntity(masterControl);
	}
}
