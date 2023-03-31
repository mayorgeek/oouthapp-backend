package com.kaptain.oouthapp.oouthapp.controller.auth;

import com.kaptain.oouthapp.oouthapp.dtos.request.AdminRegisterRequest;
import com.kaptain.oouthapp.oouthapp.dtos.request.DoctorRegisterRequest;
import com.kaptain.oouthapp.oouthapp.dtos.request.PatientRegisterRequest;
import com.kaptain.oouthapp.oouthapp.entities.Admin;
import com.kaptain.oouthapp.oouthapp.entities.Doctor;
import com.kaptain.oouthapp.oouthapp.entities.Patient;
import com.kaptain.oouthapp.oouthapp.models.ApiResponse;
import com.kaptain.oouthapp.oouthapp.repository.AdminRepository;
import com.kaptain.oouthapp.oouthapp.repository.DoctorRepository;
import com.kaptain.oouthapp.oouthapp.repository.PatientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;

    @PostMapping("/patient")
    public ResponseEntity<ApiResponse> createPatient(@RequestBody @Valid PatientRegisterRequest request) {
        String patientId = "P" + Math.round(Math.random() * 100000);

        Patient patient = Patient.builder()
                .patientId(patientId)
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        patientRepository.save(patient);

        String message = "Registration Successful. Your patient ID is " + patientId;

        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .message(message)
                                .build()
                );
    }

    @PostMapping("/doctor")
    public ResponseEntity<ApiResponse> createDoctor(@RequestBody @Valid DoctorRegisterRequest request) {
        String doctorId = "D" + Math.round(Math.random() * 100000);

        Doctor doctor = Doctor.builder()
                .doctorId(doctorId)
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        doctorRepository.save(doctor);

        String message = "Registration Successful. Your Doctor ID is " + doctorId;

        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .message(message)
                                .build()
                );
    }

    @PostMapping("/admin")
    public ResponseEntity<ApiResponse> createAdmin(@RequestBody @Valid AdminRegisterRequest request) {
        Admin admin = Admin.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        adminRepository.save(admin);

        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .message("Registration Successful")
                                .build()
                );
    }

}
