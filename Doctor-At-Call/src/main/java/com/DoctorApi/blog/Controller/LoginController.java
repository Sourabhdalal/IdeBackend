
package com.DoctorApi.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DoctorApi.blog.Dto.CommonDto;
import com.DoctorApi.blog.Dto.DoctorDto;
import com.DoctorApi.blog.Dto.PatientDto;
import com.DoctorApi.blog.Service.DoctorService;
import com.DoctorApi.blog.Service.PatientService;

@RequestMapping("/login")
@RestController
public class LoginController
{
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private PatientService patientService;
	
	@PostMapping("/{userName}/{password}")
	public ResponseEntity<CommonDto> logIn(@PathVariable String userName,@PathVariable String password)
	{
		DoctorDto d = this.doctorService.logIn(userName, password);
		
		PatientDto p = this.patientService.logIn(userName, password);
		
		CommonDto c=new CommonDto();
		
		if(d==null && p==null)
			return null;
		
		if(d!=null)
		{
			c.setName(d.getDoctorName());
			c.setId(d.getDoctorId());
			c.setEmail(d.getDoctorEmail());
			c.setPhoneNumber(d.getDoctorPhoneNumber());
			c.setPhoto(d.getDoctorePhoto());
			c.setEducation(d.getDoctorEducation());
			
			
			return new ResponseEntity<CommonDto>(c,HttpStatus.OK);
		}
		if(p!=null)
		{
			c.setAddress(p.getPatientAddress());
			c.setEmail(p.getPatientEmail());
			c.setId(p.getPatientId());
			c.setName(p.getPatientName());
			c.setPhoneNumber(p.getPatientPhoneNumber());
			c.setPhoto(p.getPatientPhoto());
			
			return new ResponseEntity<CommonDto>(c,HttpStatus.OK);
		}
		
		
		return null;
	}
}
