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
import com.ssp.maintainance.meta.beans.RefUnitSpecType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.services.RefTypeService;

@RestController
public class UnitSpecTypeServiceController extends AbstractController {

	private RefTypeService<RefUnitSpecType> unitsSpecServices;

	@Autowired
	public void setUnitSpecServices(RefTypeService<RefUnitSpecType> UnitsSpecServices) {
		this.unitsSpecServices = UnitsSpecServices;
	}

	@RequestMapping(value = "/ssp/meta/unitSpecType/{langId}", method = RequestMethod.GET)
	public ResponseEntity<List<RefUnitSpecType>> getUnitSpecs(@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getUnitSpecs");
		
		List<RefUnitSpecType> allRefType = unitsSpecServices.getAllRefType(control);
		
		postExecute(control, "getUnitSpecs");
		return new ResponseEntity<>(allRefType, HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/meta/unitSpecType", method = RequestMethod.POST)
	public ResponseEntity<String> createUnitSpec(@RequestBody RefUnitSpecType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "createUnitSpec");

		unitsSpecServices.saveOrUpdateRefType(control, unit);

		postExecute(control, "createUnitSpec");
		return new ResponseEntity<>("UnitSpec is created successfully",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ssp/meta/unitSpecType", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUnitSpec(@RequestBody RefUnitSpecType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.PUT);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "updateUnitSpec");

		unitsSpecServices.saveOrUpdateRefType(control, unit);

		preExecute(control, "updateUnitSpec");
		return new ResponseEntity<>("UnitSpec is updated successsfully",HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/meta/unitSpecType/{id}/{langId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUnitSpec(@PathVariable("id") int id, @PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.DELETE);
		control.addToControl(MasterControl.LANG_ID, langId);
		
		preExecute(control, "deleteUnitSpec");

		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		RefEntityIntKey entId = new RefEntityIntKey(tenantid, id, langId);
		unitsSpecServices.deleteRefType(control, entId);

		preExecute(control, "deleteUnitSpec");
		return new ResponseEntity<>("UnitSpec is deleted successsfully",HttpStatus.OK);
	}

}
