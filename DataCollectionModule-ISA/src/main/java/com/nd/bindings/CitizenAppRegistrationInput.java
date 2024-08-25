package com.nd.bindings;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class CitizenAppRegistrationInput {
	
	private String fullName;
	private String email;
	private String gender;
	private long phoneNo;
	private long ssn;
	private LocalDate DOB;
	

}
