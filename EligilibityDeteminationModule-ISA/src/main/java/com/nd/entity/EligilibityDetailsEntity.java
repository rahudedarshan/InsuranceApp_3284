package com.nd.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="ELIGIBILITY_DETAILS")
public class EligilibityDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eDTraceId;
	@Column(length=30)
	private Integer  caseNo;
	private  String holderName;
	private  Long holderSSN;
	@Column(length=30)
	private String planName;
	@Column(length=30)
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private Double benfitAmount;
	@Column(length=50)
	private  String denialReason;

}
