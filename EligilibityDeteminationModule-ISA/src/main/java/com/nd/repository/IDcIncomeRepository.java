package com.nd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nd.entity.DcIncome;

public interface IDcIncomeRepository extends JpaRepository<DcIncome, Integer> {
    public DcIncome findBycaseNo(Integer caseNumber);
}