package com.nd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nd.entity.PlanCategory;

public interface IPlanCategoryRepository extends JpaRepository<PlanCategory, Integer>{

}
