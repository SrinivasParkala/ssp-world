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
import com.ssp.maintainance.finance.beans.RefPeriodicType;
import com.ssp.maintainance.finance.services.impl.RefPeriodicImpl;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.controller.AbstractController;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

@RestController
public class PeriodicTypeServiceController extends AbstractController {

	private RefPeriodicImpl periodicServices;

	@Autowired
	public void setPeriodicServices(RefPeriodicImpl periodicServices) {
		this.periodicServices = periodicServices;
	}

	@RequestMapping(value = "/ssp/fin/periodicType/{langId}", method = RequestMethod.GET)
	public ResponseEntity<List<RefPeriodicType>> getPeriodicType(@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getPeriodicType");
		
		List<RefPeriodicType> allRefType = periodicServices.getAllRefType(control);
		
		postExecute(control, "getPeriodicType");
		return new ResponseEntity<>(allRefType, HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/fin/periodicType", method = RequestMethod.POST)
	public ResponseEntity<String> createPeriodicType(@RequestBody RefPeriodicType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "createPeriodicType");

		periodicServices.saveOrUpdateRefType(control, unit);

		postExecute(control, "createPeriodicType");
		return new ResponseEntity<>("Periodic Type is created successfully",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ssp/fin/periodicType", method = RequestMethod.PUT)
	public ResponseEntity<String> updatePeriodicType(@RequestBody RefPeriodicType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.PUT);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "updatePeriodicType");

		periodicServices.saveOrUpdateRefType(control, unit);

		preExecute(control, "updatePeriodicType");
		return new ResponseEntity<>("Periodic Type is updated successsfully",HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/fin/periodicType/{id}/{langId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletePeriodicType(@PathVariable("id") int id,@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.DELETE);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "deletePeriodicType");

		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		RefEntityIntKey entId = new RefEntityIntKey(tenantid, id, langId);
		periodicServices.deleteRefType(control, entId);

		preExecute(control, "deletePeriodicType");
		return new ResponseEntity<>("Periodic Type is deleted successsfully",HttpStatus.OK);
	}
	
}
