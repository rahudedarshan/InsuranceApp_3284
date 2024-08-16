package com.nd.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nd.binding.CitizenAppRegistrationInput;
import com.nd.entity.CitizenAppRegistartionEntity;
import com.nd.exception.SSNInputException;
import com.nd.repository.IApplicationRegistrationRepository;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class ICitizenRegistrationApplicationImplementation implements ICitizenApplicationRegistrationService {

	
	@Autowired
	private IApplicationRegistrationRepository appRepo;

	@Autowired
	private RestTemplate template;
	
	@Value("${ar.ssa-web.url}")
	private String endpointURL;
	@Value("${ar.state}")
	private String targetState;
	
	
	@Override
	public Integer registerCitizenApplication(CitizenAppRegistrationInput input) throws SSNInputException {
	
		/*
		
		//performs the webservice call whelther SSn is valid or not
		ResponseEntity<String> response = template.exchange(endpointURL, HttpMethod.GET,null,String.class,input.getSsn());
		
		String stateName = response.getBody();
		
		if(stateName.equalsIgnoreCase(targetState)) {
			
			CitizenAppRegistartionEntity entity = new CitizenAppRegistartionEntity();
			BeanUtils.copyProperties(input, entity);			
			int appId = appRepo.save(entity).getAppId();
		  
			return appId;
		}
		
		*/
		
		
		return 0;		
	}
	
		
		
		
	
}
