package com.DoctorApi.blog.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.DoctorApi.blog.Dto.PatientDto;
import com.DoctorApi.blog.Service.FileService;
import com.DoctorApi.blog.ServiceImpl.PatientServiceImpl;

import jakarta.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/patient")
public class PatientController
{
	
	@Autowired
	PatientServiceImpl patientService;
	
	@Value("${project.image}")
	private String path;
	
	@Autowired
	private FileService fileService;
	
	
	@PostMapping("/")
	public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDto)
	{
		PatientDto createdPatient = this.patientService.createPatient(patientDto);
		
		return new ResponseEntity<>(createdPatient,HttpStatus.CREATED);
	}
	
	@PutMapping("/{patientId}")
	public ResponseEntity<PatientDto> updatePatient(@RequestBody PatientDto patientDto,@PathVariable Integer patientId)
	{
		PatientDto updatedPatient = this.patientService.updatePatient(patientDto, patientId);
		
		return ResponseEntity.ok(updatedPatient);
	}
	
	@GetMapping("/getPatient")
	public ResponseEntity<List<PatientDto>> displayAllUser()
	{
		return ResponseEntity.ok(this.patientService.getAllPatient());
	}
	
	
	@GetMapping("/{patientId}")
	public ResponseEntity<PatientDto> singleUser(@PathVariable Integer patientId)
	{
		return ResponseEntity.ok(this.patientService.getPatientById(patientId));
	}
	
	@DeleteMapping("/{patientId}")
	public void deleteUser(@PathVariable Integer patientId)
	{
		this.patientService.deletePatient(patientId);
		//another method using by making class ApiResponse
		
		//return new ResponseEntity<ApiResponse>(new ApiResponse("Patient Delete Successfully",true),HttpStatus.OK);
		
		//return new ResponseEntity<ApiResponse>(Map.of("message","User delete Successfully"),HttpStatus.OK);
	}
	
	
	@PostMapping("/image/upload/{patientId}")
	public ResponseEntity<PatientDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer patientId) throws IOException {

		PatientDto patientDto = this.patientService.getPatientById(patientId);
		
		String fileName = this.fileService.uploadImage(path, image);
	
		patientDto.setPatientName(fileName);
		
		PatientDto updatePost = this.patientService.updatePatient(patientDto,patientId);
		return new ResponseEntity<PatientDto>(updatePost, HttpStatus.OK);

	}
	
	
	
	//method to serve files
    @GetMapping(value = "/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream())   ;

    }
    
    @PostMapping("/checkUp/{patientId}/{doctorId}")
    public ResponseEntity<PatientDto> updateCheckUp(@RequestBody PatientDto patient,@PathVariable Integer patientId,@PathVariable Integer doctorId)
    {
    	PatientDto p=this.patientService.checkingDetails(patient,patientId,doctorId);
    	return new ResponseEntity<PatientDto>(p,HttpStatus.OK);
    }

	
}
