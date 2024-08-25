package com.nd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nd.bindings.PlanData;
import com.nd.config.AppConfigProperties;
import com.nd.constants.AdminConstants;
import com.nd.entity.PlanCategory;
import com.nd.entity.PlanEntity;
import com.nd.repository.IPlanCategoryRepository;
import com.nd.repository.IPlanRepository;




@Service
public class AdminMgmtServiceImpl implements IAdminMgmtService {

	@Autowired
	private IPlanCategoryRepository categoryRepo;
	
	@Autowired
	private IPlanRepository planRepo;
	
	private Map<String, String> messages;
	
	@Autowired
	public AdminMgmtServiceImpl(AppConfigProperties props) {
		
		messages = props.getMessages();
	}
	
	@Override
	public String registerPlan(PlanData plan) {
		
		PlanEntity planEntity = new PlanEntity();
		BeanUtils.copyProperties(plan, planEntity);
		PlanEntity savedEntity =  planRepo.save(planEntity);
		
		 return savedEntity.getPlanId()!=null ?  
				messages.get(AdminConstants.SAVE_SUCCESS)+ " : "+savedEntity.getPlanId(): 
								messages.get(AdminConstants.SAVE_FAILURE);
	}

	@Override
	public Map<Integer, String> getPlanCategories() {
		List<PlanCategory> list = categoryRepo.findAll();
		Map<Integer, String> categoriesMap =  new HashMap<>();
		list.forEach(category->{
			
			categoriesMap.put(category.getCategoryId(), category.getCategoryName());
		});
		
		return categoriesMap;
	}

	@Override
	public List<PlanData> showAllPlans() {
		
		List<PlanEntity> listEntities = planRepo.findAll();
		List<PlanData> listPlanData = new ArrayList<>();
		listEntities.forEach(entity->{
			PlanData planData =  new PlanData();
			BeanUtils.copyProperties(entity, planData);
			listPlanData.add(planData);
		});
	
		return listPlanData;
	}

	@Override
	public PlanData showPlanById(Integer planId) {
		PlanEntity entity =  planRepo.findById(planId).orElseThrow(()-> 
				new IllegalArgumentException( messages.get(AdminConstants.FIND_BY_ID_FAILURE)));
		
		PlanData plandata = new PlanData();
		BeanUtils.copyProperties(entity, plandata);
		return plandata;
	}

	@Override
	public String updatePlan(PlanEntity plan) {
		
		Optional<PlanEntity> opt = planRepo.findById(plan.getPlanId());
		
		if(opt.isPresent()) {			
			PlanEntity entity = opt.get();
			BeanUtils.copyProperties(plan, entity);
			planRepo.save(entity);
			return messages.get(AdminConstants.UPDATE_SUCCESS) +" : "+plan.getPlanId();
		
		}else {
			return  messages.get(AdminConstants.UPDATE_FAILURE);
		}
	}

	@Override
	public String deletePlan(Integer planId) {

		Optional<PlanEntity> opt = planRepo.findById(planId);
		
		if(opt.isPresent()) {			
			planRepo.deleteById(planId);
			return messages.get(AdminConstants.DELETE_SUCCESS) +" : "+planId;
		}else {
			return  messages.get(AdminConstants.DELETE_FAILURE);
		}
	}

	@Override
	public String changePlanStatus(Integer planId, String status) {
		Optional<PlanEntity> opt = planRepo.findById(planId);
		
		if(opt.isPresent()) {
			PlanEntity plan = opt.get();
			plan.setActiveSW(status);
			planRepo.save(plan);
			return messages.get(AdminConstants.STATUS_UPDATE_SUCCESS) +" : "+plan.getPlanId();
		}else {
			return  messages.get(AdminConstants.STATUS_UPDATE_FAILURE);
		}	
	}

}
