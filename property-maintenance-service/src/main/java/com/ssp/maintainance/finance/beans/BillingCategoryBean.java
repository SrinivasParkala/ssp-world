package com.ssp.maintainance.finance.beans;

import java.util.Date;

import com.ssp.maintainance.beans.IGenericBean;
import com.ssp.maintainance.meta.keys.EntityIntKey;
import com.ssp.maintainance.util.DateUtil;
import com.ssp.maintainance.util.StringUtil;

public class BillingCategoryBean extends IGenericBean<BillingCategoryEntity>{
	
	//Fixed/SqrFt/tenantBase based
	private int fixed;
	
	private int billingType;
	
	private String billingValue;
	
	private float billingCost;
	
	private int formulaId;
	
	private String formulaObject;
	
	private Date startDate;
	
	private Date endDate;
	
	private int periodicType;
	
	private String periodicValue;
	
	private int installamentTerms;
	
	private boolean appliableOnce; 
	
	private String billingCatValue;

	public int isFixed() {
		return fixed;
	}

	public void setFixed(int fixed) {
		this.fixed = fixed;
	}

	public int getBillingType() {
		return billingType;
	}

	public void setBillingType(int billingType) {
		this.billingType = billingType;
	}

	public String getBillingValue() {
		return billingValue;
	}

	public void setBillingValue(String billingValue) {
		this.billingValue = billingValue;
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

	public String getFormulaObject() {
		return formulaObject;
	}

	public void setFormulaObject(String formulaObject) {
		this.formulaObject = formulaObject;
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

	public String getPeriodicValue() {
		return periodicValue;
	}

	public void setPeriodicValue(String periodicValue) {
		this.periodicValue = periodicValue;
	}

	public int getInstallamentTerms() {
		return installamentTerms;
	}

	public void setInstallamentTerms(int installamentTerms) {
		this.installamentTerms = installamentTerms;
	}

	public boolean isAppliableOnce() {
		return appliableOnce;
	}

	public void setAppliableOnce(boolean appliableOnce) {
		this.appliableOnce = appliableOnce;
	}

	public String getBillingCatValue() {
		return billingCatValue;
	}

	public void setBillingCatValue(String billingCatValue) {
		this.billingCatValue = billingCatValue;
	}

	public int getFixed() {
		return fixed;
	}

	@Override
	public BillingCategoryEntity convertToEntityObject() {
		BillingCategoryEntity entity = new BillingCategoryEntity();
		
		EntityIntKey id = null;
		
		if( StringUtil.isStringNull(this.getKeyId()) ){
			id = new EntityIntKey(this.getTenantId());
		}
		else{
			id = new EntityIntKey(this.getTenantId(), Integer.valueOf(this.getKeyId()));
		}
		entity.setUnitId(id);
		
		entity.setBillingCost(this.getBillingCost());
		entity.setBillingType(this.getBillingType());
		entity.setEndDate(this.getEndDate());
		entity.setFormulaId(this.getFormulaId());
		entity.setInstallamentTerms(this.getInstallamentTerms());
		entity.setLastUpdatedUser(this.getLastUpdatedUser());
		entity.setPeriodicType(this.getPeriodicType());
		entity.setStartDate(this.getStartDate());
		entity.setVersion(this.getVersion());
		
		if( this.getLastUpdatedDate() == null ){
			entity.setLastUpdatedDate(DateUtil.getCurrentDateTime());
		}
		else{
			entity.setLastUpdatedDate(this.getLastUpdatedDate());
		}
		
		return entity;
	}

	@Override
	public void convertToBeanObject(BillingCategoryEntity entity) {
		this.setBillingCost(entity.getBillingCost());
		this.setBillingType(entity.getBillingType());
		this.setEndDate(entity.getEndDate());
		this.setStartDate(entity.getStartDate());
		this.setFormulaId(entity.getFormulaId());
		this.setInstallamentTerms(entity.getInstallamentTerms());
		this.setLastUpdatedDate(entity.getLastUpdatedDate());
		this.setLastUpdatedUser(this.getLastUpdatedUser());
		this.setPeriodicType(entity.getPeriodicType());
		this.setVersion(entity.getVersion());
		this.setKeyId(String.valueOf(entity.getUnitId().getKeyId()));
		this.setTenantId(entity.getUnitId().getTenantId());
		this.setLastUpdatedUser(entity.getLastUpdatedUser());
	}
	
}
