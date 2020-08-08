package com.ssp.maintainance.meta.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.ssp.maintainance.exception.InvalidInputException;
import com.ssp.maintainance.meta.keys.RefEntityIntKey;

@Entity
@Table(name = "r_unit_tp")
public class RefUnitType extends RefMasterRootEntity<RefEntityIntKey>{

	private float area;
    
    private String specData;
    
    @EmbeddedId
	private RefEntityIntKey unitId;
    
	@Transient
	private JSONObject json;
	
	@Transient
	private JSONParser parser = new JSONParser();

	public RefEntityIntKey getUnitId() {
		return unitId;
	}

	public void setUnitId(RefEntityIntKey unitId) {
		this.unitId = unitId;
	}

	public float getArea() {
		return area;
	}

	public void setArea(float area) {
		this.area = area;
	}

	public String getSpecData() {
		if( json != null ){
			specData = json.toString();
		}
		
		return specData;
	}

	public void addSpec(String key, String value){
		if( json == null ){
			json = new JSONObject();
		}
		
		json.put(key, value);	
	}

	public void setSpecData(String specData) throws InvalidInputException{
		this.specData = specData;	
	}
	
	@Override
	public void validateEntity(MasterControl masterControl) throws InvalidInputException{
		super.validateEntity(masterControl);
	}
}
