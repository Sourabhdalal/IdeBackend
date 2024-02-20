package com.DoctorApi.blog.ServiceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DoctorApi.blog.Dto.PrescriptionDto;
import com.DoctorApi.blog.Entity.Doctor;
import com.DoctorApi.blog.Entity.Patient;
import com.DoctorApi.blog.Entity.Prescription;
import com.DoctorApi.blog.Repository.DoctorRepo;
import com.DoctorApi.blog.Repository.PatientRepo;
import com.DoctorApi.blog.Repository.PrescriptionRepo;
import com.DoctorApi.blog.Service.PrescriptionService;
import com.DoctorApi.blog.exception.ResourceNotFoundException;


@Service
public class PrescriptionServiceImpl implements PrescriptionService {

	@Autowired
	private PrescriptionRepo prescriptionRepo;
	
	@Autowired
	private DoctorRepo doctorRepo;
	
	@Autowired
	private PatientRepo patientRepo;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PrescriptionDto generatePrescription(PrescriptionDto ps, Integer patientId, Integer doctorId) {
		
		Prescription pres=this.modelMapper.map(ps, Prescription.class);
		Doctor d=this.doctorRepo.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("doctor","id",doctorId));
		Patient p=this.patientRepo.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("patient","id",patientId));
		
		
		pres.setDoctor(d);
		pres.setPatient(p);
		
		System.out.println(pres.getDoctor());
		System.out.println(pres.getPatient());
		
		Prescription updatedPres=this.prescriptionRepo.save(pres);
		
		return this.modelMapper.map(updatedPres,PrescriptionDto.class);
	}

	@Override
	public PrescriptionDto getPrescription(Integer prescriptionId) {
		Prescription p=this.prescriptionRepo.findById(prescriptionId).orElseThrow(() -> new ResourceNotFoundException("prescription","id",prescriptionId));
		
		
		return this.modelMapper.map(p, PrescriptionDto.class);
	}

}
