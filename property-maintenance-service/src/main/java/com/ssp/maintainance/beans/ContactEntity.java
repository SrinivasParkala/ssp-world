package com.ssp.maintainance.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.MasterRootEntity;
import com.ssp.maintainance.meta.keys.ResidentChildEntityKey;

@Entity
@Table(name = "t_contact")
public class ContactEntity extends MasterRootEntity{

	@EmbeddedId
	private ResidentChildEntityKey contactId;
	

	private int  contactType;
	
	private String contactDataValue;

	@ManyToOne
    @JoinColumns({
        @JoinColumn(
            name = "tenantId",
            referencedColumnName = "tenantId", insertable = false, updatable = false),
        @JoinColumn(
            name = "residentId",
            referencedColumnName = "keyId", insertable = false, updatable = false)
    })
	private ResidentEntity residentEntity;
	
	public ResidentChildEntityKey getContactId() {
		return contactId;
	}

	public void setContactId(ResidentChildEntityKey contactId) {
		this.contactId = contactId;
	}

	public int getContactType() {
		return contactType;
	}

	public void setContactType(int contactType) {
		this.contactType = contactType;
	}

	public String getContactDataValue() {
		return contactDataValue;
	}

	public void setContactDataValue(String contactDataValue) {
		this.contactDataValue = contactDataValue;
	}

	@Override
	public void validateEntity(MasterControl masterControl) throws InvalidInputException {
		// TODO Auto-generated method stub
		
	}
}
