package com.DoctorApi.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DoctorAtCallApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctorAtCallApplication.class, args);
	}
	
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

}
