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

import com.ssp.maintainance.beans.AddressBean;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.controller.AbstractController;
import com.ssp.maintainance.meta.keys.ResidentChildEntityKey;
import com.ssp.maintainance.services.AddressEntityService;

@RestController
public class AddressServiceController extends AbstractController {

	private AddressEntityService addressServices;
	
	@Autowired
	public void setAddressServices(AddressEntityService addressServices) {
		this.addressServices = addressServices;
	}

	@RequestMapping(value = "/ssp/address/{residentId}/{langId}", method = RequestMethod.GET)
	public ResponseEntity<List<AddressBean>> getAllAddressByResident(@PathVariable("residentId") int residentId, @PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getAllAddressByResident");
		
		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		
		List<AddressBean> addresses = addressServices.getAllAddressByResidentId(control, tenantid, residentId, langId);
		
		control.addToControl(MasterControl.CURRENT_BEAN, addresses);
		postExecute(control, "getAllAddressByResident");
		
		return new ResponseEntity<>(addresses, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ssp/address/{residentId}/{addressId}/{langId}", method = RequestMethod.GET)
	public ResponseEntity<AddressBean> getAddressById(@PathVariable("residentId") int residentId, @PathVariable("addressId") int addressId, @PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getAddressById");
		
		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		ResidentChildEntityKey entId = new ResidentChildEntityKey(tenantid, addressId,  residentId);
		AddressBean allRefType = addressServices.getAddressByResidentId(control, entId, langId);
		
		control.addToControl(MasterControl.CURRENT_BEAN, allRefType);
		postExecute(control, "getAddressById");
		return new ResponseEntity<>(allRefType, HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/address", method = RequestMethod.POST)
	public ResponseEntity<String> createAddress(@RequestBody AddressBean unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
		
		preExecute(control, "createAddress");

		addressServices.saveOrUpdateAddressEntity(control, unit);

		postExecute(control, "createAddress");
		return new ResponseEntity<>("Contact Type is created successfully",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ssp/address", method = RequestMethod.PUT)
	public ResponseEntity<String> updateAddress(@RequestBody AddressBean unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.PUT);
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
	
		preExecute(control, "updateAddress");

		addressServices.saveOrUpdateAddressEntity(control, unit);

		preExecute(control, "updateAddress");
		return new ResponseEntity<>("Contact Type is updated successsfully",HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/address/{residentId}/{addressId}/{langId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteAddress(@PathVariable("residentId") int residentId, @PathVariable("addressId") int addressId,@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.DELETE);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "deleteAddress");

		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		ResidentChildEntityKey entId = new ResidentChildEntityKey(tenantid, addressId,  residentId);
		addressServices.deleteAddress(control, entId);

		preExecute(control, "deleteAddress");
		return new ResponseEntity<>("Contact Type is deleted successsfully",HttpStatus.OK);
	}

}
