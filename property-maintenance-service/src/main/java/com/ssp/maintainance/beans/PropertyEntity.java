package com.ssp.maintainance.beans;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.MasterRootEntity;
import com.ssp.maintainance.meta.keys.EntityStringKey;
import com.ssp.maintainance.util.DateUtil;

@Entity
@Table(name = "t_property")
public class PropertyEntity extends MasterRootEntity{

	@EmbeddedId
	private EntityStringKey unitId;
	
	private int unitType;
	
	private int statusType;
	
	private float value;
	
	private Timestamp startDate;
	
	private boolean isSingleOwner = true;
	
	private int	noOfOwners = 1;
	
	private float dimension;

	public float getDimension() {
		return dimension;
	}

	public void setDimension(float dimension) {
		this.dimension = dimension;
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

	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumns({
        @JoinColumn(
            name = "tenantId",
            referencedColumnName = "tenantId"),
        @JoinColumn(
            name = "propId",
            referencedColumnName = "propId")
    })
	private List<ResPropMapEntity> resdidentList;
	
	public EntityStringKey getUnitId() {
		return unitId;
	}

	public void setUnitId(EntityStringKey unitId) {
		this.unitId = unitId;
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

	@Override
	public void validateEntity(MasterControl masterControl) throws InvalidInputException {
		super.validateEntity(masterControl);
		
		if( this.getStartDate() == null ){
			this.setStartDate(DateUtil.getCurrentDateTime());
		}
		
		if( this.getNoOfOwners() == 0 ){
			this.setNoOfOwners(1);
		}
		
	}
	
}
