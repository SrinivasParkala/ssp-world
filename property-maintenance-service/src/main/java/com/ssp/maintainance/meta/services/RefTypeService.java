package com.ssp.maintainance.meta.services;

import java.util.List;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.RefMasterRootEntity;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

public interface RefTypeService<T extends RefMasterRootEntity> {
	List<T> getAllRefType(MasterControl control) throws InvalidInputException;

	T getRefTypeById(MasterControl control, RefEntityIntKey id) throws InvalidInputException;

	T saveOrUpdateRefType(MasterControl control, T unit) throws InvalidInputException;

    void deleteRefType(MasterControl control, RefEntityIntKey id) throws InvalidInputException;
}
