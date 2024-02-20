package com.DoctorApi.blog.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DoctorApi.blog.Entity.Patient;


@Repository
public interface PatientRepo extends JpaRepository<Patient,Integer> 
{
	@Query("SELECT p FROM Patient p WHERE p.patientEmail = ?1 AND p.password = ?2")
    Optional<Patient> findByUsernameAndPassword(String username, String password);
}
