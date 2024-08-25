package com.nd.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name="PLAN_CATEGORY")
@Entity
@Data
public class PlanCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer categoryId;
	
	@Column(length = 25)
	private String  categoryName;	
	@Column(length = 30)
	private  String activeSW; 

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate creationDate;
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDate updationDate;
	
	@Column(length = 30)
	private String createdBy;
	@Column(length = 30)
	private String updatedBy;
	
}
