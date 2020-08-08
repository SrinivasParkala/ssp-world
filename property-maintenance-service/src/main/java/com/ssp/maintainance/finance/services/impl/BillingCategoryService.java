package com.ssp.maintainance.finance.services.impl;

import java.util.List;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.finance.beans.BillingCategoryBean;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.keys.EntityIntKey;

public interface BillingCategoryService {

	List<BillingCategoryBean> getAllBillingCategory(MasterControl control) throws InvalidInputException ;

	BillingCategoryBean getBillingCategoryById(MasterControl control, EntityIntKey id) throws InvalidInputException ;

	BillingCategoryBean saveOrUpdateBillingCategory(MasterControl control, BillingCategoryBean unit) throws InvalidInputException ;

    void deleteBillCatergoryEntity(MasterControl control, EntityIntKey id) throws InvalidInputException ;
}
