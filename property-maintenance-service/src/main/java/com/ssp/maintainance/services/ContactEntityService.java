package com.ssp.maintainance.services;

import java.util.List;

import com.ssp.maintainance.beans.ContactBean;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.keys.ResidentChildEntityKey;

public interface ContactEntityService {
	List<ContactBean> getAllContactByResidentId(MasterControl control, String tenantId, int residentId, int langId) throws InvalidInputException ;

	ContactBean getContactByResidentId(MasterControl control, ResidentChildEntityKey id, int langId) throws InvalidInputException ;

	ContactBean saveOrUpdateContactEntity(MasterControl control, ContactBean unit) throws InvalidInputException ;

    void deleteContact(MasterControl control, ResidentChildEntityKey id) throws InvalidInputException ;
}
