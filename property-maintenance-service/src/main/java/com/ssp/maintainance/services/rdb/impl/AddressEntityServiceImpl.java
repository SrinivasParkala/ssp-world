package com.ssp.maintainance.services.rdb.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssp.maintainance.beans.AddressBean;
import com.ssp.maintainance.beans.AddressEntity;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.RefAddressType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.keys.ResidentChildEntityKey;
import com.ssp.maintainance.meta.services.AbstractService;
import com.ssp.maintainance.meta.services.rdb.impl.RefAddressServiceImpl;
import com.ssp.maintainance.repositories.AddressEntityRepository;
import com.ssp.maintainance.services.AddressEntityService;
import com.ssp.maintainance.services.MessageByLocaleService;
import com.ssp.maintainance.util.SspConstants;
import com.ssp.maintainance.util.GenLogUtility;
import com.ssp.maintainance.util.MessageConts;

@Service
public class AddressEntityServiceImpl extends AbstractService implements AddressEntityService{

	private AddressEntityRepository addressEntityRepository;

	@Autowired
	RefAddressServiceImpl addressTypeService;
	
	@Autowired
	public AddressEntityServiceImpl(AddressEntityRepository addressEntityRepository) {
		this.addressEntityRepository = addressEntityRepository;
	}

	@Override
	public List<AddressBean>  getAllAddressByResidentId(MasterControl control, String tenantId, int residentId, int langId) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, null);
		preExecute(control, "getAllAddressByResident");
		
		List<AddressBean> units = new ArrayList<>();
		AddressBean AddressBean = null;
		Iterator<AddressEntity> iterator = addressEntityRepository.getAllAddressByResidentId(tenantId, residentId).iterator();
		while(iterator.hasNext()){
			AddressBean = new AddressBean();
			AddressBean.convertToBeanObject(iterator.next());
			units.add(AddressBean);
		}
		
		control.addToControl(MasterControl.CURRENT_BEAN, units);
		postExecute(control, "getAllAddressByResident");
		return units;
	}

	@Override
	public AddressBean getAddressByResidentId(MasterControl control, ResidentChildEntityKey id, int langId) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, null);
		preExecute(control, "getAddressByResident");
		
		Optional<AddressEntity> optional =  addressEntityRepository.findById(id);
		
		AddressBean addressBean = new AddressBean();
		addressBean.convertToBeanObject(optional.get());
		
		control.addToControl(MasterControl.CURRENT_BEAN, addressBean);
		postExecute(control, "getAddressByResident");
		
		return addressBean;
	}

	@Override
	public AddressBean saveOrUpdateAddressEntity(MasterControl control, AddressBean unit) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
		preExecute(control, "saveOrUpdateAddressEntity");
		
		AddressEntity address = addressEntityRepository.save(unit.convertToEntityObject());
		
		unit.convertToBeanObject(address);
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
		postExecute(control, "saveOrUpdateAddressEntity");
		return unit;
	}

	@Override
	public void deleteAddress(MasterControl control, ResidentChildEntityKey id) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, null);
		preExecute(control, "delete");
		
		addressEntityRepository.deleteById(id);
		
		postExecute(control, "delete");
	}

	@Override
	public void validateObject(MasterControl masterControl)	throws InvalidInputException {

		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		
		Object bean = masterControl.getFromControl(MasterControl.CURRENT_BEAN);
		if( bean != null ){
			AddressBean unit = (AddressBean)bean;
			Object obj = masterControl.getFromControl(MasterControl.LANG_ID);
			int langId = -1;
			
			if( obj != null){
				langId = (Integer)obj;
			}
			else{
				langId = SspConstants.DEFAULT_LANG;
			}
			
			if( unit.getAddressType() < 0 ){
				String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_005.toString(), new String[]{"AddressType"});
				throw new InvalidInputException(message);
			}
			else{
				RefEntityIntKey refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),unit.getAddressType(),langId);
				
				RefAddressType refAddressType = addressTypeService.getRefTypeById(masterControl, refEntityIntKey);
				if( refAddressType == null ){
					String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_006.toString(), new String[]{String.valueOf(unit.getAddressType()),"AddressType"});
					throw new InvalidInputException(message);
				}
				else{
					unit.setAddressValue(refAddressType.getStatusValue());
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
				List<AddressBean> units = (List<AddressBean>)masterControl.getFromControl(MasterControl.CURRENT_BEAN);
				
				for(AddressBean unit : units){
					getRefDataValue(masterControl, unit, langId);
				}
			}
			else{
				AddressBean unit = (AddressBean)masterControl.getFromControl(MasterControl.CURRENT_BEAN);
				getRefDataValue(masterControl, unit, langId);
			}
			
		}
	}

	private void getRefDataValue(MasterControl masterControl, AddressBean unit, int langId){
		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		
		try{
			RefEntityIntKey refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),unit.getAddressType(),langId);
			
			RefAddressType refAddressType = addressTypeService.getRefTypeById(masterControl, refEntityIntKey);
			unit.setAddressValue(refAddressType.getStatusValue());
		}
		catch(InvalidInputException iie){
			String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_007.toString());
			GenLogUtility.logMessage(masterControl, GenLogUtility.LogLevel.WARNING, this.getClass().getName(), "postTxnAction", message, iie);
		}
	}
}
