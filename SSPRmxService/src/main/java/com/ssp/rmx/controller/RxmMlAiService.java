package com.ssp.rmx.controller;

import java.security.Principal;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ssp.rmx.config.ServiceInterceptor;

@RestController
@RequestMapping("/sspService")
public class RxmMlAiService {
	@Autowired
  private RestTemplate restTemplate;
	
	@Value("${internalService.pythonServiceUrl}")
	private String pythonServiceUrl;

	@RequestMapping(value = "/getOAuthDetails", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('superuser')")
  public ResponseEntity<Principal> get(final Principal principal, final HttpServletRequest request) {
			System.out.println("Tenant:"+request.getAttribute(ServiceInterceptor.TENANT_ID)+"\tUser:"+request.getAttribute(ServiceInterceptor.USER));
      return ResponseEntity.ok(principal);
  }
	
	@RequestMapping(value = "/mlService", method = RequestMethod.GET)
  @PreAuthorize("hasAnyAuthority('tenantadmin','superuser')")
  public String getMLService(final HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    
    HttpEntity<String> entity = new HttpEntity<String>(headers);
    
    return restTemplate.exchange(pythonServiceUrl, HttpMethod.GET, entity, String.class).getBody();
 }

}
