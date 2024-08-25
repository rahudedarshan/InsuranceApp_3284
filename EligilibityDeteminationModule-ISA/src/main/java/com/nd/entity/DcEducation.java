package com.nd.entity;

import java.util.Date;

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
@Table(name = "DC_EDUCATION")
@NoArgsConstructor
@AllArgsConstructor
public class DcEducation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer educationId;
	private Integer caseNo;
	private String highestQualification;
	private Integer passoutYear;
	
}
