package com.ssp.maintainance.beans;

import java.sql.Timestamp;

import com.ssp.maintainance.meta.beans.MasterRootEntity;

public abstract class IGenericBean<T extends MasterRootEntity> {
	private String tenantId;

    private String keyId;
    
	private String lastUpdatedUser;
	
	private Timestamp lastUpdatedDate;
	
    private int version;

	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
    
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public abstract T convertToEntityObject();
		
	public abstract void convertToBeanObject(T entity);
	
}
