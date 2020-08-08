package com.ssp.maintainance.finance.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.util.Pair;

import com.ssp.maintainance.beans.PropertyBean;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.finance.services.impl.BillingCategoryImpl;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.keys.EntityIntKey;
import com.ssp.maintainance.util.DateUtil;
import com.ssp.maintainance.util.SspConstants;

public class ComputeInvoiceBilling {
	
	public List<ComputeInvoiceBean> computeInvoiceAmount(PropertyBean propertyBean, BillingCategoryBean billingCategoryBean, BillingCategoryImpl billingCategoryImpl, MasterControl control) throws InvalidInputException{
		float cost = 0f;
		ComputeInvoiceBean invoiceBean  = new ComputeInvoiceBean();
		invoiceBean.setAssetNo(propertyBean.getKeyId());
		
		if( billingCategoryBean.getFormulaId() > 0 ){
			cost = applyFormula(invoiceBean, propertyBean, billingCategoryBean, billingCategoryImpl, control);
		}
		else{
			cost = applyNonFormula(invoiceBean, propertyBean, billingCategoryBean, 1);
		}
		
		invoiceBean.setAmount(cost);
		String formula = invoiceBean.getFormula();
		if(formula.endsWith("+")){
			int len = formula.length();
			formula = formula.substring(0, len-1);
			invoiceBean.setFormula(formula);
		}
		
		List<ComputeInvoiceBean> invoiceBeans = applyTermAndInstallement(billingCategoryBean, invoiceBean, cost);
		
		return invoiceBeans;
	}
	
	private List<ComputeInvoiceBean> applyTermAndInstallement(BillingCategoryBean billingCategoryBean, ComputeInvoiceBean invoiceBean, float cost){
		List<ComputeInvoiceBean> invoiceBeans = new ArrayList<ComputeInvoiceBean>();
		
		int terms = billingCategoryBean.getInstallamentTerms();
		Date startDate = billingCategoryBean.getStartDate();
		
		if( startDate == null ){
			startDate = DateUtil.getCurrentDate();
		}
		
		if( terms > 1 ){
			int offSetMonth = 1;
			if( billingCategoryBean.getPeriodicType() == SspConstants.INSTALLEMENT_QUATERLY ){
				offSetMonth = 4;
			}
			else if( billingCategoryBean.getPeriodicType() == SspConstants.INSTALLEMENT_HALFYEARLY ){
				offSetMonth = 6;
			}
			else if( billingCategoryBean.getPeriodicType() == SspConstants.INSTALLEMENT_YEARLY ){
				offSetMonth = 12;	
			}
			
			ComputeInvoiceBean computeInvoiceBean = null;
			for( int i = 0 ; i < terms ; i++ ){
				startDate = DateUtil.addMonthsOffSet(startDate, offSetMonth);
				invoiceBean.setBillingDate(startDate);
				computeInvoiceBean = new ComputeInvoiceBean(invoiceBean.getAssetNo(), invoiceBean.getFormula(), invoiceBean.getAmount(), startDate);
				computeInvoiceBean.setTotalTerms(terms);
				computeInvoiceBean.setTerm(i+1);
				invoiceBeans.add(computeInvoiceBean);
			}
		}
		else{
			invoiceBean.setBillingDate(startDate);
			invoiceBeans.add(invoiceBean);
		}
		
		return invoiceBeans;
	}
	
	private float applyFormula(ComputeInvoiceBean invoiceBean,PropertyBean propertyBean, BillingCategoryBean billingCategoryBean, BillingCategoryImpl billingCategoryImpl, MasterControl control) throws InvalidInputException{
		float cost = 0f;
		
		BillingFormulaBean billingFormulaBean = new BillingFormulaBean();
		billingFormulaBean.buidCharge(billingCategoryBean.getFormulaObject());
		
		List<Pair<Integer, Integer>> formulaItems = billingFormulaBean.getChargeList();
		
		BillingCategoryBean billingCategoryBeanChild = null;
		EntityIntKey entityIntKey = null;
		
		for(Pair<Integer, Integer> item : formulaItems){
			entityIntKey = new EntityIntKey(billingCategoryBean.getTenantId(), item.getFirst());
			billingCategoryBeanChild = billingCategoryImpl.getBillingCategoryById(control, entityIntKey);
			cost += applyNonFormula(invoiceBean, propertyBean, billingCategoryBeanChild,item.getSecond());
		}
		
		return cost;
	}
	
	private float applyNonFormula(ComputeInvoiceBean invoiceBean,PropertyBean propertyBean, BillingCategoryBean billingCategoryBean, int noOfTimes){
		float cost = 0f;
		StringBuilder formula = new StringBuilder();
		if( billingCategoryBean.getFixed() == SspConstants.COST_IS_FIXED ){
			formula = formula.append("[").append(billingCategoryBean.getBillingValue()).append("=").append(billingCategoryBean.getBillingCost()).append("]").
			append("*[NumberOfTime=").append(noOfTimes).append("]");
			
			cost = billingCategoryBean.getBillingCost()*noOfTimes;
		}
		else if( billingCategoryBean.getFixed() == SspConstants.COST_PER_SQFT ){
			formula = formula.append("[").append(billingCategoryBean.getBillingValue()).append("=").append(billingCategoryBean.getBillingCost()).append("]").
			append("*").append("[SQFT_FT=").append(propertyBean.getDimension()).append("]").
			append("*[NumberOfTime=").append(noOfTimes).append("]");
			
			cost = billingCategoryBean.getBillingCost()*propertyBean.getDimension()*noOfTimes;
		}
		else if( billingCategoryBean.getFixed() == SspConstants.COST_PER_TENANT ){
			formula = formula.append("[").append(billingCategoryBean.getBillingValue()).append("=").append(billingCategoryBean.getBillingCost()).append("]").
			append("*").append("[NO_OF_TENANT=").append(propertyBean.getTenantCount()).append("]").
			append("*[NumberOfTime=").append(noOfTimes).append("]");
			
			cost = billingCategoryBean.getBillingCost()*propertyBean.getTenantCount()*noOfTimes;
		}	
		
		if(invoiceBean.getFormula() == null ){
			invoiceBean.setFormula(formula.toString());
		}
		else{
			invoiceBean.setFormula(invoiceBean.getFormula()+"+"+formula.toString());
		}
		return cost;
	}
	
}
