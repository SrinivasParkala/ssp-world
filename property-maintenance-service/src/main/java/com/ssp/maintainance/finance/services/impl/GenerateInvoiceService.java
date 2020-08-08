package com.ssp.maintainance.finance.services.impl;

import java.util.List;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.finance.beans.GenerateInvoiceBean;
import com.ssp.maintainance.finance.beans.ComputeInvoiceBean;
import com.ssp.maintainance.meta.beans.MasterControl;

public interface GenerateInvoiceService {
	List<ComputeInvoiceBean> generateInvoices(MasterControl control, GenerateInvoiceBean unit) throws InvalidInputException ;
}
