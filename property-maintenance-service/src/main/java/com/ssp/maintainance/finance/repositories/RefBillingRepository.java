package com.ssp.maintainance.finance.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ssp.maintainance.finance.beans.RefBillingType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

public interface RefBillingRepository extends CrudRepository<RefBillingType, RefEntityIntKey>{

}
