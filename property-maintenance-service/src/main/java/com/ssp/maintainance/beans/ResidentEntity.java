package com.ssp.maintainance.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.MasterRootEntity;
import com.ssp.maintainance.meta.keys.EntityIntKey;

@Entity
@Table(name = "t_resident")
public class ResidentEntity extends MasterRootEntity{

	@EmbeddedId
	private EntityIntKey residentId;
	
	private String firstName;
	
	private String lastName;
	
	private String middleName;
	
	private int nameTitleType;
	
	@ElementCollection(targetClass=AddressEntity.class)
	@JoinColumns({
        @JoinColumn(
            name = "tenantId",
            referencedColumnName = "tenantId"),
        @JoinColumn(
            name = "residentId",
            referencedColumnName = "keyId")
    })
	private List<AddressEntity> addresses;
	
	@ElementCollection(targetClass=ContactEntity.class)
	@JoinColumns({
        @JoinColumn(
            name = "tenantId",
            referencedColumnName = "tenantId"),
        @JoinColumn(
            name = "residentId",
            referencedColumnName = "keyId")
    })
	private List<ContactEntity> contacts;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumns({
        @JoinColumn(
            name = "tenantId",
            referencedColumnName = "tenantId"),
        @JoinColumn(
            name = "keyId",
            referencedColumnName = "keyId")
    })
	private List<ResPropMapEntity> resPropMap;
	    
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

	public EntityIntKey getResidentId() {
		return residentId;
	}

	public void setResidentId(EntityIntKey residentId) {
		this.residentId = residentId;
	}

	
	public List<ResPropMapEntity> getResPropMap() {
		return resPropMap;
	}

	public void setResPropMap(List<ResPropMapEntity> resPropMap) {
		this.resPropMap = resPropMap;
	}

	@Override
	public void validateEntity(MasterControl masterControl) throws InvalidInputException {
		
	}
	
}
