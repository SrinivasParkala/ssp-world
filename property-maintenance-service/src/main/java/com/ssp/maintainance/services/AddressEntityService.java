package com.ssp.maintainance.services;

import java.util.List;

import com.ssp.maintainance.beans.AddressBean;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.keys.ResidentChildEntityKey;

public interface AddressEntityService {
	List<AddressBean> getAllAddressByResidentId(MasterControl control, String tenantId, int residentId, int langId) throws InvalidInputException ;

	AddressBean getAddressByResidentId(MasterControl control, ResidentChildEntityKey id,int langId) throws InvalidInputException ;

	AddressBean saveOrUpdateAddressEntity(MasterControl control, AddressBean unit) throws InvalidInputException ;

    void deleteAddress(MasterControl control, ResidentChildEntityKey id) throws InvalidInputException ;
}
