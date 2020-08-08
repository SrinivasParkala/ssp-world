package com.ssp.maintainance.finance.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.util.Pair;

import com.ssp.maintainance.beans.IGenericBean;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

public class BillingFormulaBean extends IGenericBean<BillingFormulaEntity>{

	private String description;
	
	private List<Pair<Integer, Integer>> chargeList;  
	private JSONParser parser = new JSONParser();
	
	public BillingFormulaBean(){
		chargeList = new ArrayList<Pair<Integer, Integer>>();
	}
	
	public List<Pair<Integer, Integer>> getChargeList(){
		return chargeList;
	}
	
	public String getChargeListAsString(){
		JSONObject json = new JSONObject();
		for(Pair<Integer, Integer> p : chargeList){
			json.put(p.getFirst(), p.getSecond());
		}
		return json.toJSONString();	
	}
	
	public void buidCharge(String changeValue){
		if( changeValue != null ){
			try {
				JSONObject json = (JSONObject) parser.parse(changeValue);
				Iterator iter = json.keySet().iterator();
				Pair<Integer, Integer> charge = null;
				while( iter.hasNext() ){
					Object key = iter.next();
					charge = Pair.of(Integer.valueOf(key.toString()), Integer.valueOf(json.get(key).toString()));
					addToCharge(charge);
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void addToCharge(Pair<Integer, Integer> charge){
		chargeList.add(charge);
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public BillingFormulaEntity convertToEntityObject() {
		BillingFormulaEntity billingFormulaEntity = new BillingFormulaEntity();
		
		RefEntityIntKey id =  new RefEntityIntKey(this.getTenantId(), Integer.valueOf(this.getKeyId()), 10);;
			
		billingFormulaEntity.setUnitId(id);
		billingFormulaEntity.setDescription(this.getDescription());
		billingFormulaEntity.setStatusValue(this.getChargeListAsString());
		
		return billingFormulaEntity;
	}

	@Override
	public void convertToBeanObject(BillingFormulaEntity entity) {
		this.setVersion(entity.getVersion());
		this.setKeyId(String.valueOf(entity.getUnitId().getKeyId()));
		this.setTenantId(entity.getUnitId().getTenantId());
		this.setLastUpdatedUser(entity.getLastUpdatedUser());
		this.buidCharge(entity.getStatusValue());
		this.setDescription(entity.getDescription());
	}

	public static void main(String[] arg){
		String value = "{\"4\":12,\"3\":1,\"1\":48}";
		
		BillingFormulaBean billingFormulaBean = new BillingFormulaBean();
		
		billingFormulaBean.buidCharge(value);
		System.out.println(billingFormulaBean.getChargeListAsString());
			
		System.out.println(billingFormulaBean.getChargeList());
	}
}
