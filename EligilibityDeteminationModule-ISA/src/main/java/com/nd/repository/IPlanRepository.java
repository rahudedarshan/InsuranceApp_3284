package com.nd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nd.entity.PlanEntity;

public interface IPlanRepository extends JpaRepository<PlanEntity, Integer> {

}