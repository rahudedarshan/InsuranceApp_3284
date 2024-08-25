package com.nd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nd.bindings.EligilibityDetailsOutput;
import com.nd.service.IEligibilityDeteminationService;

@RestController
@RequestMapping("/ed-api")
public class EligibilityDeterminationController {
	
	  @Autowired	
	  private IEligibilityDeteminationService eligibilityService;
	   
	 @GetMapping("/determination/{caseNumber}")
	  public ResponseEntity<EligilibityDetailsOutput> checkCitizenEligibility(@PathVariable Integer caseNumber){
		 
		 System.out.println(" Application Sterted with CaseID :: "+caseNumber);
		 
		  //use service
		 EligilibityDetailsOutput elgibilityOut=eligibilityService.detemineEligibility(caseNumber);
		
		return new ResponseEntity<EligilibityDetailsOutput>(elgibilityOut,HttpStatus.OK);
	  
	 }
}