package com.ssp.maintainance.beans;

import java.sql.Timestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.MasterRootEntity;
import com.ssp.maintainance.meta.keys.ResPropKey;
import com.ssp.maintainance.util.DateUtil;

@Entity
@Table(name = "t_res_prop_map")
public class ResPropMapEntity extends MasterRootEntity{

	@EmbeddedId
    private ResPropKey id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(
            name = "tenantId",
            referencedColumnName = "tenantId", insertable=false, updatable=false),
        @JoinColumn(
            name = "propId",
            referencedColumnName = "propId", insertable=false, updatable=false)
    })
    private PropertyEntity property;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(
            name = "tenantId",
            referencedColumnName = "tenantId", insertable=false, updatable=false),
        @JoinColumn(
            name = "keyId",
            referencedColumnName = "keyId", insertable=false, updatable=false)
    })
    private ResidentEntity resident;
 
    private int  ownershipType;
    
    private int tenantCount = 1;
	
	private Timestamp startDate;
	
	private Timestamp endDate;

	public int getTenantCount() {
		return tenantCount;
	}

	public void setTenantCount(int tenantCount) {
		this.tenantCount = tenantCount;
	}

	public ResPropKey getId() {
		return id;
	}

	public void setId(ResPropKey id) {
		this.id = id;
	}

	public PropertyEntity getProperty() {
		return property;
	}

	public void setProperty(PropertyEntity property) {
		this.property = property;
	}

	public ResidentEntity getResident() {
		return resident;
	}

	public void setResident(ResidentEntity resident) {
		this.resident = resident;
	}

	public int getOwnershipType() {
		return ownershipType;
	}

	public void setOwnershipType(int ownershipType) {
		this.ownershipType = ownershipType;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	@Override
	public void validateEntity(MasterControl masterControl) throws InvalidInputException {
		super.validateEntity(masterControl);
		
		if( this.getStartDate() == null ){
			this.setStartDate(DateUtil.getCurrentDateTime());
		}
		
		if( this.getTenantCount() == 0 ){
			this.setTenantCount(1);
		}
	}
}
