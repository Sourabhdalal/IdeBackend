package com.DoctorApi.blog.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.DoctorApi.blog.Dto.DoctorDto;
import com.DoctorApi.blog.Dto.PatientDto;


public interface PatientService
{
	PatientDto createPatient(PatientDto patient);
	  
	PatientDto updatePatient(PatientDto patient,Integer patientId);
	  
	PatientDto getPatientById(Integer patientId);
	  
	void deletePatient(Integer patientId);
	  
	List<PatientDto> getAllPatient();
	
	PatientDto logIn(String username,String password);
	
	PatientDto checkingDetails(PatientDto patient,Integer patientId,Integer doctorId);
}
