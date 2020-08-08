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
import com.ssp.maintainance.meta.beans.RefTitleType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.services.RefTypeService;

@RestController
public class TitleTypeServiceController extends AbstractController {

	private RefTypeService<RefTitleType> titleServices;

	@Autowired
	public void setOwnershipServices(RefTypeService<RefTitleType> titleServices) {
		this.titleServices = titleServices;
	}

	@RequestMapping(value = "/ssp/meta/titleStatusType/{langId}", method = RequestMethod.GET)
	public ResponseEntity<List<RefTitleType>> getTitleStatus(@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		
		preExecute(control, "getTitleStatus");
		
		List<RefTitleType> allRefType = titleServices.getAllRefType(control);
		
		postExecute(control, "getTitleStatus");
		return new ResponseEntity<>(allRefType, HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/meta/titleStatusType", method = RequestMethod.POST)
	public ResponseEntity<String> createTitleStatus(@RequestBody RefTitleType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "createTitleStatus");

		titleServices.saveOrUpdateRefType(control, unit);

		postExecute(control, "createTitleStatus");
		return new ResponseEntity<>("Title is created successfully",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ssp/meta/titleStatusType", method = RequestMethod.PUT)
	public ResponseEntity<String> updateTitleStatus(@RequestBody RefTitleType unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.PUT);
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		if(  unit.getUnitId() != null ){
			control.addToControl(MasterControl.LANG_ID, unit.getUnitId().getLangId());
		}
		
		preExecute(control, "updateTitleStatus");

		titleServices.saveOrUpdateRefType(control, unit);

		preExecute(control, "updateTitleStatus");
		return new ResponseEntity<>("Title is updated successsfully",HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/meta/contactTitleType/{id}/{langId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTitleStatus(@PathVariable("id") int id, @PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.DELETE);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "deleteTitleStatus");

		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		RefEntityIntKey entId = new RefEntityIntKey(tenantid, id, langId);
		titleServices.deleteRefType(control, entId);

		preExecute(control, "deleteTitleStatus");
		return new ResponseEntity<>("Title is deleted successsfully",HttpStatus.OK);
	}

}
