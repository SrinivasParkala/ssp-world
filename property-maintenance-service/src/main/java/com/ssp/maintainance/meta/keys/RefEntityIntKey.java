package com.ssp.maintainance.meta.keys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class RefEntityIntKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RefEntityIntKey(){
		
	}
	
	public RefEntityIntKey(String tenantId, int keyId,int langId) {
		this.tenantId = tenantId;
		this.keyId = keyId;
		this.langId = langId;
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
	
	@Column(name = "langId", nullable = false)
	private int langId;

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}
	
}
