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
import com.ssp.maintainance.meta.beans.RefOwnershipType;
import com.ssp.maintainance.meta.beans.RefPropertyStatusType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.services.RefTypeService;

@RestController
public class OwnershipTypeServiceController extends AbstractController {

	private RefTypeService<RefOwnershipType> ownershipServices;

	@Autowired
	public void setOwnershipServices(RefTypeService<RefOwnershipType> ownershipServices) {
		this.ownershipServices = ownershipServices;
	}

	@RequestMapping(value = "/ssp/meta/ownershipStatusType/{langId}", method = RequestMethod.GET)
	public ResponseEntity<List<RefOwnershipType>> getOwnershipStatus(@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getOwnershipStatus");
		
		List<RefOwnershipType> allRefType = ownershipServices.getAllRefType(control);
		
		postExecute(control, "getOwnershipStatus");
		return new ResponseEntity<>(allRefType, HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/meta/ownershipStatusType", method = RequestMethod.POST)
	public ResponseEntity<String> createOwnershipStatus(@RequestBody RefOwnershipType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "createOwnershipStatus");

		ownershipServices.saveOrUpdateRefType(control, unit);

		postExecute(control, "createOwnershipStatus");
		return new ResponseEntity<>("Ownership Type is created successfully",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ssp/meta/ownershipStatusType", method = RequestMethod.PUT)
	public ResponseEntity<String> updateOwnershipStatus(@RequestBody RefPropertyStatusType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.PUT);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "updateOwnershipStatus");


		preExecute(control, "updateOwnershipStatus");
		return new ResponseEntity<>("Ownership Type is updated successsfully",HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/meta/ownershipStatusType/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteOwnershipStatus(@PathVariable("id") int id,@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.DELETE);
		control.addToControl(MasterControl.LANG_ID, langId);
		
		preExecute(control, "deleteOwnershipStatus");

		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		RefEntityIntKey entId = new RefEntityIntKey(tenantid, id, langId);
		ownershipServices.deleteRefType(control, entId);

		preExecute(control, "deleteOwnershipStatus");
		return new ResponseEntity<>("Ownership Type is deleted successsfully",HttpStatus.OK);
	}

}
