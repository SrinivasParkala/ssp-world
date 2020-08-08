package com.ssp.maintainance.meta.keys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EntityStringKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "tenantId", nullable = false)
    private String tenantId;

    @Column(name = "propId", nullable = false)
    private String propId;

    public EntityStringKey(){
    	
    }
    
    public EntityStringKey(String tenantId, String propId){
    	this.tenantId = tenantId;
    	this.propId = propId;
    }
    
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getKeyId() {
		return propId;
	}

	public void setKeyId(String propId) {
		this.propId = propId;
	}

}
