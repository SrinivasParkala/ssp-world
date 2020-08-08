package com.ssp.maintainance.meta.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ssp.maintainance.meta.beans.RefUnitType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

public interface UnitTypeRepository extends CrudRepository<RefUnitType, RefEntityIntKey>{
}
