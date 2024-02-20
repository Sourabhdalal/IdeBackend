package com.DoctorApi.blog.Service;

import com.DoctorApi.blog.Dto.PrescriptionDto;

public interface PrescriptionService 
{
	PrescriptionDto generatePrescription(PrescriptionDto ps,Integer patientId,Integer doctorId);

	PrescriptionDto getPrescription(Integer prescriptionId);
}
