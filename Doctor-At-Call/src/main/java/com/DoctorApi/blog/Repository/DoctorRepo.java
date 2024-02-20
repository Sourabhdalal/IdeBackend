package com.DoctorApi.blog.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DoctorApi.blog.Entity.Doctor;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Integer> 
{
	@Query("SELECT d FROM Doctor d WHERE d.doctorEmail = ?1 AND d.password = ?2")
    Optional<Doctor> findByUsernameAndPassword(String username, String password);
}
