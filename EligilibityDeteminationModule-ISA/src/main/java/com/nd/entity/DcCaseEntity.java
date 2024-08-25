package com.nd.entity;

import java.time.LocalDate;
import org.apache.catalina.authenticator.DigestAuthenticator.AuthDigest;

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
@Table(name = "DC_CASES")
@NoArgsConstructor
@AllArgsConstructor
public class DcCaseEntity {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Integer caseNumber;
private Integer appId;
private Integer planId;

}
