package com.nd.bindings;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PlanData {

	private String planName;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private String description;	
	private Integer planCategoryId;
	private String activeSW;
	private LocalDate creationDate;
	private LocalDate updationDate;
	private String createdBy;
	private String updatedBy;

}
