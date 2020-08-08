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

import com.ssp.maintainance.beans.ResidentBean;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.controller.AbstractController;
import com.ssp.maintainance.meta.keys.EntityIntKey;
import com.ssp.maintainance.services.ResidentEntityService;

@RestController
public class ResidentServiceController extends AbstractController {

	private ResidentEntityService residentServices;

	@Autowired
	public void setOwnershipServices(ResidentEntityService residentServices) {
		this.residentServices = residentServices;
	}

	@RequestMapping(value = "/ssp/resident/{langId}", method = RequestMethod.GET)
	public ResponseEntity<List<ResidentBean>> getAllResidents( @PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getAllResidents");
		
		List<ResidentBean> addresses = residentServices.getAllResidentEntities(control, langId);
		
		control.addToControl(MasterControl.CURRENT_BEAN, addresses);
		postExecute(control, "getAllResidents");
		
		return new ResponseEntity<>(addresses, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ssp/resident/{residentId}/{langId}", method = RequestMethod.GET)
	public ResponseEntity<ResidentBean> getResidentById(@PathVariable("residentId") int residentId, @PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getResidentById");
		
		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		EntityIntKey entId = new EntityIntKey(tenantid, residentId);
		ResidentBean allRefType = residentServices.geResidentEntityById(control, entId, langId);
		
		control.addToControl(MasterControl.CURRENT_BEAN, allRefType);
		postExecute(control, "getResidentById");
		return new ResponseEntity<>(allRefType, HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/resident", method = RequestMethod.POST)
	public ResponseEntity<String> createResident(@RequestBody ResidentBean unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
		
		preExecute(control, "createResident");

		residentServices.saveOrUpdateResidentEntity(control, unit);

		postExecute(control, "createResident");
		return new ResponseEntity<>("Contact Type is created successfully",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ssp/resident", method = RequestMethod.PUT)
	public ResponseEntity<String> updateResident(@RequestBody ResidentBean unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.PUT);
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
	
		preExecute(control, "updateResident");

		residentServices.saveOrUpdateResidentEntity(control, unit);

		preExecute(control, "updateResident");
		return new ResponseEntity<>("Contact Type is updated successsfully",HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/resident/{residentId}/{langId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteResident(@PathVariable("residentId") int residentId, @PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.DELETE);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "deleteResident");

		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		EntityIntKey entId = new EntityIntKey(tenantid, residentId);
		
		residentServices.deleteResident(control, entId);

		preExecute(control, "deleteResident");
		return new ResponseEntity<>("Contact Type is deleted successsfully",HttpStatus.OK);
	}

}
