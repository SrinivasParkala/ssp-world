package com.ssp.maintainance.finance.beans;

import java.util.ArrayList;
import java.util.List;

import com.ssp.maintainance.beans.PropertyBean;

public class GenerateInvoiceBean extends IReadOnlyBean{

	private BillingCategoryBean billingCategoryBean;
	
	private List<PropertyBean>  propertyUnits;
	
	public BillingCategoryBean getBillingCategoryBean() {
		return billingCategoryBean;
	}

	public void setBillingCategoryBean(BillingCategoryBean billingCategoryBean) {
		this.billingCategoryBean = billingCategoryBean;
	}

	public List<PropertyBean> getPropertyUnit() {
		return propertyUnits;
	}

	public void setPropertyUnit(List<PropertyBean> propertyUnits) {
		this.propertyUnits = propertyUnits;
	}

	@Override
	public List<Object> getChildBeans() {
		List<Object> beans = new ArrayList<Object>();
		
		if( this.propertyUnits != null ){
			beans.add(propertyUnits);
		}
		
		if( this.billingCategoryBean != null ){
			beans.add(billingCategoryBean);
		}
		
		return beans;
	}
	
}
