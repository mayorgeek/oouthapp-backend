package com.kaptain.oouthapp.oouthapp.repository;

import com.kaptain.oouthapp.oouthapp.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByPatientId(String patientId);

    List<Appointment> findAllByDoctorName(String doctorName);
}