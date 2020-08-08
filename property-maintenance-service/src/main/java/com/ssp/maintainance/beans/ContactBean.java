package com.ssp.maintainance.beans;

import java.sql.Timestamp;

import com.ssp.maintainance.meta.keys.EntityIntKey;
import com.ssp.maintainance.meta.keys.ResidentChildEntityKey;
import com.ssp.maintainance.util.DateUtil;

public class ContactBean extends IGenericBean<ContactEntity>{

	public int getResidentId() {
		return residentId;
	}

	public void setResidentId(int residentId) {
		this.residentId = residentId;
	}

	public int getContactType() {
		return contactType;
	}

	public void setContactType(int contactType) {
		this.contactType = contactType;
	}

	public String getContactValue() {
		return contactValue;
	}

	public void setContactValue(String contactValue) {
		this.contactValue = contactValue;
	}

	public String getContactDataValue() {
		return contactDataValue;
	}

	public void setContactDataValue(String contactDataValue) {
		this.contactDataValue = contactDataValue;
	}

	private int residentId;
	
	private int  contactType;
	
	private String  contactValue;
	
	private String contactDataValue;
	
	@Override
	public ContactEntity convertToEntityObject() {
		ContactEntity entity = new ContactEntity();
		
		ResidentChildEntityKey id = new ResidentChildEntityKey(this.getTenantId(), Integer.valueOf(this.getKeyId()), Integer.valueOf(this.getResidentId()));
		
		entity.setContactId(id);
		entity.setContactDataValue(this.getContactDataValue());
		entity.setContactType(this.getContactType());
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
	public void convertToBeanObject(ContactEntity entity) {
		this.setTenantId(entity.getContactId().getTenantId());
		this.setContactDataValue(entity.getContactDataValue());
		this.setContactType(entity.getContactType());
		this.setLastUpdatedDate(entity.getLastUpdatedDate());
		this.setLastUpdatedUser(entity.getLastUpdatedUser());
		
		this.setVersion(entity.getVersion());
		this.setKeyId(String.valueOf(entity.getContactId().getKeyId()));
		this.setResidentId(entity.getContactId().getResidentId());
		
	}

}
