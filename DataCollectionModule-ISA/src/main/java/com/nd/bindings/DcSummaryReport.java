package com.nd.bindings;

import java.util.List;

import lombok.Data;

@Data
public class DcSummaryReport {
	
  private EducationInput educationDetails;
  private IncomeInputs incomeDetails;
  private List<ChildInputs> childrenDetails;
  private CitizenAppRegistrationInput CitizenAppDetails;
  private String planName;

}