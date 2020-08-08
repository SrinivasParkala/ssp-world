package com.ssp.maintainance.finance.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.finance.beans.BillingCategoryBean;
import com.ssp.maintainance.finance.beans.BillingCategoryEntity;
import com.ssp.maintainance.finance.beans.BillingFormulaEntity;
import com.ssp.maintainance.finance.beans.RefBillingCatType;
import com.ssp.maintainance.finance.beans.RefBillingType;
import com.ssp.maintainance.finance.beans.RefPeriodicType;
import com.ssp.maintainance.finance.repositories.BillingCategoryRepository;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.keys.EntityIntKey;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.services.AbstractService;
import com.ssp.maintainance.services.MessageByLocaleService;
import com.ssp.maintainance.util.SspConstants;
import com.ssp.maintainance.util.GenLogUtility;
import com.ssp.maintainance.util.MessageConts;

@Service
public class BillingCategoryImpl extends AbstractService implements BillingCategoryService {

	private BillingCategoryRepository t;

	@Autowired
	RefBillingImpl refBillingImpl;
	
	@Autowired
	RefPeriodicImpl refPeriodicImpl;
	
	@Autowired
	BillingFormulaImpl billingFormulaImpl;
	
	@Autowired
	RefBillingCatImpl refBillingCatImpl;
	
	@Autowired
	public BillingCategoryImpl(BillingCategoryRepository t) {
		this.t = t;
	}

	@Override
	public BillingCategoryBean getBillingCategoryById(MasterControl control, EntityIntKey id) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, null);
		preExecute(control, "getBillingCategoryById");
		
		BillingCategoryEntity billingCatelogEntity = null;
		Optional<BillingCategoryEntity> optional =  t.findById(id);
		
		if( optional.isPresent() ){
			billingCatelogEntity = optional.get();
		}
		
		BillingCategoryBean billingCategoryBean = new BillingCategoryBean();
		billingCategoryBean.convertToBeanObject(billingCatelogEntity);
		
		control.addToControl(MasterControl.CURRENT_BEAN, billingCategoryBean);
		postExecute(control, "getBillingCategoryById");
		
		return billingCategoryBean;
	}

	@Override
	public BillingCategoryBean saveOrUpdateBillingCategory(MasterControl control, BillingCategoryBean unit) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, unit);
		preExecute(control, "saveOrUpdateBillingCategory");
		t.save(unit.convertToEntityObject());
		postExecute(control, "saveOrUpdateBillingCategory");
		return unit;
	}

	@Override
	public void deleteBillCatergoryEntity(MasterControl control, EntityIntKey id) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, null);
		preExecute(control, "deleteBillCatergoryEntity");
		t.deleteById(id);
		postExecute(control, "deleteBillCatergoryEntity");
		
	}

	@Override
	public void validateObject(MasterControl masterControl) throws InvalidInputException {
		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		
		Object bean = masterControl.getFromControl(MasterControl.CURRENT_BEAN);
		if( bean != null ){
			BillingCategoryBean unit = (BillingCategoryBean)bean;
			Object obj = masterControl.getFromControl(MasterControl.LANG_ID);
			int langId = -1;
			
			if( obj != null){
				langId = (Integer)obj;
			}
			else{
				langId = SspConstants.DEFAULT_LANG;
			}	
			
			if( unit.getBillingType() < 0 ){
				String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_005.toString(), new String[]{"BillingType"});
				throw new InvalidInputException(message);
			}
			else{
				RefEntityIntKey refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),unit.getBillingType(),langId);
				
				RefBillingType refBillingType = refBillingImpl.getRefTypeById(masterControl, refEntityIntKey);
				if( refBillingType == null ){
					String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_006.toString(), new String[]{String.valueOf(unit.getBillingType()),"BillingType"});
					throw new InvalidInputException(message);
				}
				else{
					unit.setBillingValue(refBillingType.getStatusValue());
				}
			}
			
			if( unit.getPeriodicType() < 0 ){
				String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_005.toString(), new String[]{"PeriodicType"});
				throw new InvalidInputException(message);
			}
			else{
				RefEntityIntKey refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),unit.getPeriodicType(),langId);
				
				RefPeriodicType refPeriodicType = refPeriodicImpl.getRefTypeById(masterControl, refEntityIntKey);
				if( refPeriodicType == null ){
					String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_006.toString(), new String[]{String.valueOf(unit.getPeriodicType()),"PeriodicType"});
					throw new InvalidInputException(message);
				}
				else{
					unit.setPeriodicValue(refPeriodicType.getStatusValue());
				}
			}
			
			if( unit.getFormulaId() > 0  ){
				RefEntityIntKey refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),unit.getFormulaId(),langId);
				BillingFormulaEntity billingFormulaEntity = billingFormulaImpl.getBillingFormulaById(masterControl, refEntityIntKey);
				unit.setFormulaObject(billingFormulaEntity.getStatusValue());
			}
			
		}//if
		
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
			
			if( bean instanceof BillingCategoryBean ){
				BillingCategoryBean unit = (BillingCategoryBean)bean;
				getRefDataValue(masterControl, unit, langId);
			}
			else if( bean instanceof List ){
				List<BillingCategoryBean> lists = (List<BillingCategoryBean>)bean;
				for( BillingCategoryBean b : lists){
					BillingCategoryBean unit = (BillingCategoryBean)b;
					getRefDataValue(masterControl, unit, langId);
				}
			}
			
		}
	}
	
	private void getRefDataValue(MasterControl masterControl, BillingCategoryBean unit, int langId){
		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		
		try{
			RefEntityIntKey refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),unit.getBillingType(),langId);
			RefBillingType refBillingType = refBillingImpl.getRefTypeById(masterControl, refEntityIntKey);
			unit.setBillingValue(refBillingType.getStatusValue());
			unit.setAppliableOnce(refBillingType.isAppliableOnce());
			unit.setFixed(refBillingType.getFixed());
			
			refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),refBillingType.getBillingCatType(),langId);
			RefBillingCatType refBillingCatType = refBillingCatImpl.getRefTypeById(masterControl, refEntityIntKey);
			unit.setBillingCatValue(refBillingCatType.getStatusValue());
			
			refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),unit.getPeriodicType(),langId);
			RefPeriodicType refPeriodicType = refPeriodicImpl.getRefTypeById(masterControl, refEntityIntKey);
			unit.setPeriodicValue(refPeriodicType.getStatusValue());
			
			if( unit.getFormulaId() > 0 ){
				refEntityIntKey = new RefEntityIntKey(unit.getTenantId(),unit.getFormulaId(),langId);
				BillingFormulaEntity billingFormulaEntity = billingFormulaImpl.getBillingFormulaById(masterControl, refEntityIntKey);
				unit.setFormulaObject(billingFormulaEntity.getStatusValue());
			}
			
		}
		catch(InvalidInputException iie){
			String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_007.toString());
			GenLogUtility.logMessage(masterControl, GenLogUtility.LogLevel.WARNING, this.getClass().getName(), "postTxnAction", message, iie);
		}
	}

	@Override
	public List<BillingCategoryBean> getAllBillingCategory(MasterControl control) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_BEAN, null);
		preExecute(control, "getAllBillingCategory");
		
		Iterable<BillingCategoryEntity> optional =  t.findAll();
		
		List<BillingCategoryBean> billingCategoryBeans = new ArrayList<BillingCategoryBean>();
		
		if( optional != null ){
			Iterator<BillingCategoryEntity> iter = optional.iterator();
			BillingCategoryBean billingCategoryBean = null; 
			while( iter.hasNext() ){
				billingCategoryBean = new BillingCategoryBean();
				billingCategoryBean.convertToBeanObject(iter.next());
				billingCategoryBeans.add(billingCategoryBean);
			}
		}
		
		control.addToControl(MasterControl.CURRENT_BEAN, billingCategoryBeans);
		postExecute(control, "getAllBillingCategory");
		
		return billingCategoryBeans;
	}
	
	
	
}
