package com.ssp.maintainance.beans;

import java.sql.Timestamp;
import java.util.List;

import com.ssp.maintainance.meta.keys.EntityIntKey;
import com.ssp.maintainance.util.DateUtil;


public class ResidentBean extends IGenericBean<ResidentEntity>{
	
	private String firstName;
	
	private String lastName;
	
	private String middleName;
	
	private int nameTitleType;
	
	private String nameTitleValue;
	
	private List<AddressBean> address;
	
	private List<ContactBean> contacts;
	
	private List<PropertyBean> resProperty;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public int getNameTitleType() {
		return nameTitleType;
	}

	public void setNameTitleType(int nameTitleType) {
		this.nameTitleType = nameTitleType;
	}

	public String getNameTitleValue() {
		return nameTitleValue;
	}

	public void setNameTitleValue(String nameTitleValue) {
		this.nameTitleValue = nameTitleValue;
	}

	public List<AddressBean> getAddress() {
		return address;
	}

	public void setAddress(List<AddressBean> address) {
		this.address = address;
	}

	public List<ContactBean> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactBean> contacts) {
		this.contacts = contacts;
	}

	public List<PropertyBean> getResProperty() {
		return resProperty;
	}

	public void setResProperty(List<PropertyBean> resProperty) {
		this.resProperty = resProperty;
	}

	@Override
	public ResidentEntity convertToEntityObject() {
		ResidentEntity entity = new ResidentEntity();
		
		EntityIntKey id = null;
		if(this.getKeyId() == null){
			id = new EntityIntKey(this.getTenantId());
		}
		else{
			id = new EntityIntKey(this.getTenantId(), Integer.valueOf(this.getKeyId()));
		}
		
		entity.setResidentId(id);
		entity.setFirstName(this.getFirstName());
		entity.setMiddleName(this.getMiddleName());
		entity.setLastName(this.getLastName());
		entity.setNameTitleType(this.getNameTitleType());
		entity.setVersion(this.getVersion());
		entity.setLastUpdatedUser(this.getLastUpdatedUser());
	
		if( this.getLastUpdatedDate() == null ){
			entity.setLastUpdatedDate(DateUtil.getCurrentDateTime());
		}
		else{
			entity.setLastUpdatedDate(this.getLastUpdatedDate());
		}
		
		return entity;
	}

	@Override
	public void convertToBeanObject(ResidentEntity entity) {
		this.setLastUpdatedDate(entity.getLastUpdatedDate());
		this.setLastUpdatedUser(entity.getLastUpdatedUser());
		this.setFirstName(entity.getFirstName());
		this.setMiddleName(entity.getMiddleName());
		this.setLastName(entity.getLastName());
		this.setNameTitleType(entity.getNameTitleType());
		this.setVersion(entity.getVersion());
		this.setKeyId(String.valueOf(entity.getResidentId().getKeyId()));
		this.setTenantId(entity.getResidentId().getTenantId());
	}
	
}
