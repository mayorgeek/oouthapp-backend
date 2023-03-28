package com.kaptain.oouthapp.oouthapp.repository;

import com.kaptain.oouthapp.oouthapp.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
//    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findByDoctorId(String doctorId);
}