package com.ssp.maintainance.finance.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ssp.maintainance.finance.beans.RefBillingCatType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

public interface RefBillingCatRepository extends CrudRepository<RefBillingCatType, RefEntityIntKey>{

}
