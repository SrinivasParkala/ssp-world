package com.ssp.maintainance.controller;

import java.util.ArrayList;
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
import com.ssp.maintainance.beans.PropertyBean;
import com.ssp.maintainance.beans.ResidentBean;
import com.ssp.maintainance.beans.ResidentSummayBean;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.controller.AbstractController;
import com.ssp.maintainance.meta.keys.EntityIntKey;
import com.ssp.maintainance.services.ResidentEntityService;

@RestController
public class ResidentServiceController extends AbstractController {

	private final int EMAIL = 1;
	private final int MOBILE = 2;
	
	private ResidentEntityService residentServices;

	@Autowired
	public void setOwnershipServices(ResidentEntityService residentServices) {
		this.residentServices = residentServices;
	}

	@RequestMapping(value = "/ssp/resident/{langId}", method = RequestMethod.GET)
	public ResponseEntity<List<ResidentSummayBean>> getAllResidents( @PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getAllResidents");
		
		List<ResidentBean> residents = residentServices.getAllResidentEntities(control, langId);
		
		control.addToControl(MasterControl.CURRENT_BEAN, residents);
		
		postExecute(control, "getAllResidents");
		
		List<ResidentSummayBean> residentSummary = fetchResidentSummary(residents);
		
		return new ResponseEntity<>(residentSummary, HttpStatus.OK);
	}
	
	private List<ResidentSummayBean> fetchResidentSummary(List<ResidentBean> residents){
		List<ResidentSummayBean> residentSummary = new ArrayList();
		ResidentSummayBean residentSummayBean = null;
		for(ResidentBean resident : residents) {
			residentSummayBean = new ResidentSummayBean();
			
			residentSummayBean.setName(resident.getNameTitleValue()+" "+resident.getFirstName()+" "+resident.getLastName());
			residentSummayBean.setResidentId(resident.getKeyId());
			
			List<ContactBean> contacts = resident.getContacts();
			if( contacts != null ) {
				for(ContactBean contact : contacts) {
					if( contact.getContactType() == EMAIL ) {
						residentSummayBean.setEmail(contact.getContactDataValue());
					}
					else if( contact.getContactType() == MOBILE ) {
						residentSummayBean.setMobileNo(contact.getContactDataValue());
					}
				}//for
			}//if
			
			 List<PropertyBean> props = resident.getResProperty();
			 if( props != null ) {
				 	boolean first = true;
					for(PropertyBean prop : props) {
						if( first ) {
							residentSummayBean.setUnitNo(prop.getKeyId());
							residentSummayBean.setUnitDescription(prop.getUnitValue());
							residentSummary.add(residentSummayBean);
							first = false;
						}
						else {
							ResidentSummayBean residentSummayBean1 = new ResidentSummayBean();
							residentSummayBean1.setName(residentSummayBean.getName());
							residentSummayBean1.setEmail(residentSummayBean.getEmail());
							residentSummayBean1.setMobileNo(residentSummayBean.getMobileNo());
							residentSummayBean1.setUnitNo(prop.getKeyId());
							residentSummayBean1.setUnitDescription(prop.getUnitValue());
							residentSummary.add(residentSummayBean1);
						}
					}//for
				}//if
			 else {
				 residentSummary.add(residentSummayBean);
			 }
			 
			
		}//for
		
		return residentSummary;
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
