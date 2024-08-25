package com.nd.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.nd.binding.CitizenAppRegistrationInput;
import com.nd.entity.CitizenAppRegistartionEntity;
import com.nd.exception.SSNInputException;
import com.nd.repository.IApplicationRegistrationRepository;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import reactor.core.publisher.Mono;

@Service
public class ICitizenRegistrationApplicationImplementation implements ICitizenApplicationRegistrationService {

	
	
	@Autowired
	private IApplicationRegistrationRepository appRepo;

	@Autowired
	private RestTemplate template;
	
	@Autowired
	private WebClient client;
	
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
		
		Mono<String> response = client.get().uri(endpointURL,input.getSsn()).retrieve().
				onStatus(HttpStatus.BAD_REQUEST::equals,res->res.bodyToMono(String.class)
						.map(ex-> new SSNInputException("Invalid SSN"))).bodyToMono(String.class);
			
		String stateName = response.block();
		
			if(stateName.equalsIgnoreCase(targetState)) {
			System.out.println(" State Name ::"+stateName);
			
			System.out.println("Input for registration :: " + input.toString());
			CitizenAppRegistartionEntity entity = new CitizenAppRegistartionEntity();
			BeanUtils.copyProperties(input, entity);			
			int appId = appRepo.save(entity).getAppId();
		  
			return appId;
		}
    	throw new SSNInputException("Invalid SSN"); 

	}
	
}
