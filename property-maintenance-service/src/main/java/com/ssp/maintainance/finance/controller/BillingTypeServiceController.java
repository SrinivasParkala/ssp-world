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
import com.ssp.maintainance.finance.beans.RefBillingType;
import com.ssp.maintainance.finance.services.impl.RefBillingImpl;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.controller.AbstractController;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

@RestController
public class BillingTypeServiceController extends AbstractController {

	private RefBillingImpl billingServices;

	@Autowired
	public void setBillingServices(RefBillingImpl billingServices) {
		this.billingServices = billingServices;
	}

	@RequestMapping(value = "/ssp/fin/billingType/{langId}", method = RequestMethod.GET)
	public ResponseEntity<List<RefBillingType>> getBillingType(@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getBillingType");
		
		List<RefBillingType> allRefType = billingServices.getAllRefType(control);
		
		postExecute(control, "getBillingType");
		return new ResponseEntity<>(allRefType, HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/fin/billingType", method = RequestMethod.POST)
	public ResponseEntity<String> createBillingType(@RequestBody RefBillingType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "createBillingType");

		billingServices.saveOrUpdateRefType(control, unit);

		postExecute(control, "createBillingType");
		return new ResponseEntity<>("Periodic Type is created successfully",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ssp/fin/billingType", method = RequestMethod.PUT)
	public ResponseEntity<String> updateBillingType(@RequestBody RefBillingType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.PUT);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "updateBillingType");

		billingServices.saveOrUpdateRefType(control, unit);

		preExecute(control, "updateBillingType");
		return new ResponseEntity<>("Periodic Type is updated successsfully",HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/fin/billingType/{id}/{langId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteBillingType(@PathVariable("id") int id,@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.DELETE);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "deleteBillingType");

		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		RefEntityIntKey entId = new RefEntityIntKey(tenantid, id, langId);
		billingServices.deleteRefType(control, entId);

		preExecute(control, "deleteBillingType");
		return new ResponseEntity<>("Periodic Type is deleted successsfully",HttpStatus.OK);
	}
	
}
