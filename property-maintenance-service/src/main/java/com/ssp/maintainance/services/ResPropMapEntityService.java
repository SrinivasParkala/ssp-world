package com.ssp.maintainance.services;

import java.util.List;

import com.ssp.maintainance.beans.ResPropMapEntity;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.keys.ResPropKey;

public interface ResPropMapEntityService {
	List<ResPropMapEntity> getAllResPropMapEntities(MasterControl control, String tenantId, String residentId, int langId) throws InvalidInputException ;

	ResPropMapEntity geResPropMapById(MasterControl control, ResPropKey id, int langId) throws InvalidInputException ;

	ResPropMapEntity saveOrUpdateResPropMapEntity(MasterControl control, ResPropMapEntity unit) throws InvalidInputException ;

    void deleteResPropMap(MasterControl control, ResPropKey id) throws InvalidInputException ;
}
