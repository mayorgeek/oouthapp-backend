package com.kaptain.oouthapp.oouthapp.controller;

import com.kaptain.oouthapp.oouthapp.dtos.request.AddPrescriptionRequest;
import com.kaptain.oouthapp.oouthapp.dtos.request.NewAppointmentRequest;
import com.kaptain.oouthapp.oouthapp.entities.Appointment;
import com.kaptain.oouthapp.oouthapp.entities.Doctor;
import com.kaptain.oouthapp.oouthapp.entities.Drug;
import com.kaptain.oouthapp.oouthapp.exceptions.AppointmentNotFoundException;
import com.kaptain.oouthapp.oouthapp.exceptions.DoctorNotFoundException;
import com.kaptain.oouthapp.oouthapp.models.ApiResponse;
import com.kaptain.oouthapp.oouthapp.repository.AppointmentRepository;
import com.kaptain.oouthapp.oouthapp.repository.DoctorRepository;
import com.kaptain.oouthapp.oouthapp.repository.DrugRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/appointments")
@RestController
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final DrugRepository drugRepository;
    private final DoctorRepository doctorRepository;

    @PostMapping
    public ResponseEntity<ApiResponse> saveAppointment(@RequestBody @Valid NewAppointmentRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) throw new RuntimeException("No currently authenticated user!");

        Jwt principal = (Jwt) authentication.getPrincipal();

        Appointment appointment = Appointment.builder()
                .patientId(principal.getSubject())
                .doctorName(request.getDoctorName())
                .preferredDate(request.getPreferredDate())
                .preferredTime(request.getPreferredTime())
                .build();
        appointmentRepository.save(appointment);

        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .message("Appointment saved successfully")
                                .build()
                );
    }

    @GetMapping("/patient")
    public ResponseEntity<ApiResponse> listPatientAppointments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) throw new RuntimeException("No currently authenticated user!");

        Jwt principal = (Jwt) authentication.getPrincipal();

        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(appointmentRepository.findAllByPatientId(principal.getSubject()))
                                .build()
                );
    }

    @GetMapping("/doctor/{doctorName}")
    public ResponseEntity<ApiResponse> listDoctorAppointments(@PathVariable String doctorName) {
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(appointmentRepository.findAllByDoctorName(doctorName))
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listAppointments() {
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(appointmentRepository.findAll())
                                .build()
                );
    }

    @GetMapping("/doctor/count")
    public ResponseEntity<ApiResponse> countDoctorAppointments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) throw new RuntimeException("No currently authenticated user!");

        Jwt principal = (Jwt) authentication.getPrincipal();

        Doctor doctor = doctorRepository.findByDoctorId(principal.getSubject())
                .orElseThrow(DoctorNotFoundException::new);

        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(appointmentRepository.findAllByDoctorName(doctor.getName()))
                                .build()
                );
    }

    @GetMapping("/patient/count")
    public ResponseEntity<ApiResponse> countPatientAppointments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) throw new RuntimeException("No currently authenticated user!");

        Jwt principal = (Jwt) authentication.getPrincipal();

        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(appointmentRepository.findAllByPatientId(principal.getSubject()))
                                .build()
                );
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> count() {
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(appointmentRepository.count())
                                .build()
                );
    }

    @PostMapping("/add-prescription")
    public ResponseEntity<ApiResponse> addPrescription(@RequestBody @Valid AddPrescriptionRequest request) {

        Appointment appointment = appointmentRepository.findById(request.getAppointment())
                .orElseThrow(AppointmentNotFoundException::new);

        Drug drug = drugRepository.findByReference(request.getDrug());
        appointment.setPrescription(drug.getName());

        appointmentRepository.save(appointment);

        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .message("Prescription added successfully!")
                                .build()
                );
    }

}
