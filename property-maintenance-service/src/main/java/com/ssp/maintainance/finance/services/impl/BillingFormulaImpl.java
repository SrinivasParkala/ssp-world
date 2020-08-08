package com.ssp.maintainance.finance.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.finance.beans.BillingCategoryBean;
import com.ssp.maintainance.finance.beans.BillingFormulaBean;
import com.ssp.maintainance.finance.beans.BillingFormulaEntity;
import com.ssp.maintainance.finance.repositories.BillingFormulaRepository;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.services.AbstractService;
import com.ssp.maintainance.services.MessageByLocaleService;
import com.ssp.maintainance.util.SspConstants;
import com.ssp.maintainance.util.MessageConts;

@Service
public class BillingFormulaImpl extends AbstractService implements BillingFormulaService {

	private BillingFormulaRepository t;

	@Autowired
	BillingCategoryImpl	billingCategoryImpl;
	
	@Autowired
	public BillingFormulaImpl(BillingFormulaRepository t) {
		this.t = t;
	}

	@Override
	public BillingFormulaEntity getBillingFormulaById(MasterControl control, RefEntityIntKey id) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "getBillingFormulaById");
		
		BillingFormulaEntity billingFormulaEntity = null;
		Optional<BillingFormulaEntity> optional =  t.findById(id);
		
		if( optional.isPresent() ){
			billingFormulaEntity = optional.get();
		}
		
		control.addToControl(MasterControl.CURRENT_OBJECT, billingFormulaEntity);
		postExecute(control, "getBillingFormulaById");
		
		return billingFormulaEntity;
	}

	@Override
	public BillingFormulaEntity saveOrUpdateBillingFormula(
			MasterControl control, BillingFormulaEntity unit)
			throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		preExecute(control, "saveOrUpdateBillingFormula");
		t.save(unit);
		postExecute(control, "saveOrUpdateBillingFormula");
		return unit;
	}

	@Override
	public void deleteBillingFormulaEntity(MasterControl control, RefEntityIntKey id)
			throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "deleteBillingFormulaEntity");
		t.deleteById(id);
		postExecute(control, "deleteBillingFormulaEntity");
		
	}

	@Override
	public void validateObject(MasterControl masterControl) throws InvalidInputException {
		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		Object bean = masterControl.getFromControl(MasterControl.CURRENT_OBJECT);
	
		if( bean != null ){
			BillingFormulaEntity unit = (BillingFormulaEntity)bean;
			Object obj = masterControl.getFromControl(MasterControl.LANG_ID);
			int langId = -1;
			
			if( obj != null){
				langId = (Integer)obj;
			}
			else{
				langId = SspConstants.DEFAULT_LANG;
			}	
			
			BillingFormulaBean billingFormulaBean = new BillingFormulaBean();
			billingFormulaBean.convertToBeanObject(unit);
			
			List<Pair<Integer, Integer>> charges = billingFormulaBean.getChargeList();
			
			List<BillingCategoryBean> categoryBeans = billingCategoryImpl.getAllBillingCategory(masterControl);
			Set<Integer> keys = new HashSet<Integer>();
			for( BillingCategoryBean categoryBean : categoryBeans){
				if( categoryBean.getFormulaId() < 1 ){
					keys.add(Integer.valueOf(categoryBean.getKeyId()));
				}
			}//for

			for( Pair<Integer, Integer> p : charges){
				if( !keys.contains(p.getFirst()) ){
					String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_009.toString(), new String[]{String.valueOf(p.getFirst())});
					throw new InvalidInputException(message);
				}//if
			}//for
		}//if
		
	}

	@Override
	public void postTxnAction(MasterControl masterControl) {
		// TODO Auto-generated method stub
		
	}
	
}
