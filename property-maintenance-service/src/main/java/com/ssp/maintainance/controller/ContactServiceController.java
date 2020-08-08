package com.ssp.maintainance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssp.maintainance.beans.ContactBean;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.controller.AbstractController;
import com.ssp.maintainance.meta.keys.ResidentChildEntityKey;
import com.ssp.maintainance.services.ContactEntityService;

@RestController
public class ContactServiceController extends AbstractController {

	private ContactEntityService contactServices;
	
	@Autowired
	public void setOwnershipServices(ContactEntityService contactServices) {
		this.contactServices = contactServices;
	}

	@RequestMapping(value = "/ssp/contactmethod/{residentId}/{langId}", method = RequestMethod.GET)
	public ResponseEntity<List<ContactBean>> getAllContactsByResident(@PathVariable("residentId") int residentId, @PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getAllContactsByResident");
		
		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		
		List<ContactBean> addresses = contactServices.getAllContactByResidentId(control, tenantid, residentId, langId);
		
		control.addToControl(MasterControl.CURRENT_BEAN, addresses);
		postExecute(control, "getAllContactsByResident");
		
		return new ResponseEntity<>(addresses, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ssp/contactmethod/{residentId}/{addressId}/{langId}", method = RequestMethod.GET)
	public ResponseEntity<ContactBean> getContactById(@PathVariable("residentId") int residentId, @PathVariable("addressId") int addressId, @PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getContactById");
		
		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		ResidentChildEntityKey entId = new ResidentChildEntityKey(tenantid, addressId,  residentId);
		ContactBean allRefType = contactServices.getContactByResidentId(control, entId, langId);
		
		control.addToControl(MasterControl.CURRENT_BEAN, allRefType);
		postExecute(control, "getContactById");
		return new ResponseEntity<>(allRefType, HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/contactmethod", method = RequestMethod.POST)
	public ResponseEntity<String> createContact(@RequestBody ContactBean unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
		
		preExecute(control, "createContact");

		contactServices.saveOrUpdateContactEntity(control, unit);

		postExecute(control, "createContact");
		return new ResponseEntity<>("Contact Type is created successfully",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ssp/contactmethod", method = RequestMethod.PUT)
	public ResponseEntity<String> updateContact(@RequestBody ContactBean unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.PUT);
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
	
		preExecute(control, "updateContact");

		contactServices.saveOrUpdateContactEntity(control, unit);

		preExecute(control, "updateContact");
		return new ResponseEntity<>("Contact Type is updated successsfully",HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/contactmethod/{residentId}/{addressId}/{langId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteContact(@PathVariable("residentId") int residentId, @PathVariable("addressId") int addressId,@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.DELETE);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "deleteContact");

		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		ResidentChildEntityKey entId = new ResidentChildEntityKey(tenantid, addressId,  residentId);
		contactServices.deleteContact(control, entId);

		preExecute(control, "deleteContact");
		return new ResponseEntity<>("Contact Type is deleted successsfully",HttpStatus.OK);
	}

}
