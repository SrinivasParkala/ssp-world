package com.ssp.maintainance.finance.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ssp.maintainance.finance.beans.RefPeriodicType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

public interface RefPeriodicRepository extends CrudRepository<RefPeriodicType, RefEntityIntKey>{

}
