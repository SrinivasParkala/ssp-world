package com.ssp.rmx.service;

import org.springframework.http.ResponseEntity;

import com.ssp.rmx.sdo.RmxPredictRequest;

public interface PredictService {
	public abstract ResponseEntity<String> predictValues(RmxPredictRequest predictRequest, String tenantId);
}
