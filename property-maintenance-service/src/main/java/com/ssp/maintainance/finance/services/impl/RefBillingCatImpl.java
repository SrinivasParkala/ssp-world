package com.ssp.maintainance.finance.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.finance.beans.RefBillingCatType;
import com.ssp.maintainance.finance.repositories.RefBillingCatRepository;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.services.AbstractService;
import com.ssp.maintainance.meta.services.RefTypeService;

@Service
public class RefBillingCatImpl extends AbstractService implements RefTypeService<RefBillingCatType> {

	private RefBillingCatRepository t;

	@Autowired
	public RefBillingCatImpl(RefBillingCatRepository t) {
		this.t = t;
	}
	
	@Override
	public List<RefBillingCatType> getAllRefType(MasterControl control)
			throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "getAllRefType");
		
		List<RefBillingCatType> refDatas = new ArrayList<>();
		Iterator<RefBillingCatType> iterator = t.findAll().iterator();
		while(iterator.hasNext()){
			refDatas.add(iterator.next());
		}
		
		postExecute(control, "getAllRefType");
		return refDatas;
	}

	@Override
	public RefBillingCatType getRefTypeById(MasterControl control,
			RefEntityIntKey id) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "getRefTypeById");
		
		Optional<RefBillingCatType> optional =  t.findById(id);
		
		postExecute(control, "getRefTypeById");
		RefBillingCatType refStatusType = null;
		if( optional.isPresent() ){
			refStatusType = optional.get();
		}
		
		return refStatusType;
	}

	@Override
	public RefBillingCatType saveOrUpdateRefType(MasterControl control,
			RefBillingCatType unit) throws InvalidInputException {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postTxnAction(MasterControl masterControl) {
		// TODO Auto-generated method stub
	}

}
