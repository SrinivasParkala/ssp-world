package com.ssp.maintainance.finance.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.finance.beans.RefBillingCatType;
import com.ssp.maintainance.finance.beans.RefBillingType;
import com.ssp.maintainance.finance.repositories.RefBillingRepository;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.services.AbstractService;
import com.ssp.maintainance.meta.services.RefTypeService;
import com.ssp.maintainance.services.MessageByLocaleService;
import com.ssp.maintainance.util.MessageConts;

@Service
public class RefBillingImpl extends AbstractService implements RefTypeService<RefBillingType> {

	private RefBillingRepository t;

	@Autowired
	private RefBillingCatImpl refBillingCatImpl;
	
	@Autowired
	public RefBillingImpl(RefBillingRepository t) {
		this.t = t;
	}
	
	@Override
	public List<RefBillingType> getAllRefType(MasterControl control)
			throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "getAllRefType");
		
		List<RefBillingType> refDatas = new ArrayList<>();
		Iterator<RefBillingType> iterator = t.findAll().iterator();
		while(iterator.hasNext()){
			refDatas.add(iterator.next());
		}
		
		postExecute(control, "getAllRefType");
		return refDatas;
	}

	@Override
	public RefBillingType getRefTypeById(MasterControl control,
			RefEntityIntKey id) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "getRefTypeById");
		
		Optional<RefBillingType> optional =  t.findById(id);
		
		postExecute(control, "getRefTypeById");
		RefBillingType refStatusType = null;
		if( optional.isPresent() ){
			refStatusType = optional.get();
		}
		
		return refStatusType;
	}

	@Override
	public RefBillingType saveOrUpdateRefType(MasterControl control,
			RefBillingType unit) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		preExecute(control, "saveOrUpdateRefType");
		t.save(unit);
		postExecute(control, "saveOrUpdateRefType");
		return unit;
	}

	@Override
	public void deleteRefType(MasterControl control, RefEntityIntKey id)
			throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "deleteRefType");
		t.deleteById(id);
		postExecute(control, "deleteRefType");
	}

	@Override
	public void validateObject(MasterControl masterControl)
			throws InvalidInputException {
		
		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		
		if( masterControl.getFromControl(MasterControl.CURRENT_OBJECT) != null ){
			RefBillingType unit = (RefBillingType)masterControl.getFromControl(MasterControl.CURRENT_OBJECT);
			
			int catType = unit.getBillingCatType();
			
			String tenantId = masterControl.getFromControl(MasterControl.TENANT).toString();
			int langId = Integer.valueOf(masterControl.getFromControl(MasterControl.LANG_ID).toString());
			
			RefEntityIntKey catKey = new RefEntityIntKey(tenantId, catType, langId);
			RefBillingCatType refBillingCatType = refBillingCatImpl.getRefTypeById(masterControl, catKey);
			
			if( refBillingCatType == null ){
				String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_008.toString(), new String[]{String.valueOf(catType)});
				throw new InvalidInputException(message);
			}
		}
		
	}

	@Override
	public void postTxnAction(MasterControl masterControl) {
		
	}

}
