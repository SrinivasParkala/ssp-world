package com.ssp.maintainance.meta.services.rdb.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.RefPropertyStatusType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.repositories.PropertyStatusTypeRepository;
import com.ssp.maintainance.meta.services.AbstractService;
import com.ssp.maintainance.meta.services.RefTypeService;

@Service
public class RefPropertyServiceImpl extends AbstractService implements RefTypeService<RefPropertyStatusType> {

	private PropertyStatusTypeRepository t;

	@Autowired
	public RefPropertyServiceImpl(PropertyStatusTypeRepository t) {
		this.t = t;
	}

	@Override
	public List<RefPropertyStatusType> getAllRefType(MasterControl control) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "getAllRefType");
		
		List<RefPropertyStatusType> refDatas = new ArrayList<>();
		Iterator<RefPropertyStatusType> iterator = t.findAll().iterator();
		while(iterator.hasNext()){
			refDatas.add(iterator.next());
		}
		
		postExecute(control, "getAllRefType");
		return refDatas;
	}

	@Override
	public RefPropertyStatusType getRefTypeById(MasterControl control, RefEntityIntKey id) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "getRefTypeById");
		
		Optional<RefPropertyStatusType> optional =  t.findById(id);
		
		postExecute(control, "getRefTypeById");
		RefPropertyStatusType refStatusType = null;
		if( optional.isPresent() ){
			refStatusType = optional.get();
		}
		
		return refStatusType;
	}

	@Override
	public RefPropertyStatusType saveOrUpdateRefType(MasterControl control, RefPropertyStatusType refData) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, refData);
		preExecute(control, "saveOrUpdateRefType");
		t.save(refData);
		postExecute(control, "saveOrUpdateRefType");
		return refData;
	}

	@Override
	public void deleteRefType(MasterControl control, RefEntityIntKey id) throws InvalidInputException {
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
