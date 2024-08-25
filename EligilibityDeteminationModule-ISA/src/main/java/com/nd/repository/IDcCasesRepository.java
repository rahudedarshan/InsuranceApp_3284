package com.nd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nd.entity.DcCaseEntity;

public interface IDcCasesRepository extends JpaRepository<DcCaseEntity, Integer> {

}