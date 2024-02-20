package com.DoctorApi.blog.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DoctorApi.blog.Entity.Prescription;

public interface PrescriptionRepo extends JpaRepository<Prescription,Integer> {

}
