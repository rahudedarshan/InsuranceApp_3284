package com.nd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nd.entity.DcEducation;

public interface IDcEductionRepository extends JpaRepository<DcEducation, Integer> {
   
	public DcEducation findByCaseNo(Integer caseNo);
}