package com.ssp.maintainance.finance.services.impl;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.finance.beans.BillingFormulaEntity;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

public interface BillingFormulaService {

	BillingFormulaEntity getBillingFormulaById(MasterControl control, RefEntityIntKey id) throws InvalidInputException ;

	BillingFormulaEntity saveOrUpdateBillingFormula(MasterControl control, BillingFormulaEntity unit) throws InvalidInputException ;

    void deleteBillingFormulaEntity(MasterControl control, RefEntityIntKey id) throws InvalidInputException ;
}
