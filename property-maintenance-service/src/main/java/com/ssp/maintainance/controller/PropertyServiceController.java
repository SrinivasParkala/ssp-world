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

import com.ssp.maintainance.beans.PropertyBean;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.controller.AbstractController;
import com.ssp.maintainance.meta.keys.EntityStringKey;
import com.ssp.maintainance.services.PropertyEntityService;

@RestController
public class PropertyServiceController extends AbstractController {

	private PropertyEntityService propertyServices;

	@Autowired
	public void setOwnershipServices(PropertyEntityService propertyServices) {
		this.propertyServices = propertyServices;
	}

	@RequestMapping(value = "/ssp/property/{propertyId}/{langId}", method = RequestMethod.GET)
	public ResponseEntity<PropertyBean> getProperty(@PathVariable("propertyId") String propertyId, @PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getProperty");
		
		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		EntityStringKey entId = new EntityStringKey(tenantid, propertyId);
		PropertyBean allRefType = propertyServices.gePropertyEntityId(control, entId, langId);
		
		control.addToControl(MasterControl.CURRENT_BEAN, allRefType);
		postExecute(control, "getProperty");
		
		return new ResponseEntity<>(allRefType, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ssp/property/{langId}", method = RequestMethod.GET)
	public ResponseEntity<List<PropertyBean>> getAllProperty(@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getAllProperty");
		
		List<PropertyBean> allRefType = propertyServices.getPropertyEntities(control , langId);
		
		control.addToControl(MasterControl.CURRENT_BEAN, allRefType);
		postExecute(control, "getAllProperty");
		return new ResponseEntity<>(allRefType, HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/property", method = RequestMethod.POST)
	public ResponseEntity<String> createProperty(@RequestBody PropertyBean unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
		
		preExecute(control, "createProperty");

		propertyServices.saveOrUpdatePropertyEntity(control, unit);

		postExecute(control, "createProperty");
		return new ResponseEntity<>("Contact Type is created successfully",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ssp/property", method = RequestMethod.PUT)
	public ResponseEntity<String> updateProperty(@RequestBody PropertyBean unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.PUT);
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
	
		preExecute(control, "updateProperty");

		propertyServices.saveOrUpdatePropertyEntity(control, unit);

		preExecute(control, "updateProperty");
		return new ResponseEntity<>("Contact Type is updated successsfully",HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/property/{propertyId}/{langId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteProperty(@PathVariable("propertyId") String propertyId,@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.DELETE);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "deleteProperty");

		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		EntityStringKey entId = new EntityStringKey(tenantid, propertyId);
		propertyServices.delete(control, entId);

		preExecute(control, "deleteProperty");
		return new ResponseEntity<>("Contact Type is deleted successsfully",HttpStatus.OK);
	}

}
