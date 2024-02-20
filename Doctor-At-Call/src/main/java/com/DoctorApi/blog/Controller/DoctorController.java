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

import com.DoctorApi.blog.Dto.DoctorDto;
import com.DoctorApi.blog.Dto.PrescriptionDto;
import com.DoctorApi.blog.Service.FileService;
import com.DoctorApi.blog.ServiceImpl.DoctorServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/doctor")
public class DoctorController
{
	@Autowired
	DoctorServiceImpl doctorService;
	
	@Value("${project.images")
	private String path;
	
	
	@Autowired
	private FileService fileService;
	
	
	
	@PostMapping("/")
	public ResponseEntity<DoctorDto> createDoctor(@RequestBody DoctorDto doctorDto)
	{
		DoctorDto createdDoctor = this.doctorService.createDoctor(doctorDto);
		
		return new ResponseEntity<DoctorDto>(createdDoctor,HttpStatus.CREATED);
	}
	
	@PutMapping("/{doctorId}")
	public ResponseEntity<DoctorDto> updateDoctor(@RequestBody DoctorDto doctorDto,@PathVariable Integer doctorId)
	{
		DoctorDto updatedDoctor = this.doctorService.updateDoctor(doctorDto, doctorId);
		
		return ResponseEntity.ok(updatedDoctor);
	}
	
	@GetMapping("/getDoctor")
	public ResponseEntity<List<DoctorDto>> displayAllDoctor()
	{
		return ResponseEntity.ok(this.doctorService.getAllDoctor());
	}
	
	
	@GetMapping("/{doctorId}")
	public ResponseEntity<DoctorDto> singleDoctor(@PathVariable Integer doctorId)
	{
		return ResponseEntity.ok(this.doctorService.getDoctorById(doctorId));
	}
	
	@DeleteMapping("/{doctorId}")
	public void deleteDoctor(@PathVariable Integer doctorId)
	{
		this.doctorService.deleteDoctor(doctorId);
		//another method using by making class ApiResponse
		
		//return new ResponseEntity<ApiResponse>(new ApiResponse("Doctor Delete Successfully",true),HttpStatus.OK);
		
		//return new ResponseEntity<ApiResponse>(Map.of("message","User delete Successfully"),HttpStatus.OK);
	}
	
	// post image upload

		@PostMapping("/image/upload/{doctorId}")
		public ResponseEntity<DoctorDto> uploadPostImage(@RequestParam("image") MultipartFile image,
				@PathVariable Integer doctorId) throws IOException {

			DoctorDto doctorDto = this.doctorService.getDoctorById(doctorId);
			
			String fileName = this.fileService.uploadImage(path, image);
			doctorDto.setDoctorePhoto(fileName);
			DoctorDto updatePost = this.doctorService.updateDoctor(doctorDto, doctorId);
			return new ResponseEntity<DoctorDto>(updatePost, HttpStatus.OK);

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
	    
	    @DeleteMapping("/cancel/{doctorId}/{patientId}")
	    public ResponseEntity<DoctorDto> deletePatient(@PathVariable Integer doctorId,@PathVariable Integer patientId)
	    {
	    	DoctorDto d=this.doctorService.deletePatient(doctorId,patientId);
	    	
	    	return new ResponseEntity<DoctorDto>(d,HttpStatus.OK);
	    }
	    
	    
	    @PostMapping("/accept/{doctorId}/{patientId}")
	    public ResponseEntity<DoctorDto> acceptPatient(@PathVariable Integer doctorId,@PathVariable Integer patientId)
	    {
	    	DoctorDto d=this.doctorService.acceptPatient(doctorId,patientId);
	    	
	    	return new ResponseEntity<DoctorDto>(d,HttpStatus.OK);
	    }
	    
	    
	    @PostMapping("/prescription/{doctorId}/{patientId}")
	    public ResponseEntity<DoctorDto> generatePrescription(@RequestBody PrescriptionDto prescDto,
	    		@PathVariable Integer doctorId,@PathVariable Integer patientId)
	    {
	    	DoctorDto d=this.doctorService.generatePrescription(prescDto,doctorId,patientId);
	    	
	    	
	    	return new ResponseEntity<DoctorDto>(d,HttpStatus.OK);
	    }
}
