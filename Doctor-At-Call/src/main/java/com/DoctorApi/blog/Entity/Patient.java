package com.DoctorApi.blog.Entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="patient")
@NoArgsConstructor
@Getter
@Setter
public class Patient{
	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
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
     
//     @ManyToOne
//     @JoinColumn(name = "doctor_id")
//     private Doctor doctor;
     
     
     @OneToMany
     private Set<Doctor> doctor=new HashSet<>();

   
  
}