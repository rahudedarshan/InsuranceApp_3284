package com.nd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nd.bindings.PlanData;
import com.nd.entity.PlanCategory;
import com.nd.entity.PlanEntity;
import com.nd.repository.IPlanRepository;
import com.nd.service.IAdminMgmtService;




@RestController
@RequestMapping("/ad-api")
public class AdminOperationController {

	@Autowired
	private IAdminMgmtService service;
	
	
	// It is registering the plan
	@PostMapping("/register")
	public ResponseEntity<String> savePlan(@RequestBody PlanData plan){	
		
		try {
			 String msg = service.registerPlan(plan);
		   	return new ResponseEntity<String>(msg,HttpStatus.CREATED);
			
		}catch (Exception e) {
			   e.printStackTrace();
			   return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@GetMapping("/showCategories")
	public ResponseEntity<?> showCategories(){	
		
		try {
			Map<Integer, String> mapCategories = new HashMap<>();
			mapCategories = service.getPlanCategories();
			
		   	return new ResponseEntity<Map<Integer, String>>(mapCategories,HttpStatus.CREATED);
			
		}catch (Exception e) {
			   e.printStackTrace();
			   return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@GetMapping("/showPlans")
	public ResponseEntity<?> showPlans(){	
		
		try {
			List<PlanData> planList =  new ArrayList<>();
			planList = service.showAllPlans();
			
		   	return new ResponseEntity<List<PlanData>>(planList,HttpStatus.CREATED);
			
		}catch (Exception e) {
			   e.printStackTrace();
			   return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}


	@GetMapping("/find/{planId}")
	public ResponseEntity<?> getPlanById(@PathVariable Integer planId){
		
		try {
			  PlanData plan = service.showPlanById(planId);
			  return new ResponseEntity<PlanData>(plan,HttpStatus.CREATED);
			
		}catch (Exception e) {
			   e.printStackTrace();
			   return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateTravelPlan(@RequestBody PlanEntity plan){		
		
		try {		
			    String msg = service.updatePlan(plan);
			   	return new ResponseEntity<String>(msg,HttpStatus.CREATED);
			
		}catch (Exception e) {
			   e.printStackTrace();
			   return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@DeleteMapping("/delete/{planId}")
	public ResponseEntity<?> deletePlanById(@PathVariable Integer planId){
		
		try {
			
			  String msg = service.deletePlan(planId);
			 return new ResponseEntity<String>(msg,HttpStatus.CREATED);
			
		}catch (Exception e) {
			   e.printStackTrace();
			   return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PutMapping("/status-update/{planId}/{status}")
	public ResponseEntity<?> UpdateStatusByid(@PathVariable Integer planId,@PathVariable String status){
		
		try {
			
			 String msg = service.changePlanStatus(planId, status);
			 return new ResponseEntity<String>(msg,HttpStatus.CREATED);
			
		}catch (Exception e) {
			   e.printStackTrace();
			   return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	

}
