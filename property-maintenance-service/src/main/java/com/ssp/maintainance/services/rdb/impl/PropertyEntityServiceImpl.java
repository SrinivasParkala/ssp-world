package com.ssp.maintainance.services.rdb.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssp.maintainance.beans.PropertyBean;
import com.ssp.maintainance.beans.PropertyEntity;
import com.ssp.maintainance.beans.ResPropMapEntity;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.RefPropertyStatusType;
import com.ssp.maintainance.meta.beans.RefUnitType;
import com.ssp.maintainance.meta.keys.EntityStringKey;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.services.AbstractService;
import com.ssp.maintainance.meta.services.rdb.impl.RefPropertyServiceImpl;
import com.ssp.maintainance.meta.services.rdb.impl.RefUnitServiceImpl;
import com.ssp.maintainance.repositories.PropertyEntityRepository;
import com.ssp.maintainance.services.MessageByLocaleService;
import com.ssp.maintainance.services.PropertyEntityService;
import com.ssp.maintainance.util.SspConstants;
import com.ssp.maintainance.util.GenLogUtility;
import com.ssp.maintainance.util.MessageConts;

@Service
public class PropertyEntityServiceImpl extends AbstractService implements PropertyEntityService{

	private PropertyEntityRepository propertyEntityRepository;

	@Autowired
	RefPropertyServiceImpl propertyStatusService;
	
	@Autowired
	RefUnitServiceImpl unitService;
	
	@Autowired
	ResPropMapEntityServiceImpl resPropMapEntityServiceImpl;
	
	@Autowired
	public PropertyEntityServiceImpl(PropertyEntityRepository propertyEntityRepository) {
		this.propertyEntityRepository = propertyEntityRepository;
	}

	@Override
	public List<PropertyBean>  getPropertyEntities(MasterControl control, int langId) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, null);
		preExecute(control, "getPropertyEntities");
		
		List<PropertyBean> units = new ArrayList<>();
		PropertyBean propertyBean = null;
		Iterator<PropertyEntity> iterator = propertyEntityRepository.findAll().iterator();
		while(iterator.hasNext()){
			propertyBean = new PropertyBean();
			propertyBean.convertToBeanObject(iterator.next());
			units.add(propertyBean);
		}
		
		control.addToControl(MasterControl.CURRENT_BEAN, units);
		postExecute(control, "getPropertyEntities");
		return units;
	}

	@Override
	public PropertyBean gePropertyEntityId(MasterControl control, EntityStringKey id,int langId) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, null);
		preExecute(control, "gePropertyEntityId");
		
		Optional<PropertyEntity> optional =  propertyEntityRepository.findById(id);
		
		List<PropertyEntity> properties = new ArrayList<PropertyEntity>();
		properties.add(optional.get());
		
		PropertyBean propertyBean = new PropertyBean();
		propertyBean.convertToBeanObject(optional.get());
		
		control.addToControl(MasterControl.CURRENT_BEAN, propertyBean);
		postExecute(control, "gePropertyEntityId");
		
		return propertyBean;
	}

	@Override
	public PropertyBean saveOrUpdatePropertyEntity(MasterControl control, PropertyBean unit) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
		preExecute(control, "saveOrUpdatePropertyEntity");
		
		PropertyEntity property = propertyEntityRepository.save(unit.convertToEntityObject());
		unit.convertToBeanObject(property);
		
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
		postExecute(control, "saveOrUpdatePropertyEntity");
		return unit;
	}

	@Override
	public void delete(MasterControl control, EntityStringKey id) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, null);
		preExecute(control, "delete");
		
		propertyEntityRepository.deleteById(id);
		
		postExecute(control, "delete");
	}

	@Override
	public void validateObject(MasterControl masterControl)
			throws InvalidInputException {

		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		
		Object bean = masterControl.getFromControl(MasterControl.CURRENT_BEAN);
		if( bean != null ){
			PropertyBean unit = (PropertyBean)bean;
			Object obj = masterControl.getFromControl(MasterControl.LANG_ID);
			int langId = -1;
			
			if( obj != null){
				langId = (Integer)obj;
			}
			else{
				langId = SspConstants.DEFAULT_LANG;
			}
			
			if( unit.getStatusType() < 0 ){
				String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_005.toString(), new String[]{"PropertyStatusType"});
				throw new InvalidInputException(message);
			}
			else{
				RefEntityIntKey refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),unit.getStatusType(),langId);
				
				RefPropertyStatusType refPropertyStatusType = propertyStatusService.getRefTypeById(masterControl, refEntityIntKey);
				if( refPropertyStatusType == null ){
					String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_006.toString(), new String[]{String.valueOf(unit.getStatusType()),"PropertyStatusType"});
					throw new InvalidInputException(message);
				}
				else{
					unit.setStatusValue(refPropertyStatusType.getStatusValue());
				}
			}
			
			if( unit.getUnitType() < 0 ){
				String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_005.toString(), new String[]{"UnitType"});
				throw new InvalidInputException(message);
			}
			else{	
				RefEntityIntKey refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),unit.getUnitType(),langId);
				
				RefUnitType refUnitType = unitService.getRefTypeById(masterControl, refEntityIntKey);
				if( refUnitType == null ){
					String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_006.toString(), new String[]{String.valueOf(unit.getUnitType()),"UnitType"});
					throw new InvalidInputException(message);
				}
				else{
					unit.setUnitValue(refUnitType.getStatusValue());
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
				List<PropertyBean> units = (List<PropertyBean>)masterControl.getFromControl(MasterControl.CURRENT_BEAN);
				
				for(PropertyBean unit : units){
					getRefDataValue(masterControl, unit, langId);
				}
			}
			else{
				PropertyBean unit = (PropertyBean)masterControl.getFromControl(MasterControl.CURRENT_BEAN);
				getRefDataValue(masterControl, unit, langId);
			}
			
		}
	
	}

	private void getRefDataValue(MasterControl masterControl, PropertyBean unit, int langId){
		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		
		try{
			RefEntityIntKey refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),unit.getStatusType(),langId);
			
			RefPropertyStatusType refPropertyStatusType = propertyStatusService.getRefTypeById(masterControl, refEntityIntKey);
			unit.setStatusValue(refPropertyStatusType.getStatusValue());
			
			refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),unit.getUnitType(),langId);
			RefUnitType refUnitType = unitService.getRefTypeById(masterControl, refEntityIntKey);
			unit.setUnitValue(refUnitType.getStatusValue());
			unit.setUnitSpecification(refUnitType.getSpecData());
			
			List<ResPropMapEntity> properties = resPropMapEntityServiceImpl.getAllResPropMapEntities(masterControl, unit.getTenantId(), unit.getKeyId(), langId);
			if( properties != null ){
						
				for(ResPropMapEntity property :properties ){
					unit.setOwnershipType(property.getOwnershipType());
					unit.setStartDate(property.getStartDate());
					unit.setEndDate(property.getEndDate());
					unit.setTenantCount(property.getTenantCount());
				}//for
			}
		}
		catch(InvalidInputException iie){
			String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_007.toString());
			GenLogUtility.logMessage(masterControl, GenLogUtility.LogLevel.WARNING, this.getClass().getName(), "postTxnAction", message, iie);
		}
	}
}
