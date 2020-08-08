package com.ssp.maintainance.meta.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ssp.maintainance.meta.keys.RefEntityIntKey;

@Entity
@Table(name = "r_prop_tp")
public class RefPropertyStatusType extends RefMasterRootEntity<RefEntityIntKey>{
	@EmbeddedId
	private RefEntityIntKey unitId;

	public RefEntityIntKey getUnitId() {
		return unitId;
	}

	public void setUnitId(RefEntityIntKey unitId) {
		this.unitId = unitId;
	}
	
	
}
