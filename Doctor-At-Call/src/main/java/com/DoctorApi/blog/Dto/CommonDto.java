package com.DoctorApi.blog.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CommonDto {

	    private int id;
	    private String name;
	    private String email;
	    private String password;
	    private String phoneNumber;
	    private String education;
	    private String photo;
	    private String address;

}
