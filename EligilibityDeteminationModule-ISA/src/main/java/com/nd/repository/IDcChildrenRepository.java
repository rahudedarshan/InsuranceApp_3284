package com.nd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nd.entity.DcChildernEntity;
	
	public interface IDcChildrenRepository extends JpaRepository<DcChildernEntity, Integer> {
	    public List<DcChildernEntity>findByCaseNo(Integer caseNumber);

}
