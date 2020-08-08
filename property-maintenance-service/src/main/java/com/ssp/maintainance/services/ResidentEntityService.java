package com.ssp.maintainance.services;

import java.util.List;

import com.ssp.maintainance.beans.ResidentBean;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.keys.EntityIntKey;

public interface ResidentEntityService {
	List<ResidentBean> getAllResidentEntities(MasterControl control,int langId) throws InvalidInputException ;

	ResidentBean geResidentEntityById(MasterControl control, EntityIntKey id, int langId) throws InvalidInputException ;

	ResidentBean saveOrUpdateResidentEntity(MasterControl control, ResidentBean unit) throws InvalidInputException ;

    void deleteResident(MasterControl control, EntityIntKey id) throws InvalidInputException ;
}
