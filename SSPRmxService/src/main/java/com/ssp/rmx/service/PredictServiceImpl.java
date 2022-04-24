package com.ssp.rmx.service;

import java.io.File;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ssp.rmx.sdo.PythonPredictRequest;
import com.ssp.rmx.sdo.RmxPredictRequest;

@Service
public class PredictServiceImpl implements PredictService{

	public static final String MODEL_NAME = "finalized_model.sav";
	public static final String APP_HOME = "SPRING_BOOT_MODEL_PATH";
	
	public static final String GLOBAL_TENANT = "all";
	
	@Autowired
  private RestTemplate restTemplate;
	
	@Value("${internalService.pythonServiceUrl}")
	private String pythonServiceUrl;
	
	@Override
	public ResponseEntity<String> predictValues(RmxPredictRequest predictRequest, String tenantId) {
		
		PythonPredictRequest pythonPredictRequest = convertToPythonRequest(predictRequest, tenantId);
		HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    
    ResponseEntity<String> response = restTemplate.postForEntity(pythonServiceUrl, pythonPredictRequest, String.class, headers);
		
		return response;
	}

	private PythonPredictRequest convertToPythonRequest(RmxPredictRequest predictRequest, String tenantId) {
		String storagePath = System.getenv(APP_HOME);
		
		String modelPath = storagePath+File.separator+tenantId+File.separator+predictRequest.getCategory()+File.separator+predictRequest.getSubCategory()+File.separator+predictRequest.getGrade()+File.separator+MODEL_NAME;
		
		File f = new File(modelPath);
		
		if( !f.exists() ) {
			modelPath = storagePath+File.separator+GLOBAL_TENANT+File.separator+predictRequest.getCategory()+File.separator+predictRequest.getSubCategory()+File.separator+predictRequest.getGrade()+File.separator+MODEL_NAME;
		}
				
		PythonPredictRequest pythonPredictRequest = new PythonPredictRequest();
		
		pythonPredictRequest.setPath(modelPath);
		pythonPredictRequest.setyArrayInput(predictRequest.getHeaders());
		pythonPredictRequest.setxArrayInput(predictRequest.getRequestValues());
		
		return pythonPredictRequest;
	}
	
}
