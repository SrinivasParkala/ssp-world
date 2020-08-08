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
import com.ssp.maintainance.meta.beans.RefPropertyStatusType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.services.RefTypeService;

@RestController
public class PropertyStatusTypeServiceController extends AbstractController {

	private RefTypeService<RefPropertyStatusType> propertyStautsServices;

	@Autowired
	public void setPropertyServices(RefTypeService<RefPropertyStatusType> propertyStautsServices) {
		this.propertyStautsServices = propertyStautsServices;
	}

	@RequestMapping(value = "/ssp/meta/propertyStatusType/{langId}", method = RequestMethod.GET)
	public ResponseEntity<List<RefPropertyStatusType>> getPropertyStatus(@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getPropertyStatus");
		
		List<RefPropertyStatusType> allRefType = propertyStautsServices.getAllRefType(control);
		
		postExecute(control, "getPropertyStatus");
		return new ResponseEntity<>(allRefType, HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/meta/propertyStatusType", method = RequestMethod.POST)
	public ResponseEntity<String> createPropertyStatus(@RequestBody RefPropertyStatusType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "createPropertyStatus");

		propertyStautsServices.saveOrUpdateRefType(control, unit);

		postExecute(control, "createPropertyStatus");
		return new ResponseEntity<>("Property Type is created successfully",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ssp/meta/propertyStatusType", method = RequestMethod.PUT)
	public ResponseEntity<String> updatePropertyStatus(@RequestBody RefPropertyStatusType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.PUT);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "updatePropertyStatus");

		propertyStautsServices.saveOrUpdateRefType(control, unit);

		preExecute(control, "updatePropertyStatus");
		return new ResponseEntity<>("Property Type is updated successsfully",HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/meta/propertyStatusType/{id}/{langId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletePropertyStatus(@PathVariable("id") int id,@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.DELETE);
		control.addToControl(MasterControl.LANG_ID, langId);
		
		preExecute(control, "deletePropertyStatus");

		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		RefEntityIntKey entId = new RefEntityIntKey(tenantid, id, langId);
		propertyStautsServices.deleteRefType(control, entId);

		preExecute(control, "deletePropertyStatus");
		return new ResponseEntity<>("Property Type is deleted successsfully",HttpStatus.OK);
	}

}
