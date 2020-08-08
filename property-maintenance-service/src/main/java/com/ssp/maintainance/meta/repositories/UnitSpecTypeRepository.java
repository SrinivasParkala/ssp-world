package com.ssp.maintainance.meta.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ssp.maintainance.meta.beans.RefUnitSpecType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

public interface UnitSpecTypeRepository extends CrudRepository<RefUnitSpecType, RefEntityIntKey>{

}
