package com.nd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nd.binding.CitizenAppRegistrationInput;
import com.nd.exception.SSNInputException;
import com.nd.service.ICitizenApplicationRegistrationService;

@RestController
@RequestMapping("citizen/api")
public class CitizenApplicationRegistrationController {
	
	@Autowired
	private ICitizenApplicationRegistrationService regService;

	@PostMapping("/save")
	public ResponseEntity<String> saveCitizenApplication(@RequestBody CitizenAppRegistrationInput inputs) throws SSNInputException{
		
		try {
			
			int appId  = regService.registerCitizenApplication(inputs);
			
			if(appId>0) {
				return new ResponseEntity<String>("Citizen Application with registration ID : "+appId,HttpStatus.CREATED);
			}else {
				return new ResponseEntity<String>("Invalid SSN or Bad State",HttpStatus.CREATED);
			}
			

		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
  }
}
