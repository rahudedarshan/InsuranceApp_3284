package com.nd.sevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nd.bindings.ChildInputs;
import com.nd.bindings.CitizenAppRegistrationInput;
import com.nd.bindings.DcSummaryReport;
import com.nd.bindings.EducationInput;
import com.nd.bindings.IncomeInputs;
import com.nd.bindings.planSelectionInput;
import com.nd.entity.CitizenAppRegistartionEntity;
import com.nd.entity.DcCaseEntity;
import com.nd.entity.DcChildernEntity;
import com.nd.entity.DcEducation;
import com.nd.entity.DcIncome;
import com.nd.entity.PlanEntity;
import com.nd.repository.IApplicationRegistrationRepository;
import com.nd.repository.IDcCasesRepository;
import com.nd.repository.IDcChildrenRepository;
import com.nd.repository.IDcEductionRepository;
import com.nd.repository.IDcIncomeRepository;
import com.nd.repository.IPlanRepository;

@Service
public class DcMgmtServiceImpl implements DcMgmtService {
	
	@Autowired
	private IDcCasesRepository caseRepository;
	@Autowired
	private IApplicationRegistrationRepository citizenAppRepo;
	@Autowired
	private IPlanRepository planRepository; 
	@Autowired
	private IDcIncomeRepository incomeRepo;
	@Autowired
	private IDcEductionRepository educationRepo;
	@Autowired
	private IDcChildrenRepository childrenRepo;
	
	
	
	@Override
	public Integer generateCaseNumber(Integer appId) {
		//load the Citizen Data
		Optional<CitizenAppRegistartionEntity> appCitizen=citizenAppRepo.findById(appId);
		if(appCitizen.isPresent()) {
			DcCaseEntity caseEntity=new DcCaseEntity();
			caseEntity.setAppId(appId);
			caseEntity.setPlanId(100);
			return caseRepository.save(caseEntity).getCaseNumber();//save obj operation
			
		}
		return 0;
	 }

	@Override
	public List<String> showAllPlanNames() {
		List<PlanEntity> plansList=planRepository.findAll();
		List<String> planNamesList=plansList.stream().map(plan->plan.getPlanName()).toList();
	
		return planNamesList;
	}
 
	// to set pan id using case no -> case table
	@Override
	public Integer savePlanSelection(planSelectionInput plan) {
		
		Optional<DcCaseEntity> opt = caseRepository.findById(plan.getCaseNo());
		if(opt.isPresent()) {
			DcCaseEntity entity = opt.get();
			entity.setPlanId(plan.getPlanId());
			caseRepository.save(entity);
			return entity.getCaseNumber();
		}
		return 0;
	}
		

	// to save income details in income table
	@Override
	public Integer saveIncomeDetails(IncomeInputs income) {
		DcIncome incomeEntity = new DcIncome();
		BeanUtils.copyProperties(income, incomeEntity);
		incomeRepo.save(incomeEntity);
		return income.getCaseNo();
	}
	
	// to save education details
	@Override
	public Integer saveEducationDetails(EducationInput education) {
		DcEducation eduEntity = new DcEducation();
		BeanUtils.copyProperties(education, eduEntity);
		educationRepo.save(eduEntity);
		return education.getCaseNo();
	}


	@Override
	public Integer saveChildrenDetails(List<ChildInputs> childrens) {
		childrens.forEach(child->{
			DcChildernEntity childEntity = new DcChildernEntity();
			BeanUtils.copyProperties(child, childEntity);
			childrenRepo.save(childEntity);
		});
		return childrens.get(0).getCaseNo();
	}

	
	@Override
	public DcSummaryReport showSummaryReport(Integer caseNo) {
		
		//convert entity objects to bindings objects
		  DcEducation educationEntity = educationRepo.findByCaseNo(caseNo);
		  DcIncome incomeEntity = incomeRepo.findBycaseNo(caseNo);
		  List<DcChildernEntity> listChildrens=childrenRepo.findByCaseNo(caseNo);
		  
		  EducationInput educationInputs=new EducationInput();
		  BeanUtils.copyProperties(educationEntity, educationInputs);
		  IncomeInputs incomeInputs=new IncomeInputs();
		  BeanUtils.copyProperties(incomeEntity, incomeInputs);
		  //children list
		  List<ChildInputs> ChildrensList=new ArrayList<ChildInputs>();
		  
		  listChildrens.forEach(children->{
			  ChildInputs childInputs=new ChildInputs();
			  BeanUtils.copyProperties(children, childInputs);
			  ChildrensList.add(childInputs);
		  });
		  
		  //getting plan name
		  String planName = null;
		//  Integer appId=null;
		 Optional<DcCaseEntity> optCaseEntity=caseRepository.findById(caseNo);
		 if(optCaseEntity.isPresent()) {
			 Optional<PlanEntity> optPlanEntity=planRepository.findById(optCaseEntity.get().getPlanId());
			 if(optPlanEntity.isPresent()) {
				 planName=optPlanEntity.get().getPlanName();
			 }//inner if
		 }//outer if
		 //collecting citizen data
		 Optional<CitizenAppRegistartionEntity> optCitizenEntity=citizenAppRepo.findById(optCaseEntity.get().getAppId());
		  CitizenAppRegistrationInput citizenInputs=new CitizenAppRegistrationInput();
		  if(optCaseEntity.isPresent()) {
			  BeanUtils.copyProperties(optCitizenEntity.get(), citizenInputs);
		  }
		//set properties to DcSummaryReport
		  DcSummaryReport report=new DcSummaryReport();
		  report.setEducationDetails(educationInputs);
		  report.setIncomeDetails(incomeInputs);
		  report.setChildrenDetails(ChildrensList);
	 	  report.setPlanName(planName);
		  report.setCitizenAppDetails(citizenInputs);
		  return report;
	}//end of show summary report

	
}