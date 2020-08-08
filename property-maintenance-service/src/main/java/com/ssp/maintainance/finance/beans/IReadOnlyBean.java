package com.ssp.maintainance.finance.beans;

import java.util.List;

public abstract class IReadOnlyBean {
	private String tenantId;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public abstract List<Object> getChildBeans();
}
