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
import com.ssp.maintainance.meta.beans.RefAddressType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.services.RefTypeService;

@RestController
public class AddressTypeServiceController extends AbstractController {

	private RefTypeService<RefAddressType> addressServices;

	@Autowired
	public void setOwnershipServices(RefTypeService<RefAddressType> addressServices) {
		this.addressServices = addressServices;
	}

	@RequestMapping(value = "/ssp/meta/addressStatusType/{langId}", method = RequestMethod.GET)
	public ResponseEntity<List<RefAddressType>> getAddressStatus(@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getAddressStatus");
		
		List<RefAddressType> allRefType = addressServices.getAllRefType(control);
		
		postExecute(control, "getAddressStatus");
		return new ResponseEntity<>(allRefType, HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/meta/addressStatusType", method = RequestMethod.POST)
	public ResponseEntity<String> createAddressStatus(@RequestBody RefAddressType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "createAddressStatus");

		addressServices.saveOrUpdateRefType(control, unit);

		postExecute(control, "createAddressStatus");
		return new ResponseEntity<>("Address Type is created successfully",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ssp/meta/addressStatusType", method = RequestMethod.PUT)
	public ResponseEntity<String> updateAddessStatus(@RequestBody RefAddressType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.PUT);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "updateAddessStatus");

		addressServices.saveOrUpdateRefType(control, unit);

		preExecute(control, "updateAddessStatus");
		return new ResponseEntity<>("Address Type is updated successsfully",HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/meta/addressStatusType/{id}/{langId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteAddressStatus(@PathVariable("id") int id,@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.DELETE);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "deleteAddressStatus");

		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		RefEntityIntKey entId = new RefEntityIntKey(tenantid, id, langId);
		addressServices.deleteRefType(control, entId);

		preExecute(control, "deleteAddressStatus");
		return new ResponseEntity<>("Address Type is deleted successsfully",HttpStatus.OK);
	}

}
