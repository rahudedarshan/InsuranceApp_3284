package com.nd.bindings;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EligilibityDetailsOutput {

	private  String holderName;
	private  Long holderSSN;
	private String planName;
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private Double benfitAmount;
	private  String denialReason;
}
