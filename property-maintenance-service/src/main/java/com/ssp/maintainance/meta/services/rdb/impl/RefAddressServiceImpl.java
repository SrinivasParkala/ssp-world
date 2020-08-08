package com.ssp.maintainance.meta.services.rdb.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.RefAddressType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.repositories.AddressTypeRepository;
import com.ssp.maintainance.meta.services.AbstractService;
import com.ssp.maintainance.meta.services.RefTypeService;

@Service
public class RefAddressServiceImpl extends AbstractService implements RefTypeService<RefAddressType> {

	private AddressTypeRepository t;

	@Autowired
	public RefAddressServiceImpl(AddressTypeRepository t) {
		this.t = t;
	}

	@Override
	public List<RefAddressType> getAllRefType(MasterControl control) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "getAllRefType");
		
		List<RefAddressType> refDatas = new ArrayList<>();
		Iterator<RefAddressType> iterator = t.findAll().iterator();
		while(iterator.hasNext()){
			refDatas.add(iterator.next());
		}
		
		postExecute(control, "getAllRefType");
		return refDatas;
	}

	@Override
	public RefAddressType getRefTypeById(MasterControl control, RefEntityIntKey id) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "getRefTypeById");
		
		Optional<RefAddressType> optional =  t.findById(id);
		
		postExecute(control, "getRefTypeById");
		RefAddressType refStatusType = null;
		if( optional.isPresent() ){
			refStatusType = optional.get();
		}
		
		return refStatusType;
	}

	@Override
	public RefAddressType saveOrUpdateRefType(MasterControl control, RefAddressType refData) throws InvalidInputException {
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
