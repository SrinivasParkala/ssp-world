package com.ssp.maintainance.finance.beans;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ssp.maintainance.meta.beans.MasterRootEntity;
import com.ssp.maintainance.meta.keys.EntityIntKey;

@Entity
@Table(name = "t_billinvoice")
public class BillingInvoiceEntity extends MasterRootEntity{
	
	@EmbeddedId
	private EntityIntKey invoiceId;
	
	//Default current Date
	private Date invoiceDate;
	private Date dueDate;
	private float amount;
	private String description;
	
	private int billingId;
	private int unitId;
	private int residentId;
	
}
