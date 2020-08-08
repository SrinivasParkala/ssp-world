package com.ssp.maintainance.finance.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ssp.maintainance.finance.beans.BillingCategoryEntity;
import com.ssp.maintainance.meta.keys.EntityIntKey;

public interface BillingCategoryRepository extends CrudRepository<BillingCategoryEntity, EntityIntKey>{

}
