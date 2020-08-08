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
import com.ssp.maintainance.meta.beans.RefContactType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.services.RefTypeService;

@RestController
public class ContactTypeServiceController extends AbstractController {

	private RefTypeService<RefContactType> contactServices;

	@Autowired
	public void setOwnershipServices(RefTypeService<RefContactType> contactServices) {
		this.contactServices = contactServices;
	}

	@RequestMapping(value = "/ssp/meta/contactStatusType/{langId}", method = RequestMethod.GET)
	public ResponseEntity<List<RefContactType>> getContactStatus(@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getContactStatus");
		
		List<RefContactType> allRefType = contactServices.getAllRefType(control);
		
		postExecute(control, "getContactStatus");
		return new ResponseEntity<>(allRefType, HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/meta/contacStatusType", method = RequestMethod.POST)
	public ResponseEntity<String> createContactStatus(@RequestBody RefContactType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "createContactStatus");

		contactServices.saveOrUpdateRefType(control, unit);

		postExecute(control, "createContactStatus");
		return new ResponseEntity<>("Contact Type is created successfully",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ssp/meta/contactStatusType", method = RequestMethod.PUT)
	public ResponseEntity<String> updateContactStatus(@RequestBody RefContactType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.PUT);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "updateContactStatus");

		contactServices.saveOrUpdateRefType(control, unit);

		preExecute(control, "updateContactStatus");
		return new ResponseEntity<>("Contact Type is updated successsfully",HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/meta/contactStatusType/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteContactStatus(@PathVariable("id") int id,@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.DELETE);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "deleteContactStatus");

		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		RefEntityIntKey entId = new RefEntityIntKey(tenantid, id, langId);
		contactServices.deleteRefType(control, entId);

		preExecute(control, "deleteContactStatus");
		return new ResponseEntity<>("Contact Type is deleted successsfully",HttpStatus.OK);
	}

}
