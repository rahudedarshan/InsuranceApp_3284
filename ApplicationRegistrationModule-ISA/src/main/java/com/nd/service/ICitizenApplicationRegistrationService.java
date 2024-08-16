package com.nd.service;

import com.nd.binding.CitizenAppRegistrationInput;
import com.nd.exception.SSNInputException;

public interface ICitizenApplicationRegistrationService {
	
	public Integer registerCitizenApplication(CitizenAppRegistrationInput input) throws SSNInputException;

}
