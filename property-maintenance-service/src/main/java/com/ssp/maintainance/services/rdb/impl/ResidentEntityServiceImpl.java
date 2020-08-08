package com.ssp.maintainance.services.rdb.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssp.maintainance.beans.AddressBean;
import com.ssp.maintainance.beans.ContactBean;
import com.ssp.maintainance.beans.PropertyBean;
import com.ssp.maintainance.beans.ResPropMapEntity;
import com.ssp.maintainance.beans.ResidentBean;
import com.ssp.maintainance.beans.ResidentEntity;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.RefOwnershipType;
import com.ssp.maintainance.meta.beans.RefTitleType;
import com.ssp.maintainance.meta.keys.EntityIntKey;
import com.ssp.maintainance.meta.keys.EntityStringKey;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.keys.ResPropKey;
import com.ssp.maintainance.meta.services.AbstractService;
import com.ssp.maintainance.meta.services.rdb.impl.RefOwnershipServiceImpl;
import com.ssp.maintainance.meta.services.rdb.impl.RefTitleServiceImpl;
import com.ssp.maintainance.repositories.ResidentEntityRepository;
import com.ssp.maintainance.services.MessageByLocaleService;
import com.ssp.maintainance.services.ResidentEntityService;
import com.ssp.maintainance.util.SspConstants;
import com.ssp.maintainance.util.GenLogUtility;
import com.ssp.maintainance.util.MessageConts;

@Service
@Transactional
public class ResidentEntityServiceImpl extends AbstractService implements ResidentEntityService{

	private ResidentEntityRepository residentEntityRepository;

	@Autowired
	AddressEntityServiceImpl	addressEntityServiceImpl;
	
	@Autowired
	ContactEntityServiceImpl	contactEntityServiceImpl;
	
	@Autowired
	ResPropMapEntityServiceImpl resPropMapEntityServiceImpl;
	
	@Autowired
	PropertyEntityServiceImpl propertyEntityServiceImpl;
	
	@Autowired
	RefOwnershipServiceImpl ownerTypeService;
	
	@Autowired
	RefTitleServiceImpl titleTypeService;
	
	@Autowired
	public ResidentEntityServiceImpl(ResidentEntityRepository residentEntityRepository) {
		this.residentEntityRepository = residentEntityRepository;
	}

	@Override
	public List<ResidentBean>  getAllResidentEntities(MasterControl control, int langId) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, null);
		preExecute(control, "getAllResidentEntities");
		
		List<ResidentBean> units = new ArrayList<>();
		ResidentBean residentBean = null;
		EntityIntKey residentId = null;
		Iterator<ResidentEntity> iterator = residentEntityRepository.findAll().iterator();
		
		while(iterator.hasNext()){
			residentBean = new ResidentBean();
			residentBean.convertToBeanObject(iterator.next());
			residentId = new EntityIntKey(residentBean.getTenantId(), Integer.valueOf(residentBean.getKeyId()));
			popullateChildObjects(residentBean, control ,residentId, langId);
			units.add(residentBean);
		}
		
		control.addToControl(MasterControl.CURRENT_BEAN, units);
		postExecute(control, "getAllResidentEntities");
		return units;
	}

	@Override
	public ResidentBean geResidentEntityById(MasterControl control, EntityIntKey id, int langId) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, null);
		preExecute(control, "geResidentEntityById");
		
		Optional<ResidentEntity> optional =  residentEntityRepository.findById(id);
		
		ResidentBean residentBean = new ResidentBean();
		
		popullateChildObjects(residentBean, control ,id, langId);
		residentBean.convertToBeanObject(optional.get());
		
		control.addToControl(MasterControl.CURRENT_BEAN, residentBean);
		postExecute(control, "geResidentEntityById");
		
		return residentBean;
	}
	
	private void popullateChildObjects(ResidentBean residentBean, MasterControl control, EntityIntKey id, int langId) throws NumberFormatException, InvalidInputException{
		List<AddressBean> address = addressEntityServiceImpl.getAllAddressByResidentId(control, id.getTenantId(), Integer.valueOf(id.getKeyId()), langId);
		residentBean.setAddress(address);
		
		List<ContactBean> contacts = contactEntityServiceImpl.getAllContactByResidentId(control, id.getTenantId(), Integer.valueOf(id.getKeyId()), langId);
		residentBean.setContacts(contacts);
		
		
		List<ResPropMapEntity> properties = resPropMapEntityServiceImpl.getAllResPropMapEntities(control, id.getTenantId(), String.valueOf(id.getKeyId()), langId);
		if( properties != null ){
			List<PropertyBean> propertyBean = new ArrayList<PropertyBean>();
			EntityStringKey propKey = null;
					
			for(ResPropMapEntity property :properties ){
				propKey = new EntityStringKey(id.getTenantId(),property.getId().getPropId());
				PropertyBean bean = propertyEntityServiceImpl.gePropertyEntityId(control, propKey, langId);
				bean.setOwnershipType(property.getOwnershipType());
				bean.setStartDate(property.getStartDate());
				bean.setEndDate(property.getEndDate());
				bean.setTenantCount(property.getTenantCount());
				
				propertyBean.add(bean);
				
			}//for
			residentBean.setResProperty(propertyBean);
		}
	}

	@Override
	@Transactional(rollbackOn=Exception.class)
	public ResidentBean saveOrUpdateResidentEntity(MasterControl control, ResidentBean unit) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
		preExecute(control, "saveOrUpdateResidentEntity");
		
		ResidentEntity resident = residentEntityRepository.save(unit.convertToEntityObject());
		
		List<AddressBean> addresses = unit.getAddress();
		List<AddressBean> newaddresses = new ArrayList<AddressBean>();
		
		if( addresses != null ){
			for( AddressBean address : addresses){
				address.setResidentId(resident.getResidentId().getKeyId());
				newaddresses.add(addressEntityServiceImpl.saveOrUpdateAddressEntity(control, address));
			}
			unit.setAddress(newaddresses);
		}
		
		List<ContactBean> contacts = unit.getContacts();
		List<ContactBean> newContacts = new ArrayList<ContactBean>();
		if( contacts != null ){
			for( ContactBean contact : contacts){
				contact.setResidentId(resident.getResidentId().getKeyId());
				newContacts.add(contactEntityServiceImpl.saveOrUpdateContactEntity(control, contact));
			}
			unit.setContacts(newContacts);
		}
		
		List<PropertyBean> assets = unit.getResProperty();
		if( assets != null ){
			ResPropMapEntity resPropMapEntity = null;
			ResPropKey propKey = null;
			for( PropertyBean asset : assets){
				propKey = new ResPropKey(resident.getResidentId().getTenantId(),resident.getResidentId().getKeyId(),asset.getKeyId());
				resPropMapEntity = new ResPropMapEntity();
				resPropMapEntity.setEndDate(asset.getEndDate());
				resPropMapEntity.setStartDate(asset.getStartDate());
				resPropMapEntity.setOwnershipType(asset.getOwnershipType());
				resPropMapEntity.setTenantCount(asset.getTenantCount());
				resPropMapEntity.setId(propKey);
				
				resPropMapEntity = resPropMapEntityServiceImpl.saveOrUpdateResPropMapEntity(control, resPropMapEntity);
			}
		}
		
		unit.convertToBeanObject(resident);
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
		postExecute(control, "saveOrUpdateResidentEntity");
		return unit;
	}

	@Override
	public void deleteResident(MasterControl control, EntityIntKey id) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, null);
		preExecute(control, "delete");
		
		residentEntityRepository.deleteById(id);
		
		postExecute(control, "delete");
	}

	@Override
	public void validateObject(MasterControl masterControl)
			throws InvalidInputException {

		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		
		Object bean = masterControl.getFromControl(MasterControl.CURRENT_BEAN);
		if( bean != null ){
			ResidentBean unit = (ResidentBean)bean;
			Object obj = masterControl.getFromControl(MasterControl.LANG_ID);
			int langId = -1;
			
			if( obj != null){
				langId = (Integer)obj;
			}
			else{
				langId = SspConstants.DEFAULT_LANG;
			}
			
			List<PropertyBean> propertyBeans = unit.getResProperty();
			for(PropertyBean propertyBean : propertyBeans ){
				if( propertyBean.getOwnershipType() < 0 ){
					String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_005.toString(), new String[]{"OwnershipType"});
					throw new InvalidInputException(message);
				}
				else{
					RefEntityIntKey refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),propertyBean.getOwnershipType(),langId);
					
					RefOwnershipType refOwnershipType = ownerTypeService.getRefTypeById(masterControl, refEntityIntKey);
					if( refOwnershipType == null ){
						String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_006.toString(), new String[]{String.valueOf(propertyBean.getOwnershipType()),"OwnershipType"});
						throw new InvalidInputException(message);
					}
					else{
						propertyBean.setOwnershipValue(refOwnershipType.getStatusValue());
					}
				}
			}
			
			if( unit.getNameTitleType() < 0 ){
				String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_005.toString(), new String[]{"NameTitleType"});
				throw new InvalidInputException(message);
			}
			else{
				RefEntityIntKey refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),unit.getNameTitleType(),langId);
				
				RefTitleType refTitleType = titleTypeService.getRefTypeById(masterControl, refEntityIntKey);
				if( refTitleType == null ){
					String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_006.toString(), new String[]{String.valueOf(unit.getNameTitleType()),"NameTitleType"});
					throw new InvalidInputException(message);
				}
				else{
					unit.setNameTitleValue(refTitleType.getStatusValue());
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
				List<ResidentBean> units = (List<ResidentBean>)masterControl.getFromControl(MasterControl.CURRENT_BEAN);
				
				for(ResidentBean unit : units){
					setRefDataValue(masterControl, unit, langId);
				}
			}
			else{
				ResidentBean unit = (ResidentBean)masterControl.getFromControl(MasterControl.CURRENT_BEAN);
				setRefDataValue(masterControl, unit, langId);
			}
			
		}
		
	}

	private void setRefDataValue(MasterControl masterControl, ResidentBean unit, int langId){
		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		
		try{
			List<PropertyBean> propertyBeans = unit.getResProperty();
			
			for( PropertyBean propertyBean : propertyBeans ){
				RefEntityIntKey refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),propertyBean.getOwnershipType(),langId);
				RefOwnershipType refOwnershipType = ownerTypeService.getRefTypeById(masterControl, refEntityIntKey);
				propertyBean.setOwnershipValue(refOwnershipType.getStatusValue());
			}
			
			RefEntityIntKey refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),unit.getNameTitleType(),langId);
			RefTitleType refTitleType = titleTypeService.getRefTypeById(masterControl, refEntityIntKey);
			unit.setNameTitleValue(refTitleType.getStatusValue());
			
		}
		catch(InvalidInputException iie){
			String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_007.toString());
			GenLogUtility.logMessage(masterControl, GenLogUtility.LogLevel.WARNING, this.getClass().getName(), "postTxnAction", message, iie);
		}
	}
}
