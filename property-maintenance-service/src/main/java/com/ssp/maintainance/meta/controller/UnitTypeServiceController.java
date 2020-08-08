package com.ssp.maintainance.meta.controller;

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
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.RefUnitType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.services.RefTypeService;

@RestController
public class UnitTypeServiceController extends AbstractController {

	private RefTypeService<RefUnitType> unitsServices;
	
	@Autowired
	public void setUnitsServices(RefTypeService<RefUnitType> unitsServices) {
		this.unitsServices = unitsServices;
	}

	@RequestMapping(value = "/ssp/meta/unitType/{langId}", method = RequestMethod.GET)
	public ResponseEntity<List<RefUnitType>> getUnits(@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		
		preExecute(control, "getUnits");
		
		List<RefUnitType> allRefType = unitsServices.getAllRefType(control);
		
		postExecute(control, "getUnits");
		return new ResponseEntity<>(allRefType, HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/meta/unitType", method = RequestMethod.POST)
	public ResponseEntity<String> createUnit(@RequestBody RefUnitType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "createUnit");

		unitsServices.saveOrUpdateRefType(control, unit);

		postExecute(control, "createUnit");
		return new ResponseEntity<>("Unit is created successfully",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ssp/meta/unitType", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUnit(@RequestBody RefUnitType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.PUT);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "updateUnit");

		unitsServices.saveOrUpdateRefType(control, unit);

		preExecute(control, "updateUnit");
		return new ResponseEntity<>("Unit is updated successsfully",HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/meta/unitType/{id}/{langId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUnit(@PathVariable("id") int id, @PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.DELETE);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "deleteUnit");

		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		RefEntityIntKey entId = new RefEntityIntKey(tenantid, id, langId);
		unitsServices.deleteRefType(control, entId);

		preExecute(control, "deleteUnit");
		return new ResponseEntity<>("Unit is deleted successsfully",HttpStatus.OK);
	}

}
