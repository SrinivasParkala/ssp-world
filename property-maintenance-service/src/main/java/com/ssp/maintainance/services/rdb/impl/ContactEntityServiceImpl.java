package com.ssp.maintainance.services.rdb.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssp.maintainance.beans.ContactBean;
import com.ssp.maintainance.beans.ContactEntity;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.RefContactType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.keys.ResidentChildEntityKey;
import com.ssp.maintainance.meta.services.AbstractService;
import com.ssp.maintainance.meta.services.rdb.impl.RefContactServiceImpl;
import com.ssp.maintainance.repositories.ContactEntityRepository;
import com.ssp.maintainance.services.ContactEntityService;
import com.ssp.maintainance.services.MessageByLocaleService;
import com.ssp.maintainance.util.SspConstants;
import com.ssp.maintainance.util.GenLogUtility;
import com.ssp.maintainance.util.MessageConts;

@Service
public class ContactEntityServiceImpl extends AbstractService implements ContactEntityService{

	private ContactEntityRepository contactEntityRepository;

	@Autowired
	RefContactServiceImpl contactTypeService;
	
	@Autowired
	public ContactEntityServiceImpl(ContactEntityRepository contactEntityRepository) {
		this.contactEntityRepository = contactEntityRepository;
	}

	@Override
	public List<ContactBean>  getAllContactByResidentId(MasterControl control, String tenantId, int residentId, int langId) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, null);
		preExecute(control, "getAllContactByResidentId");
		
		List<ContactBean> units = new ArrayList<>();
		ContactBean ContactBean = null;
		Iterator<ContactEntity> iterator = contactEntityRepository.getAllContactByResidentId(tenantId, residentId).iterator();
		while(iterator.hasNext()){
			ContactBean = new ContactBean();
			ContactBean.convertToBeanObject(iterator.next());
			units.add(ContactBean);
		}
		
		control.addToControl(MasterControl.CURRENT_BEAN, units);
		postExecute(control, "getAllContactByResidentId");
		return units;
	}

	@Override
	public ContactBean getContactByResidentId(MasterControl control, ResidentChildEntityKey id, int langId) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, null);
		preExecute(control, "getContactByResidentId");
		
		Optional<ContactEntity> optional =  contactEntityRepository.findById(id);
		
		ContactBean contactBean = new ContactBean();
		contactBean.convertToBeanObject(optional.get());
		
		control.addToControl(MasterControl.CURRENT_BEAN, contactBean);
		postExecute(control, "getContactByResidentId");
		
		return contactBean;
	}

	@Override
	public ContactBean saveOrUpdateContactEntity(MasterControl control, ContactBean unit) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
		preExecute(control, "saveOrUpdateContactEntity");
		
		ContactEntity contact = contactEntityRepository.save(unit.convertToEntityObject());
		unit.convertToBeanObject(contact);
		
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
		postExecute(control, "saveOrUpdateContactEntity");
		return unit;
	}

	@Override
	public void deleteContact(MasterControl control, ResidentChildEntityKey id) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, null);
		preExecute(control, "delete");
		
		contactEntityRepository.deleteById(id);
		
		postExecute(control, "delete");
	}

	@Override
	public void validateObject(MasterControl masterControl)
			throws InvalidInputException {

		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		
		Object bean = masterControl.getFromControl(MasterControl.CURRENT_BEAN);
		if( bean != null ){
			ContactBean unit = (ContactBean)bean;
			Object obj = masterControl.getFromControl(MasterControl.LANG_ID);
			int langId = -1;
			
			if( obj != null){
				langId = (Integer)obj;
			}
			else{
				langId = SspConstants.DEFAULT_LANG;
			}
			
			if( unit.getContactType() < 0 ){
				String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_005.toString(), new String[]{"ContactType"});
				throw new InvalidInputException(message);
			}
			else{
				RefEntityIntKey refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),unit.getContactType(),langId);
				
				RefContactType refContactType = contactTypeService.getRefTypeById(masterControl, refEntityIntKey);
				if( refContactType == null ){
					String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_006.toString(), new String[]{String.valueOf(unit.getContactType()),"ContactType"});
					throw new InvalidInputException(message);
				}
				else{
					unit.setContactValue(refContactType.getStatusValue());
				}
			}
			
		}
	}

	@Override
	public void postTxnAction(MasterControl masterControl) {

		
		Object bean = masterControl.getFromControl(MasterControl.CURRENT_BEAN);
		
		if( bean != null ){
			Object obj = masterControl.getFromControl(MasterControl.LANG_ID);
			int langId = -1;
			
			if( obj != null){
				langId = (Integer)obj;
			}
			else{
				langId = SspConstants.DEFAULT_LANG;
			}
			
			if( bean instanceof List){
				List<ContactBean> units = (List<ContactBean>)masterControl.getFromControl(MasterControl.CURRENT_BEAN);
				
				for(ContactBean unit : units){
					getRefDataValue(masterControl, unit, langId);
				}
			}
			else{
				ContactBean unit = (ContactBean)masterControl.getFromControl(MasterControl.CURRENT_BEAN);
				getRefDataValue(masterControl, unit, langId);
			}
			
		}
	}
	
	private void getRefDataValue(MasterControl masterControl, ContactBean unit, int langId){
		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		
		try{
			RefEntityIntKey refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),unit.getContactType(),langId);
			
			RefContactType refContactType = contactTypeService.getRefTypeById(masterControl, refEntityIntKey);
			unit.setContactValue(refContactType.getStatusValue());
		}
		catch(InvalidInputException iie){
			String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_007.toString());
			GenLogUtility.logMessage(masterControl, GenLogUtility.LogLevel.WARNING, this.getClass().getName(), "postTxnAction", message, iie);
		}
	}

}
