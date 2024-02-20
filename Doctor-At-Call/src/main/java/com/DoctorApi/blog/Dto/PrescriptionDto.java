package com.DoctorApi.blog.Dto;

import com.DoctorApi.blog.Entity.Doctor;
import com.DoctorApi.blog.Entity.Patient;

import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PrescriptionDto 
{
	
	private Integer Id;
	
	private String dieases;
	private String tablet;
	private String date;
	private String time;
	
	private Doctor doctor;

	private Patient patient;
}
