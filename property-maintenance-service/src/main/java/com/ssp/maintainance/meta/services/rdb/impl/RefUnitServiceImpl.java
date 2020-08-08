package com.ssp.maintainance.meta.services.rdb.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.beans.MasterControl;
import com.ssp.maintainance.meta.beans.RefUnitSpecType;
import com.ssp.maintainance.meta.beans.RefUnitType;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;
import com.ssp.maintainance.meta.repositories.UnitTypeRepository;
import com.ssp.maintainance.meta.services.AbstractService;
import com.ssp.maintainance.meta.services.RefTypeService;
import com.ssp.maintainance.services.MessageByLocaleService;
import com.ssp.maintainance.util.MessageConts;
import com.ssp.maintainance.util.StringUtil;

@Service
public class RefUnitServiceImpl extends AbstractService implements RefTypeService<RefUnitType> {

	private UnitTypeRepository t;

	private JSONParser parser = new JSONParser();
	
	@Autowired
	private RefUnitSpecServiceImpl refUnitSpecServiceImpl;
	
	@Autowired
	public RefUnitServiceImpl(UnitTypeRepository t) {
		this.t = t;
	}

	@Override
	public List<RefUnitType> getAllRefType(MasterControl control) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "getAllRefType");
		
		List<RefUnitType> refDatas = new ArrayList<>();
		Iterator<RefUnitType> iterator = t.findAll().iterator();
		while(iterator.hasNext()){
			refDatas.add(iterator.next());
		}
		
		postExecute(control, "getAllRefType");
		return refDatas;
	}

	@Override
	public RefUnitType getRefTypeById(MasterControl control, RefEntityIntKey id) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "getRefTypeById");
		
		Optional<RefUnitType> optional =  t.findById(id);
		
		postExecute(control, "getRefTypeById");
		RefUnitType refStatusType = null;
		if( optional.isPresent() ){
			refStatusType = optional.get();
		}
		
		return refStatusType;
	}

	@Override
	public RefUnitType saveOrUpdateRefType(MasterControl control, RefUnitType refData) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, refData);
		preExecute(control, "saveOrUpdateRefType");
		t.save(refData);
		postExecute(control, "saveOrUpdateRefType");
		return refData;
	}

	@Override
	public void deleteRefType(MasterControl control, RefEntityIntKey id) throws InvalidInputException {
		control.addToControl(MasterControl.CURRENT_OBJECT, null);
		preExecute(control, "deleteRefType");
		t.deleteById(id);
		postExecute(control, "deleteRefType");
	}

	@Override
	public void validateObject(MasterControl masterControl)
			throws InvalidInputException {


		MessageByLocaleService messageByLocaleService = (MessageByLocaleService)masterControl.getFromControl(MasterControl.MESSAGE_SERVICE);
		JSONObject json = null;
		
		if( masterControl.getFromControl(MasterControl.CURRENT_OBJECT) != null ){
			RefUnitType unit = (RefUnitType)masterControl.getFromControl(MasterControl.CURRENT_OBJECT);
			
			if( !StringUtil.isStringNull(unit.getSpecData())){
				
				try {
					json = (JSONObject) parser.parse(unit.getSpecData());
				} catch (ParseException e) {
					String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_004.toString(), new String[]{unit.getSpecData()});
					throw new InvalidInputException(message);
				}
				
				Set<String> values = new HashSet<String>();
				List<RefUnitSpecType> specs = refUnitSpecServiceImpl.getAllRefType(masterControl);
				if( specs != null ){
					
					for(RefUnitSpecType spec : specs){
						values.add(spec.getStatusValue());	
					}
				}
				
				Iterator<String> iter = json.keySet().iterator();
				while( iter.hasNext() ){
					String key = iter.next();
					if( !values.contains(key)){
						String message = messageByLocaleService.getMessage(MessageConts.SSP_ERR_004.toString(), new String[]{key});
						throw new InvalidInputException(message);
					}
				}
			}
		}
	}

	@Override
	public void postTxnAction(MasterControl masterControl) {
		
	}

}
