package com.nd.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nd.bindings.EligilibityDetailsOutput;
import com.nd.entity.CitizenAppRegistartionEntity;
import com.nd.entity.CoTriggersEntity;
import com.nd.entity.DcCaseEntity;
import com.nd.entity.DcChildernEntity;
import com.nd.entity.DcEducation;
import com.nd.entity.DcIncome;
import com.nd.entity.EligilibityDetailsEntity;
import com.nd.entity.PlanEntity;
import com.nd.repository.IApplicationRegistrationRepository;
import com.nd.repository.ICoTriggersRepository;
import com.nd.repository.IDcCasesRepository;
import com.nd.repository.IDcChildrenRepository;
import com.nd.repository.IDcEductionRepository;
import com.nd.repository.IDcIncomeRepository;
import com.nd.repository.IEligibilityDetailsRepository;
import com.nd.repository.IPlanRepository;

@Service 
class EligibilityDeterminatiinServiceImpl implements IEligibilityDeteminationService {
	
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
	@Autowired
	private IEligibilityDetailsRepository eligilibityService;
	@Autowired
	private ICoTriggersRepository triggerService;

	@Override
	public EligilibityDetailsOutput detemineEligibility(Integer caseNumber) {
	
		System.out.println("EligibilityDeterminatiinServiceImpl.detemineEligibility()");
		
		Integer appId= null;
		Integer planId= null;
		
		// get planId and appid based on CaseNo
		
     Optional<DcCaseEntity> optEntity = caseRepository.findById(caseNumber);
     if(optEntity.isPresent()) {
    	 DcCaseEntity entity = optEntity.get();
    	 planId = entity.getPlanId();
    	 appId = entity.getAppId();
     }
     
     System.out.println("Plan is ::"+planId);
     System.out.println("Plan is ::"+appId);
     
     //get plan name
	   String planName=null;
	   Optional<PlanEntity> optPlanEntity=planRepository.findById(planId);
	    if(optPlanEntity.isPresent()) {
	        PlanEntity planEntity=optPlanEntity.get();
	        planName=planEntity.getPlanName(); 
	    }
	    
	   System.out.println("Plan Name ::"+planName);
	    
	    //get citizen age and citizen name
	    
	    Optional<CitizenAppRegistartionEntity> optcitizenEntity=citizenAppRepo.findById(appId);
	    
	    Integer citizenAge=0;
	    String citizenName=null;
	    Long  citizenSSN=null;
	    
	    if(optcitizenEntity.isPresent()) {
	    	CitizenAppRegistartionEntity citizenEntity=optcitizenEntity.get();
	    	System.out.println("CitizenAppRegistartionEntity :: "+ citizenEntity.toString());
	    	citizenAge=Period.between(citizenEntity.getDOB(), LocalDate.now()).getYears();
	    	citizenName=citizenEntity.getFullName();
	    	citizenSSN=citizenEntity.getSsn();
	    	 
	    }
	    System.out.println("Citizen SSN :: "+ citizenAge);
	    System.out.println("Citizen SSN :: "+ citizenSSN);
	    
	  //call helper method to plan conditions
	    EligilibityDetailsOutput elgiOutput= applyPlanContiditions(caseNumber,planName,citizenAge);
	    elgiOutput.setHolderName(citizenName);
	    elgiOutput.setHolderSSN(citizenSSN);
	    
	    //create ElibilityDetails object
	     EligilibityDetailsEntity eligibilityEntity=new EligilibityDetailsEntity();
	     eligibilityEntity.setCaseNo(caseNumber);
	     BeanUtils.copyProperties(elgiOutput, eligibilityEntity);
	     eligilibityService.save(eligibilityEntity);
	      
	     //create Trigger Entity
	      CoTriggersEntity triggerEntity=new CoTriggersEntity();
	      triggerEntity.setCaseNo(caseNumber);
	      triggerEntity.setTriggerStatus("pending");
	      //save trigger entity object
	      triggerService.save(triggerEntity);
	     
	      return elgiOutput;
	}

	//helper method implementation
    private EligilibityDetailsOutput applyPlanContiditions(Integer caseNumber,String planName,Integer citizenAge) {
    	
    	EligilibityDetailsOutput elgiOut=new EligilibityDetailsOutput();
	    elgiOut.setPlanName(planName);
	 
	  //get employee income data
	    
	   DcIncome incomeEntity=incomeRepo.findBycaseNo(caseNumber);
	   Double empIncome=incomeEntity.getEmpIncome();
	   Double propertyIncome=incomeEntity.getPropertyIncome();
	   
	   //for SNAP plan
	   if(planName.equalsIgnoreCase("SNAP")) {
	   if(empIncome<=300) {
		 elgiOut.setPlanStatus("Approved");  
		 elgiOut.setBenfitAmount(200.0);
	   }else {
		   elgiOut.setPlanStatus("Denied");
		   elgiOut.setDenialReason("High income");
	   }
	   }//end of snap plan
	   
	   //for CCAP plan
	   else if(planName.equalsIgnoreCase("CCAP")) {
		  List<DcChildernEntity> listChildrens= childrenRepo.findByCaseNo(caseNumber);
		   boolean kidsCountCondition=false;
		   boolean kidsAgeCondition=true;
		   if(!listChildrens.isEmpty()) {
			  kidsCountCondition=true;
		   }
		   
		   for(DcChildernEntity children: listChildrens) {
			   Integer kidAge=Period.between(children.getChildenDOB(),LocalDate.now()).getYears();
			   if(kidAge>16) {
				   kidsAgeCondition=false;
				   break;
			   }
		   }//end for loop
		   
		    if(empIncome<=300 && kidsAgeCondition && kidsAgeCondition) {
		    	   elgiOut.setPlanStatus("Approved");
		    	   elgiOut.setBenfitAmount(300.0);
		    }else {
		    	  elgiOut.setPlanStatus("Denied");
		    	  elgiOut.setDenialReason("ccap plan conditions are not satisfied");
		    }
	     
	   }//end of ccap plan
	   
	   else if(planName.equalsIgnoreCase("MEDAID")) {
		      if(empIncome<=300 && propertyIncome==0) {
		    	   elgiOut.setPlanStatus("Approved");
		    	   elgiOut.setBenfitAmount(300.0);
		      }else {
		    	  elgiOut.setPlanStatus("Denied");
		    	  elgiOut.setDenialReason("medcaid plan conditions are not satisfied");
		      }
	   }//end of medaid plan
	   
	   else if(planName.equalsIgnoreCase("MEDCARE")) {
		      if(citizenAge>=65) {
		    	  elgiOut.setPlanStatus("Approved");
		    	  elgiOut.setBenfitAmount(300.0);
		      }else {
		    	  elgiOut.setPlanStatus("Denied");
		         elgiOut.setDenialReason("medcare plan conditios are not satisfied ");
		      }
	   }//end of medcare plan
	   
	   else if(planName.equalsIgnoreCase("CAJW")) {
		    DcEducation educationEntity=educationRepo.findByCaseNo(caseNumber);
		    int passOutYear=educationEntity.getPassoutYear();
		    if(empIncome==0 && passOutYear<LocalDate.now().getYear()) {
		    	elgiOut.setPlanStatus("Approved");
		    	elgiOut.setBenfitAmount(300.00);
		    }else
		    {
		    	elgiOut.setPlanStatus("Denied");
		    	elgiOut.setDenialReason("cajw plan conditions are not satisfiied");
		    }
	   }//end of cajw
	   
	   else if(planName.equalsIgnoreCase("QHP")) {
		      if(citizenAge>=1) {
		    	   elgiOut.setPlanStatus("Approved");
		    	   elgiOut.setBenfitAmount(300.0);
		      }else
		      {
		    	 elgiOut.setPlanStatus("Denied");
	    	   elgiOut.setDenialReason("qhp plan conditions are not satisfied");
		      }
	   }//end of qhp
	   
	   //set common properties eligibility object who are plan approved
	   if(elgiOut.getPlanStatus().equalsIgnoreCase("Approved")) {
	   elgiOut.setPlanStartDate(LocalDate.now());
	   elgiOut.setPlanEndDate(LocalDate.now().plusYears(2));
	   }
	   
	 return elgiOut;
  }//end of method

}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	