package com.ssp.maintainance.finance.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ssp.maintainance.finance.beans.BillingFormulaEntity;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

public interface BillingFormulaRepository extends CrudRepository<BillingFormulaEntity, RefEntityIntKey>{

}
