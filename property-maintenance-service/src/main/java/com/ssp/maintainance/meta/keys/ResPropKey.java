package com.ssp.maintainance.meta.keys;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ResPropKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "tenantId", nullable = false)
	private String tenantId;

	@Column(name = "keyId", nullable = false)
	private int keyId;
	
	@Column(name = "propId", nullable = false)
	private String propId;

	public ResPropKey(){
		
	}

	public ResPropKey(String tenantId, int keyId, String propId){
		this.tenantId = tenantId;
		this.keyId = keyId;
		this.propId = propId;
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

	public String getPropId() {
		return propId;
	}

	public void setPropId(String propId) {
		this.propId = propId;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        ResPropKey that = (ResPropKey) o;
        return Objects.equals(tenantId, that.tenantId) &&
               Objects.equals(keyId, that.keyId) && Objects.equals(propId, that.propId);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(tenantId, keyId,propId);
    }
}
