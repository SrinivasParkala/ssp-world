package com.ssp.maintainance.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ssp.maintainance.beans.PropertyEntity;
import com.ssp.maintainance.meta.keys.EntityStringKey;

public interface PropertyEntityRepository extends CrudRepository<PropertyEntity, EntityStringKey>{

}
