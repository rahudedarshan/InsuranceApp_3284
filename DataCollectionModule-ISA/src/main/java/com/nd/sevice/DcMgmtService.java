package com.nd.sevice;

import java.util.List;

import com.nd.bindings.ChildInputs;
import com.nd.bindings.DcSummaryReport;
import com.nd.bindings.EducationInput;
import com.nd.bindings.IncomeInputs;
import com.nd.bindings.planSelectionInput;

public interface DcMgmtService {

	  public Integer generateCaseNumber(Integer appId);
	  public List<String> showAllPlanNames();
	  public Integer savePlanSelection(planSelectionInput Inputs);
	  public Integer saveIncomeDetails(IncomeInputs income);
	  public Integer saveChildrenDetails(List<ChildInputs> childrens);
	  public Integer saveEducationDetails(EducationInput education);
	  public DcSummaryReport showSummaryReport(Integer caseNo);

}
