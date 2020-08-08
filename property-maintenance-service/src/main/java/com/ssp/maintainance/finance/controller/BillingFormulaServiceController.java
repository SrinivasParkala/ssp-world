package com.ssp.maintainance.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.finance.beans.BillingFormulaEntity;
import com.ssp.maintainance.finance.services.impl.BillingFormulaImpl;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.controller.AbstractController;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

@RestController
public class BillingFormulaServiceController extends AbstractController {
	private BillingFormulaImpl billingFormulaImpl;
	
	@Autowired
	public void setBillingFormulaServices(BillingFormulaImpl billingFormulaImpl) {
		this.billingFormulaImpl = billingFormulaImpl;
	}

	@RequestMapping(value = "/ssp/fin/billingFormula/{formulaId}/{langId}", method = RequestMethod.GET)
	public ResponseEntity<BillingFormulaEntity> getBillingFormulaById(@PathVariable("formulaId") int billingId, @PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getBillingFormulaById");
		
		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		RefEntityIntKey refEntityIntKey = new RefEntityIntKey(tenantid, billingId, langId);
		
		BillingFormulaEntity billingFormulaEntity = billingFormulaImpl.getBillingFormulaById(control, refEntityIntKey);
		
		control.addToControl(MasterControl.CURRENT_OBJECT, billingFormulaEntity);
		postExecute(control, "getBillingFormulaById");
		
		return new ResponseEntity<>(billingFormulaEntity, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ssp/fin/billingFormula", method = RequestMethod.POST)
	public ResponseEntity<String> createBillingFormula(@RequestBody BillingFormulaEntity unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		
		preExecute(control, "createBillingFormula");

		billingFormulaImpl.saveOrUpdateBillingFormula(control, unit);

		postExecute(control, "createBillingFormula");
		return new ResponseEntity<>("BillingFormula is created successfully",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ssp/fin/billingFormula", method = RequestMethod.PUT)
	public ResponseEntity<String> updateBillingFormula(@RequestBody BillingFormulaEntity unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.PUT);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
	
		preExecute(control, "updateBillingFormula");

		billingFormulaImpl.saveOrUpdateBillingFormula(control, unit);

		preExecute(control, "updateBillingFormula");
		return new ResponseEntity<>("BillingFormula is updated successsfully",HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/fin/billingFormula/{formulaId}/{langId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteBillingFormula(@PathVariable("billingId") int billingId, @PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.DELETE);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "deleteBillingFormula");

		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
	
		RefEntityIntKey refEntityIntKey = new RefEntityIntKey(tenantid, billingId, langId);
		billingFormulaImpl.deleteBillingFormulaEntity(control, refEntityIntKey);

		preExecute(control, "deleteBillingFormula");
		return new ResponseEntity<>("BillingFormula is deleted successsfully",HttpStatus.OK);
	}
}
