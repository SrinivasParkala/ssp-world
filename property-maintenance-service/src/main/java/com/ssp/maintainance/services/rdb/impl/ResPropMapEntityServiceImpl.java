package com.ssp.maintainance.services.rdb.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssp.maintainance.beans.ResPropMapEntity;
import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.keys.ResPropKey;
import com.ssp.maintainance.meta.services.AbstractService;
import com.ssp.maintainance.repositories.ResPropMapEntityRepository;
import com.ssp.maintainance.services.MessageByLocaleService;
import com.ssp.maintainance.services.ResPropMapEntityService;
import com.ssp.maintainance.util.MessageConts;

@Service
public class ResPropMapEntityServiceImpl extends AbstractService implements ResPropMapEntityService{

	private ResPropMapEntityRepository resPropMapEntityRepository;
	
	@Autowired
	public ResPropMapEntityServiceImpl(ResPropMapEntityRepository resPropMapEntityRepository) {
		this.resPropMapEntityRepository = resPropMapEntityRepository;
	}
	
	@Override
	public List<ResPropMapEntity> getAllResPropMapEntities(MasterControl control, String tentantId, String residentId, int langId) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "getAllResPropMapEntities");
		
		List<ResPropMapEntity> units = new ArrayList<>();
		Iterator<ResPropMapEntity> iterator = resPropMapEntityRepository.getAllPropertyByResidentId(tentantId, Integer.valueOf(residentId)).iterator();
		while(iterator.hasNext()){
			units.add(iterator.next());
		}
		
		control.addToControl(MasterControl.CURRENT_OBJECT, units);
		postExecute(control, "getAllResPropMapEntities");
		return units;
	}

	@Override
	public ResPropMapEntity geResPropMapById(MasterControl control,	ResPropKey id, int langId) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "geResPropMapById");
		
		Optional<ResPropMapEntity> optional =  resPropMapEntityRepository.findById(id);
		
		control.addToControl(MasterControl.CURRENT_OBJECT, optional.get());
		postExecute(control, "geResPropMapById");
		
		return optional.get();
	}

	@Override
	public ResPropMapEntity saveOrUpdateResPropMapEntity(MasterControl control, ResPropMapEntity unit) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		preExecute(control, "saveOrUpdateResPropMapEntity");
		
		unit = resPropMapEntityRepository.save(unit);
		
		control.addToControl(MasterControl.CURRENT_OBJECT, unit);
		postExecute(control, "saveOrUpdateResPropMapEntity");
		return unit;
	}

	@Override
	public void deleteResPropMap(MasterControl control, ResPropKey id) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "deleteResPropMap");
		
		resPropMapEntityRepository.deleteById(id);
		
		postExecute(control, "deleteResPropMap");
	}

	@Override
	public void validateObject(MasterControl masterControl) throws InvalidInputException {
		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		
		Object bean = masterControl.getFromControl(MasterControl.CURRENT_OBJECT);
		if( bean != null ){
			ResPropMapEntity unit = (ResPropMapEntity)bean;
			
			if( unit.getOwnershipType() < 0 ){
				String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_005.toString(), new String[]{"OwnershipType"});
				throw new InvalidInputException(message);
			}
		}
		
	}

	@Override
	public void postTxnAction(MasterControl masterControl) {
		// TODO Auto-generated method stub
		
	}

}
