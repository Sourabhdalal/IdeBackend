package com.DoctorApi.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DoctorApi.blog.Dto.PrescriptionDto;
import com.DoctorApi.blog.Service.PrescriptionService;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController
{
	
	@Autowired
	PrescriptionService prescriptionService;
	
	@PostMapping("/generate/{doctorId}/{patientId}")
	public ResponseEntity<PrescriptionDto> generatePrescription(@RequestBody PrescriptionDto ps
			,@PathVariable Integer doctorId
			,@PathVariable Integer patientId)
	{
		
		PrescriptionDto pd=this.prescriptionService.generatePrescription(ps, patientId, doctorId);
		
		return new ResponseEntity<PrescriptionDto>(pd,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/get/{prescriptionId}")
	public ResponseEntity<PrescriptionDto> getPrescription(@PathVariable Integer prescriptionId)
	{
		PrescriptionDto p=this.prescriptionService.getPrescription(prescriptionId);
		
		
		return new ResponseEntity<PrescriptionDto>(p,HttpStatus.OK);
	}
}
