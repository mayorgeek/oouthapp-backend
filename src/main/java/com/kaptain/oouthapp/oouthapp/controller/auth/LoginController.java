package com.kaptain.oouthapp.oouthapp.controller.auth;

import com.kaptain.oouthapp.oouthapp.dtos.request.AdminLoginRequest;
import com.kaptain.oouthapp.oouthapp.dtos.request.DoctorLoginRequest;
import com.kaptain.oouthapp.oouthapp.dtos.request.PatientLoginRequest;
import com.kaptain.oouthapp.oouthapp.dtos.response.LoginResponse;
import com.kaptain.oouthapp.oouthapp.entities.Admin;
import com.kaptain.oouthapp.oouthapp.entities.Doctor;
import com.kaptain.oouthapp.oouthapp.entities.Patient;
import com.kaptain.oouthapp.oouthapp.exceptions.AdminNotFoundException;
import com.kaptain.oouthapp.oouthapp.exceptions.DoctorNotFoundException;
import com.kaptain.oouthapp.oouthapp.exceptions.PatientNotFoundException;
import com.kaptain.oouthapp.oouthapp.repository.AdminRepository;
import com.kaptain.oouthapp.oouthapp.repository.DoctorRepository;
import com.kaptain.oouthapp.oouthapp.repository.PatientRepository;
import com.kaptain.oouthapp.oouthapp.security.JWTUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
@RequiredArgsConstructor
public class LoginController {

    private final JWTUtil jwtUtil;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AdminRepository adminRepository;

    @PostMapping("/patient")
    public ResponseEntity<LoginResponse> loginPatient(@RequestBody @Valid PatientLoginRequest request) {
        Patient patient = patientRepository.findByPatientId(request.getPatientId())
                .orElseThrow(PatientNotFoundException::new);

        String token = jwtUtil.generateTokenForPatient(patient.getPatientId());

        return ResponseEntity.ok()
                .body(
                       LoginResponse.builder()
                               .token(token)
                               .build()
                );
    }

    @PostMapping("/doctor")
    public ResponseEntity<LoginResponse> loginDoctor(@RequestBody @Valid DoctorLoginRequest request) {
        Doctor doctor = doctorRepository.findByDoctorId(request.getDoctorId())
                .orElseThrow(DoctorNotFoundException::new);

        String token = jwtUtil.generateTokenForDoctors(doctor.getDoctorId());

        return ResponseEntity.ok()
                .body(
                        LoginResponse.builder()
                                .token(token)
                                .build()
                );
    }

    @PostMapping("/admin")
    public ResponseEntity<LoginResponse> loginAdmin(@RequestBody @Valid AdminLoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(AdminNotFoundException::new);

        String token = jwtUtil.generateTokenForAdmin(admin.getEmail());

        return ResponseEntity.ok()
                .body(
                        LoginResponse.builder()
                                .token(token)
                                .build()
                );
    }

}
