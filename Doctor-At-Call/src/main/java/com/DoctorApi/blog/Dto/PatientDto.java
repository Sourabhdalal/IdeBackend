package com.DoctorApi.blog.Dto;

import java.util.HashSet;
import java.util.Set;

import com.DoctorApi.blog.Entity.Doctor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@NoArgsConstructor
@Getter
public class PatientDto
{
	private int patientId;
    private String patientName;
    private String patientEmail;
    private String password;
    private String patientPhoneNumber;
    private String patientAddress;
    private String patientPhoto;
    private boolean cough;
    private boolean fever;
    private boolean cold;
    private boolean checkUp;
    private String date;
    private String time;
    private boolean status;
    
    //private Integer doctorId;
    private Set<DoctorDto> doctor=new HashSet<>();
    
    
}
