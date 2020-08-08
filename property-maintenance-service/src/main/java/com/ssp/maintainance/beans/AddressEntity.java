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
@Table(name = "t_address")
public class AddressEntity extends MasterRootEntity{

	@EmbeddedId
	private ResidentChildEntityKey addressId;
	
	private int  addressType;
	
	private String addressLine;
	
	private String street;
	
	private String district;
	
	private String state;
	
	private int postalCode;
	
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
	
	public ResidentChildEntityKey getAddressId() {
		return addressId;
	}

	public void setAddressId(ResidentChildEntityKey addressId) {
		this.addressId = addressId;
	}

	public int getAddressType() {
		return addressType;
	}

	public void setAddressType(int addressType) {
		this.addressType = addressType;
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

	@Override
	public void validateEntity(MasterControl masterControl) throws InvalidInputException {
		// TODO Auto-generated method stub
		
	}
}
