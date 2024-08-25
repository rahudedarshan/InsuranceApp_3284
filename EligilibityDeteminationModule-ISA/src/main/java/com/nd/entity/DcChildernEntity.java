package com.nd.entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "DC_CHILDERN")
@NoArgsConstructor
@AllArgsConstructor
public class DcChildernEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer childernId;
	private Integer caseNo;
	private LocalDate childenDOB;
	private Long ssnNo;
	
	

}
