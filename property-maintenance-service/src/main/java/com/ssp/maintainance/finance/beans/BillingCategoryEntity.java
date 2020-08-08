package com.ssp.maintainance.finance.beans;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.MasterRootEntity;
import com.ssp.maintainance.meta.keys.EntityIntKey;

@Entity
@Table(name = "t_bill_catergory")
public class BillingCategoryEntity extends MasterRootEntity{
	@EmbeddedId
	private EntityIntKey unitId;
	
	private int billingType;
	
	private float billingCost;
	
	private int formulaId;
	
	//Default = current time
	private Date startDate;
	
	private Date endDate;
	
	private int periodicType;
	
	//Default = 1
	private int installamentTerms = 1;

	public EntityIntKey getUnitId() {
		return unitId;
	}

	public void setUnitId(EntityIntKey unitId) {
		this.unitId = unitId;
	}

	public int getBillingType() {
		return billingType;
	}

	public void setBillingType(int billingType) {
		this.billingType = billingType;
	}

	public float getBillingCost() {
		return billingCost;
	}

	public void setBillingCost(float billingCost) {
		this.billingCost = billingCost;
	}

	public int getFormulaId() {
		return formulaId;
	}

	public void setFormulaId(int formulaId) {
		this.formulaId = formulaId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getPeriodicType() {
		return periodicType;
	}

	public void setPeriodicType(int periodicType) {
		this.periodicType = periodicType;
	}

	public int getInstallamentTerms() {
		return installamentTerms;
	}

	public void setInstallamentTerms(int installamentTerms) {
		this.installamentTerms = installamentTerms;
	}

	@Override
	public void validateEntity(MasterControl masterControl) throws InvalidInputException{
		super.validateEntity(masterControl);
		
		if( startDate == null){
			Calendar cal = Calendar.getInstance();
			startDate = cal.getTime();
		}
		
		if( installamentTerms == 0 ){
			installamentTerms = 1;
		}
		
	}
}
