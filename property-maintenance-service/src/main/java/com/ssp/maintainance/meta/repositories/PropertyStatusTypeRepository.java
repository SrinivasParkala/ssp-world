package com.ssp.maintainance.meta.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ssp.maintainance.meta.beans.RefPropertyStatusType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

public interface PropertyStatusTypeRepository extends CrudRepository<RefPropertyStatusType, RefEntityIntKey>{
	
}
