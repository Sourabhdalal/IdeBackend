package com.DoctorApi.blog.ServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DoctorApi.blog.Dto.PatientDto;
import com.DoctorApi.blog.Entity.Doctor;
import com.DoctorApi.blog.Entity.Patient;
import com.DoctorApi.blog.Repository.DoctorRepo;
import com.DoctorApi.blog.Repository.PatientRepo;
import com.DoctorApi.blog.Service.PatientService;
import com.DoctorApi.blog.exception.ResourceNotFoundException;


@Service
public class PatientServiceImpl implements PatientService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PatientRepo patientRepo;
	
	@Autowired
	private DoctorRepo doctorRepo;

	@Override
	public PatientDto createPatient(PatientDto patient) {
		
        Patient p=this.modelMapper.map(patient,Patient.class);
		
		this.patientRepo.save(p);
	
		return this.modelMapper.map(p, PatientDto.class);
	}

	@Override
	public PatientDto updatePatient(PatientDto patient, Integer patientId) {
		Patient p=this.patientRepo.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("patient","id",patientId));
		
		p.setPatientAddress(patient.getPatientAddress());
		p.setPatientEmail(patient.getPatientEmail());
		p.setPatientPhoneNumber(patient.getPatientPhoneNumber());
		p.setPatientPhoto(p.getPatientPhoto());
		
		Patient updatedPatient=this.patientRepo.save(p);
		
		return this.modelMapper.map(updatedPatient, PatientDto.class);
	}

	@Override
	public PatientDto getPatientById(Integer patientId) {
		Patient p=this.patientRepo.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("patient","id",patientId));

		return this.modelMapper.map(p, PatientDto.class);
	}

	@Override
	public void deletePatient(Integer patientId) {
	   Patient p=this.patientRepo.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("patient","id",patientId));

	   this.patientRepo.deleteById(patientId);
	}

	@Override
	public List<PatientDto> getAllPatient() {
		
        List<Patient> patientList=this.patientRepo.findAll();
		
		List<PatientDto> patientDtoList=patientList.stream().map(patient->this.modelMapper.map(patient, PatientDto.class)).collect(Collectors.toList());
		
		return patientDtoList;
	}

	@Override
	public PatientDto logIn(String username, String password) {
		
		Optional<Patient> p=this.patientRepo.findByUsernameAndPassword(username, password);
		
		
		if(p.isPresent())
		{
		   return this.modelMapper.map(p.get(),PatientDto.class);
		}
		return null;
	}

	@Override
	public PatientDto checkingDetails(PatientDto patient,Integer patientId,Integer doctorId) {
		Patient p=this.patientRepo.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("patient","id",patientId));
		
		Doctor d=this.doctorRepo.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("doctor","id",doctorId));
		
		 
		 //System.out.println(d.getDoctorCategory()+" "+p.getPatientName());
	     p.setCold(patient.isCold());
	     p.setCheckUp(patient.isCheckUp());
	     p.setCough(patient.isCough());
	     p.setFever(patient.isFever());
	     p.setDate(patient.getDate());
	     p.setTime(patient.getTime());
	    
	     Set<Doctor> hs1=new HashSet<>();
	     p.setDoctor(hs1);
         
	     Set<Patient> hs=new HashSet<>();
	     hs.add(p);
	     
	     
	     d.setPatients(hs);
		 
		 this.patientRepo.save(p);
		 
		 this.doctorRepo.save(d);
		 return this.modelMapper.map(p, PatientDto.class);
	}

}
