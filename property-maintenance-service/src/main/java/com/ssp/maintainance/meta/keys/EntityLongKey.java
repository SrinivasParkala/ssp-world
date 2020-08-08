package com.ssp.maintainance.meta.keys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EntityLongKey implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public long getKeyId() {
		return keyId;
	}

	public void setKeyId(long keyId) {
		this.keyId = keyId;
	}


	@Column(name = "tenantId", nullable = false)
    private String tenantId;

    @Column(name = "keyId", nullable = false)
    private long keyId;

 
}
