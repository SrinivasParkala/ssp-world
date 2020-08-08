package com.ssp.maintainance.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.finance.beans.RefBillingCatType;
import com.ssp.maintainance.finance.services.impl.RefBillingCatImpl;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.controller.AbstractController;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

@RestController
public class BillingCatTypeServiceController extends AbstractController {

	private RefBillingCatImpl billingCatServices;

	@Autowired
	public void setBillingCatServices(RefBillingCatImpl billingCatServices) {
		this.billingCatServices = billingCatServices;
	}

	@RequestMapping(value = "/ssp/fin/billingCatType/{langId}", method = RequestMethod.GET)
	public ResponseEntity<List<RefBillingCatType>> getBillingCatType(@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "RefBillingCatType");
		
		List<RefBillingCatType> allRefType = billingCatServices.getAllRefType(control);
		
		postExecute(control, "RefBillingCatType");
		return new ResponseEntity<>(allRefType, HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/fin/billingCatType", method = RequestMethod.POST)
	public ResponseEntity<String> createBillingCatType(@RequestBody RefBillingCatType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "createBillingCatType");

		billingCatServices.saveOrUpdateRefType(control, unit);

		postExecute(control, "createBillingCatType");
		return new ResponseEntity<>("Periodic Type is created successfully",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ssp/fin/billingCatType", method = RequestMethod.PUT)
	public ResponseEntity<String> updateBillingCatType(@RequestBody RefBillingCatType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.PUT);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "updateBillingCatType");

		billingCatServices.saveOrUpdateRefType(control, unit);

		preExecute(control, "updateBillingCatType");
		return new ResponseEntity<>("Periodic Type is updated successsfully",HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/fin/billingCatType/{id}/{langId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteBillingCatType(@PathVariable("id") int id,@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.DELETE);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "deleteBillingCatType");

		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		RefEntityIntKey entId = new RefEntityIntKey(tenantid, id, langId);
		billingCatServices.deleteRefType(control, entId);

		preExecute(control, "deleteBillingCatType");
		return new ResponseEntity<>("Periodic Type is deleted successsfully",HttpStatus.OK);
	}
	
}
