package com.DoctorApi.blog.ServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DoctorApi.blog.Dto.DoctorDto;
import com.DoctorApi.blog.Dto.PrescriptionDto;
import com.DoctorApi.blog.Entity.Doctor;
import com.DoctorApi.blog.Entity.Patient;
import com.DoctorApi.blog.Entity.Prescription;
import com.DoctorApi.blog.Repository.DoctorRepo;
import com.DoctorApi.blog.Repository.PatientRepo;
import com.DoctorApi.blog.Repository.PrescriptionRepo;
import com.DoctorApi.blog.Service.DoctorService;
import com.DoctorApi.blog.exception.ResourceNotFoundException;



@Service
public class DoctorServiceImpl implements DoctorService {
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private DoctorRepo doctorRepo;
	
	@Autowired
	private PatientRepo patientRepo;
	
	@Autowired
	private PrescriptionRepo prescriptionRepo;

	@Override
	public DoctorDto createDoctor(DoctorDto doctor) {
		
		Doctor d=this.modelMapper.map(doctor,Doctor.class);
		
		this.doctorRepo.save(d);
	
		return this.modelMapper.map(d, DoctorDto.class);
	}

	@Override
	public DoctorDto updateDoctor(DoctorDto doctor, Integer doctorId) {
		
		Doctor d=this.doctorRepo.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("doctor","id",doctorId));
		
		d.setDoctorEducation(doctor.getDoctorEducation());
		d.setDoctorEmail(doctor.getDoctorEmail());
		d.setDoctorePhoto(doctor.getDoctorePhoto());
		d.setDoctorPhoneNumber(doctor.getDoctorPhoneNumber());
		d.setPassword(doctor.getPassword());
		d.setDoctorCategory(doctor.getDoctorCategory());
		
		Doctor updatedDoctor=this.doctorRepo.save(d);
		
		return this.modelMapper.map(updatedDoctor, DoctorDto.class);
	}

	@Override
	public DoctorDto getDoctorById(Integer doctorId) {
		
		Doctor d=this.doctorRepo.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("doctor","id",doctorId));
		return this.modelMapper.map(d,DoctorDto.class);
	}

	@Override
	public void deleteDoctor(Integer doctorId) {
		this.doctorRepo.deleteById(doctorId);
	}

	@Override
	public List<DoctorDto> getAllDoctor() {
		
		List<Doctor> doctorList=this.doctorRepo.findAll();
		
		List<DoctorDto> doctorDtoList=doctorList.stream().map(doctor->this.modelMapper.map(doctor, DoctorDto.class)).collect(Collectors.toList());
		
		return doctorDtoList;
	}

	@Override
	public DoctorDto logIn(String username, String password) {
		
		Optional<Doctor> d=this.doctorRepo.findByUsernameAndPassword(username, password);
		
		if(d.isPresent())
		{
			//System.out.println(d);
		   return this.modelMapper.map(d.get(),DoctorDto.class);
		}
		return null;
	}

	public DoctorDto deletePatient(Integer doctorId, Integer patientId) {
		
		Doctor d=this.doctorRepo.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("doctor","id",doctorId));
		Patient ps=this.patientRepo.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("patient","id",patientId));

		Set<Patient> s=d.getPatients();
		
		for(Patient p:s)
		{
			if(p.getPatientId()==patientId)
			{
				s.remove(p);
			}
		}
		
		d.setPatients(s);
		ps.setStatus(false);
		
		this.patientRepo.save(ps);
		this.doctorRepo.save(d);
		
		return this.modelMapper.map(d, DoctorDto.class);
	}

	public DoctorDto acceptPatient(Integer doctorId, Integer patientId) {
		Doctor d=this.doctorRepo.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("doctor","id",doctorId));
		Patient ps=this.patientRepo.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("patient","id",patientId));
		
		Set<Patient> hs=new HashSet<>();
	    hs.add(ps);
    
	    Set<Doctor> hs1=new HashSet<>();
        hs1.add(d);
//	     
//	    
////	    
//	    d.setPatients(hs);
//	    ps.setDoctor(hs1);
//	    ps.setStatus(true);
		
		
//		System.out.println(d.getDoctorName()+" ");
//		System.out.println(ps.getPatientName());
	    this.doctorRepo.save(d);
	    this.patientRepo.save(ps);
	    
		return this.modelMapper.map(d, DoctorDto.class);
	}

	public DoctorDto generatePrescription(PrescriptionDto prescDto, Integer doctorId, Integer patientId) {
		
		Doctor d=this.doctorRepo.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("doctor","id",doctorId));
		Patient ps=this.patientRepo.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("patient","id",patientId));
		Prescription p=this.prescriptionRepo.save(this.modelMapper.map(prescDto, Prescription.class));
		
		
		System.out.println(d.getDoctorName());
		System.out.println(ps.getPatientName());
		System.out.println(p.getTablet());
		
		return this.modelMapper.map(d, DoctorDto.class);
	}
	
	

}
