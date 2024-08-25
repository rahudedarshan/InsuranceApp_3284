package com.nd.bindings;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PlanCategoryData {

	private String  categoryName;	
	private  String activeSW; 
	private LocalDate creationDate;
	private LocalDate updationDate;
	private String createdBy;
	private String updatedBy;
}
