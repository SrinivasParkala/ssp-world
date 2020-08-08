package com.ssp.maintainance.services;

import java.util.List;

import com.ssp.maintainance.beans.PropertyBean;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.keys.EntityStringKey;

public interface PropertyEntityService {
	List<PropertyBean> getPropertyEntities(MasterControl control,int langId) throws InvalidInputException ;

	PropertyBean gePropertyEntityId(MasterControl control, EntityStringKey id, int langId) throws InvalidInputException ;

	PropertyBean saveOrUpdatePropertyEntity(MasterControl control, PropertyBean unit) throws InvalidInputException ;

    void delete(MasterControl control, EntityStringKey id) throws InvalidInputException ;
}
