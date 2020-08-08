package com.ssp.maintainance.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.finance.beans.GenerateInvoiceBean;
import com.ssp.maintainance.finance.beans.ComputeInvoiceBean;
import com.ssp.maintainance.finance.services.impl.GenerateInvoiceImpl;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.controller.AbstractController;

@RestController
public class GenerateInvoiceServiceController extends AbstractController {
	private GenerateInvoiceImpl generateInvoiceImpl;
	
	@Autowired
	public void setGenerateBilingServices(GenerateInvoiceImpl generateInvoiceImpl) {
		this.generateInvoiceImpl = generateInvoiceImpl;
	}

	@RequestMapping(value = "/ssp/fin/generateInvoice", method = RequestMethod.POST)
	public ResponseEntity<List<ComputeInvoiceBean>> createBillingCategory(@RequestBody GenerateInvoiceBean unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
		
		preExecute(control, "createBillingCategory");

		List<ComputeInvoiceBean> invoices = generateInvoiceImpl.generateInvoices(control, unit);

		postExecute(control, "createBillingCategory");
		return new ResponseEntity<>(invoices, HttpStatus.CREATED);
	}

}
