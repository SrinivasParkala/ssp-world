package com.ssp.maintainance.finance.beans;

import java.util.Date;

public class ComputeInvoiceBean {
	private String assetNo;
	private String formula;
	private float  amount;
	private Date billingDate;
	private int term;
	
	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public int getTotalTerms() {
		return totalTerms;
	}

	public void setTotalTerms(int totalTerms) {
		this.totalTerms = totalTerms;
	}
	private int totalTerms;
	
	public ComputeInvoiceBean(){
		this.term = 1;
		this.totalTerms = 1;
	}
	
	public ComputeInvoiceBean(String assetNo, String formula, float  amount, Date billingDate){
		this.assetNo = assetNo;
		this.formula = formula;
		this.amount = amount;
		this.billingDate = billingDate;
		this.term = 1;
		this.totalTerms = 1;
	}
	
	public Date getBillingDate() {
		return billingDate;
	}
	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}
	public String getAssetNo() {
		return assetNo;
	}
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
}
