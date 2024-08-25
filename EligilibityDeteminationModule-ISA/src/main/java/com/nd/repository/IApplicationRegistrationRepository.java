package com.nd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nd.entity.CitizenAppRegistartionEntity;

public interface IApplicationRegistrationRepository extends JpaRepository<CitizenAppRegistartionEntity,Integer>{

}
