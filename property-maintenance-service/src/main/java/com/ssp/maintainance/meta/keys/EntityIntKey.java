package com.ssp.maintainance.meta.keys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class EntityIntKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityIntKey(){
		
	}
	
	public EntityIntKey(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public EntityIntKey(String tenantId, int keyId) {
		this.tenantId = tenantId;
		this.keyId = keyId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public int getKeyId() {
		return keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

	@Column(name = "tenantId", nullable = false)
	private String tenantId;

	@Column(name = "keyId", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int keyId;

}
