package com.ssp.maintainance.meta.keys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class ResidentChildEntityKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResidentChildEntityKey(){
		
	}
	
	public ResidentChildEntityKey(String tenantId, int keyId, int residentId) {
		this.tenantId = tenantId;
		this.keyId = keyId;
		this.residentId = residentId;
	}
	
	public ResidentChildEntityKey(String tenantId,  int residentId) {
		this.tenantId = tenantId;
		this.residentId = residentId;
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

	@Column(name = "residentId", nullable = false)
	private int residentId;

	public int getResidentId() {
		return residentId;
	}

	public void setResidentId(int residentId) {
		this.residentId = residentId;
	}
}
