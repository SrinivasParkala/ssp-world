package com.ssp.maintainance.finance.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.finance.beans.RefPeriodicType;
import com.ssp.maintainance.finance.repositories.RefPeriodicRepository;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.services.AbstractService;
import com.ssp.maintainance.meta.services.RefTypeService;

@Service
public class RefPeriodicImpl extends AbstractService implements RefTypeService<RefPeriodicType> {

	private RefPeriodicRepository t;

	@Autowired
	public RefPeriodicImpl(RefPeriodicRepository t) {
		this.t = t;
	}
	
	@Override
	public List<RefPeriodicType> getAllRefType(MasterControl control)
			throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "getAllRefType");
		
		List<RefPeriodicType> refDatas = new ArrayList<>();
		Iterator<RefPeriodicType> iterator = t.findAll().iterator();
		while(iterator.hasNext()){
			refDatas.add(iterator.next());
		}
		
		postExecute(control, "getAllRefType");
		return refDatas;
	}

	@Override
	public RefPeriodicType getRefTypeById(MasterControl control,
			RefEntityIntKey id) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "getRefTypeById");
		
		Optional<RefPeriodicType> optional =  t.findById(id);
		
		postExecute(control, "getRefTypeById");
		RefPeriodicType refStatusType = null;
		if( optional.isPresent() ){
			refStatusType = optional.get();
		}
		
		return refStatusType;
	}

	@Override
	public RefPeriodicType saveOrUpdateRefType(MasterControl control,
			RefPeriodicType unit) throws InvalidInputException {
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
