package com.DoctorApi.blog.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Prescription 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer Id;
	
	private String dieases;
	private String tablet;
	private String date;
	private String time;
	
	@OneToOne
	private Doctor doctor;
	
	
	@OneToOne
	private Patient patient;
}
