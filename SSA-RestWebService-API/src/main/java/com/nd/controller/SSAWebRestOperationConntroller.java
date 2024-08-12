package com.nd.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ssa-web-api")
public class SSAWebRestOperationConntroller {
	
	@GetMapping("/find/{ssn}")
	public ResponseEntity<String> getStateBySSN(@PathVariable Integer ssn){
		
		// validation for ssn 
		if(String.valueOf(ssn).length()!=9) {
			return new ResponseEntity<>("Enter valid SSN",HttpStatus.BAD_REQUEST);
		}
		
		//get state name
		int stateCode = ssn%100;
		String stateName = null;
		
		if(stateCode==01)
			stateName="Washington DC";
		else if(stateCode==02)
			stateName="Ohio";
		else if(stateCode==03)
			stateName="Texas";
		else if(stateCode==04)
			stateName="california";
		else if(stateCode==05)
			stateName="Florida";
		else
			stateName = "Inavlid SSN";
		
		return new ResponseEntity<>(stateName,HttpStatus.OK);
	}
	
	

}
