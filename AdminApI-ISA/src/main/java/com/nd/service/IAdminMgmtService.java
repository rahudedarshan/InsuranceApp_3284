package com.nd.service;

import java.util.List;
import java.util.Map;

import com.nd.bindings.PlanData;
import com.nd.entity.PlanEntity;


public interface IAdminMgmtService {

	public String registerPlan(PlanData plan);
	public Map<Integer, String> getPlanCategories();
	public List<PlanData> showAllPlans();
	public PlanData showPlanById(Integer planId);
	public String updatePlan(PlanEntity plan);
	public String deletePlan(Integer planId);
	public String changePlanStatus(Integer planId, String status);
	
	
}