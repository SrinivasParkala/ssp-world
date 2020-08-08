package com.ssp.maintainance.finance.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssp.maintainance.beans.PropertyBean;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.finance.beans.BillingCategoryBean;
import com.ssp.maintainance.finance.beans.ComputeInvoiceBilling;
import com.ssp.maintainance.finance.beans.GenerateInvoiceBean;
import com.ssp.maintainance.finance.beans.ComputeInvoiceBean;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.keys.EntityIntKey;
import com.ssp.maintainance.meta.keys.EntityStringKey;
import com.ssp.maintainance.meta.services.AbstractService;
import com.ssp.maintainance.services.MessageByLocaleService;
import com.ssp.maintainance.services.rdb.impl.PropertyEntityServiceImpl;
import com.ssp.maintainance.util.MessageConts;
import com.ssp.maintainance.util.SspConstants;

@Service
public class GenerateInvoiceImpl extends AbstractService implements GenerateInvoiceService {

	@Autowired
	private BillingCategoryImpl billingCategoryImpl;
	
	@Autowired
	private PropertyEntityServiceImpl propertyEntityServiceImpl;
	
	@Override
	public List<ComputeInvoiceBean> generateInvoices(MasterControl control, GenerateInvoiceBean unit) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
		preExecute(control, "generateInvoices");
		
		int count = 0;
		ComputeInvoiceBilling computeInvoiceBilling = new ComputeInvoiceBilling();
		List<PropertyBean> propertyUnits = unit.getPropertyUnit();
		List<ComputeInvoiceBean> invoices = new ArrayList<ComputeInvoiceBean>(propertyUnits.size());
		for(PropertyBean propertyUnit : propertyUnits){
			count++;
			List<ComputeInvoiceBean> invoice = computeInvoiceBilling.computeInvoiceAmount(propertyUnit , unit.getBillingCategoryBean(), billingCategoryImpl, control);
			invoices.addAll(invoice);
		}
		
		postExecute(control, "generateInvoices");
		return invoices;
	}

	@Override
	public void validateObject(MasterControl masterControl)
			throws InvalidInputException {
		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		
		Object bean = masterControl.getFromControl(MasterControl.CURRENT_BEAN);
		if( bean != null ){
			GenerateInvoiceBean unit = (GenerateInvoiceBean)bean;
			Object obj = masterControl.getFromControl(MasterControl.LANG_ID);
			int langId = -1;
			
			if( obj != null){
				langId = (Integer)obj;
			}
			else{
				langId = SspConstants.DEFAULT_LANG;
			}	
			
			BillingCategoryBean billingCategoryId = unit.getBillingCategoryBean();
			List<PropertyBean> propertyUnitIds = unit.getPropertyUnit();
			
			if( propertyUnitIds == null || billingCategoryId == null){
				String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_005.toString());
				throw new InvalidInputException(message);
			}
			
			EntityIntKey id = new EntityIntKey(billingCategoryId.getTenantId(), Integer.valueOf(billingCategoryId.getKeyId()));
			BillingCategoryBean billingCategory = billingCategoryImpl.getBillingCategoryById(masterControl, id);
			
			List<PropertyBean> propertyUnits = new ArrayList<PropertyBean>();
			PropertyBean propertyUnit = null;
			EntityStringKey entityStringKey = null;
			for(PropertyBean propertyUnitId : propertyUnitIds){
				entityStringKey = new EntityStringKey(propertyUnitId.getTenantId(), propertyUnitId.getKeyId());
				propertyUnit = propertyEntityServiceImpl.gePropertyEntityId(masterControl, entityStringKey, langId);
				
				propertyUnits.add(propertyUnit);
			}//for
			
			unit.setPropertyUnit(propertyUnits);
			unit.setBillingCategoryBean(billingCategory);
		}
		
	}

	@Override
	public void postTxnAction(MasterControl masterControl) {
		// TODO Auto-generated method stub
		
	}
}
