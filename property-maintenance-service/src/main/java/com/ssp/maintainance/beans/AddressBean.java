package com.ssp.maintainance.beans;

import java.sql.Timestamp;

import com.ssp.maintainance.meta.keys.EntityIntKey;
import com.ssp.maintainance.meta.keys.ResidentChildEntityKey;
import com.ssp.maintainance.util.DateUtil;
import com.ssp.maintainance.util.StringUtil;

public class AddressBean extends IGenericBean<AddressEntity>{

	private int residentId;
	
	private int  addressType;
	
	private String  addressValue;
	
	private String addressLine;
	
	private String street;
	
	private String district;
	
	private String state;
	
	private int postalCode;
	
	public int getAddressType() {
		return addressType;
	}

	public void setAddressType(int addressType) {
		this.addressType = addressType;
	}

	public String getAddressValue() {
		return addressValue;
	}

	public void setAddressValue(String addressValue) {
		this.addressValue = addressValue;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public int getResidentId() {
		return residentId;
	}

	public void setResidentId(int residentId) {
		this.residentId = residentId;
	}

	@Override
	public AddressEntity convertToEntityObject() {
		AddressEntity entity = new AddressEntity();
		
		ResidentChildEntityKey id = null;
		if( StringUtil.isStringNull(this.getKeyId()) ){
			id = new ResidentChildEntityKey(this.getTenantId(), Integer.valueOf(this.getResidentId()));
		}
		else{
			id = new ResidentChildEntityKey(this.getTenantId(), Integer.valueOf(this.getKeyId()), Integer.valueOf(this.getResidentId()));
		}
		
		entity.setAddressId(id);
		entity.setAddressLine(this.getAddressLine());
		entity.setAddressType(this.getAddressType());
		entity.setDistrict(this.getDistrict());
		entity.setPostalCode(this.getPostalCode());
		entity.setState(this.getState());
		entity.setStreet(this.getStreet());
		entity.setVersion(this.getVersion());
		entity.setLastUpdatedUser(this.getLastUpdatedUser());
		
		EntityIntKey residentId = new EntityIntKey(this.getTenantId(),  Integer.valueOf(this.getResidentId()));
		ResidentEntity residentEntity = new ResidentEntity();
		residentEntity.setResidentId(residentId);		
			
		if( this.getLastUpdatedDate() == null ){
			entity.setLastUpdatedDate(DateUtil.getCurrentDateTime());
		}
		else{
			entity.setLastUpdatedDate(this.getLastUpdatedDate());
		}
		
		return entity;
	}

	@Override
	public void convertToBeanObject(AddressEntity entity) {
		this.setAddressLine(entity.getAddressLine());
		this.setAddressType(entity.getAddressType());
		this.setDistrict(entity.getDistrict());
		this.setLastUpdatedDate(entity.getLastUpdatedDate());
		this.setLastUpdatedUser(entity.getLastUpdatedUser());
		this.setPostalCode(entity.getPostalCode());
		this.setState(entity.getState());
		this.setStreet(entity.getStreet());
		this.setTenantId(entity.getAddressId().getTenantId());
		this.setVersion(entity.getVersion());
		this.setKeyId(String.valueOf(entity.getAddressId().getKeyId()));
		this.setResidentId(entity.getAddressId().getResidentId());
	}

}
