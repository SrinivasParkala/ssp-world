package com.ssp.maintainance.finance.controller;

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
import com.ssp.maintainance.finance.beans.BillingCategoryBean;
import com.ssp.maintainance.finance.services.impl.BillingCategoryImpl;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.controller.AbstractController;
import com.ssp.maintainance.meta.keys.EntityIntKey;

@RestController
public class BillingCategoryServiceController extends AbstractController {
	private BillingCategoryImpl billingCategoryImpl;
	
	@Autowired
	public void setBillingCategoryServices(BillingCategoryImpl billingCategoryImpl) {
		this.billingCategoryImpl = billingCategoryImpl;
	}

	@RequestMapping(value = "/ssp/fin/billingCategory/{billingId}/{langId}", method = RequestMethod.GET)
	public ResponseEntity<BillingCategoryBean> getBillingCategoryById(@PathVariable("billingId") int billingId, @PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getBillingCategoryById");
		
		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		EntityIntKey refEntityIntKey = new EntityIntKey(tenantid, billingId);
		
		BillingCategoryBean billingCategoryBean = billingCategoryImpl.getBillingCategoryById(control, refEntityIntKey);
		
		control.addToControl(MasterControl.CURRENT_BEAN, billingCategoryBean);
		postExecute(control, "getBillingCategoryById");
		
		return new ResponseEntity<>(billingCategoryBean, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ssp/fin/billingCategory/{langId}", method = RequestMethod.GET)
	public ResponseEntity<List<BillingCategoryBean>> getAllBillingCategory(@PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.GET);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "getAllBillingCategory");
		
		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
		
		List<BillingCategoryBean> billingCategoryBeans = billingCategoryImpl.getAllBillingCategory(control);
		
		control.addToControl(MasterControl.CURRENT_BEAN, billingCategoryBeans);
		postExecute(control, "getAllBillingCategory");
		
		return new ResponseEntity<>(billingCategoryBeans, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ssp/fin/billingCategory", method = RequestMethod.POST)
	public ResponseEntity<String> createBillingCategory(@RequestBody BillingCategoryBean unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.POST);
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
		
		preExecute(control, "createBillingCategory");

		billingCategoryImpl.saveOrUpdateBillingCategory(control, unit);

		postExecute(control, "createBillingCategory");
		return new ResponseEntity<>("BillingCategory is created successfully",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ssp/fin/billingCategory", method = RequestMethod.PUT)
	public ResponseEntity<String> updateBillingCategory(@RequestBody BillingCategoryBean unit) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.PUT);
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
	
		preExecute(control, "updateBillingCategory");

		billingCategoryImpl.saveOrUpdateBillingCategory(control, unit);

		preExecute(control, "updateBillingCategory");
		return new ResponseEntity<>("BillingCategory is updated successsfully",HttpStatus.OK);
	}

	@RequestMapping(value = "/ssp/fin/billingCategory/{billingId}/{langId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteBillingCategory(@PathVariable("billingId") int billingId, @PathVariable("langId") int  langId) throws InvalidInputException {
		MasterControl control = initControl();
		control.setActionType(MasterControl.ActionType.DELETE);
		control.addToControl(MasterControl.LANG_ID, langId);
		preExecute(control, "deleteBillingCategory");

		String tenantid = control.getFromControl(MasterControl.TENANT).toString();
	
		EntityIntKey refEntityIntKey = new EntityIntKey(tenantid, billingId);
		billingCategoryImpl.deleteBillCatergoryEntity(control, refEntityIntKey);

		preExecute(control, "deleteBillingCategory");
		return new ResponseEntity<>("BillingCategory is deleted successsfully",HttpStatus.OK);
	}
}
