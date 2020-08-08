package com.ssp.maintainance.beans;

import java.sql.Timestamp;

import com.ssp.maintainance.meta.keys.EntityStringKey;
import com.ssp.maintainance.util.DateUtil;


public class PropertyBean extends IGenericBean<PropertyEntity>{
	private int unitType;
	
	private int statusType;
	
	private String unitValue;
	
	private String unitSpecification;
	
	private String statusValue;
	
	private float value;
	
	private Timestamp startDate;
	
	private Timestamp endDate;
	
	private int tenantCount;
	
	private boolean isSingleOwner;
	
	private int	noOfOwners;
	
	private int  ownershipType;
	
	private String  ownershipValue;
	
	private float dimension;

	public float getDimension() {
		return dimension;
	}

	public void setDimension(float dimension) {
		this.dimension = dimension;
	}
	
	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public int getTenantCount() {
		return tenantCount;
	}

	public void setTenantCount(int tenantCount) {
		this.tenantCount = tenantCount;
	}

	public int getOwnershipType() {
		return ownershipType;
	}

	public void setOwnershipType(int ownershipType) {
		this.ownershipType = ownershipType;
	}

	public String getOwnershipValue() {
		return ownershipValue;
	}

	public void setOwnershipValue(String ownershipValue) {
		this.ownershipValue = ownershipValue;
	}
	
	public int getUnitType() {
		return unitType;
	}

	public void setUnitType(int unitType) {
		this.unitType = unitType;
	}

	public int getStatusType() {
		return statusType;
	}

	public void setStatusType(int statusType) {
		this.statusType = statusType;
	}

	public String getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	
	public boolean isSingleOwner() {
		return isSingleOwner;
	}

	public void setSingleOwner(boolean isSingleOwner) {
		this.isSingleOwner = isSingleOwner;
	}

	public int getNoOfOwners() {
		return noOfOwners;
	}

	public void setNoOfOwners(int noOfOwners) {
		this.noOfOwners = noOfOwners;
	}

	@Override
	public PropertyEntity convertToEntityObject() {
		PropertyEntity entity = new PropertyEntity();
		
		EntityStringKey id = new EntityStringKey(this.getTenantId(), this.getKeyId());
		
		entity.setUnitId(id);
		entity.setLastUpdatedDate(this.getLastUpdatedDate());
		entity.setLastUpdatedUser(this.getLastUpdatedUser());
		entity.setStartDate(this.getStartDate());
		entity.setStatusType(this.getStatusType());
		entity.setSingleOwner(this.isSingleOwner());
		entity.setNoOfOwners(this.getNoOfOwners());
		entity.setUnitType(this.getUnitType());
		entity.setValue(this.getValue());
		entity.setVersion(this.getVersion());
		
		if( this.getStartDate() == null ){
			entity.setStartDate(DateUtil.getCurrentDateTime());
		}
		else{
			entity.setStartDate(this.getStartDate());
		}
		
		if( this.getLastUpdatedDate() == null ){
			entity.setLastUpdatedDate(DateUtil.getCurrentDateTime());
		}
		else{
			entity.setLastUpdatedDate(this.getLastUpdatedDate());
		}
		
		return entity;
	}

	public String getUnitSpecification() {
		return unitSpecification;
	}

	public void setUnitSpecification(String unitSpecification) {
		this.unitSpecification = unitSpecification;
	}

	@Override
	public void convertToBeanObject(PropertyEntity entity) {
		this.setKeyId(entity.getUnitId().getKeyId());
		this.setTenantId(entity.getUnitId().getTenantId());
		
		this.setLastUpdatedDate(entity.getLastUpdatedDate());
		this.setLastUpdatedUser(entity.getLastUpdatedUser());
		this.setStartDate(entity.getStartDate());
		this.setStatusType(entity.getStatusType());
		this.setUnitType(entity.getUnitType());
		this.setValue(entity.getValue());
		this.setVersion(entity.getVersion());
		this.setSingleOwner(entity.isSingleOwner());
		this.setNoOfOwners(entity.getNoOfOwners());
		this.setDimension(entity.getDimension());
	}

}
