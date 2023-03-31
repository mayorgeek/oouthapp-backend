package com.kaptain.oouthapp.oouthapp.controller;

import com.kaptain.oouthapp.oouthapp.exceptions.AdminNotFoundException;
import com.kaptain.oouthapp.oouthapp.exceptions.DoctorNotFoundException;
import com.kaptain.oouthapp.oouthapp.exceptions.PatientNotFoundException;
import com.kaptain.oouthapp.oouthapp.models.ApiResponse;
import com.kaptain.oouthapp.oouthapp.repository.AdminRepository;
import com.kaptain.oouthapp.oouthapp.repository.DoctorRepository;
import com.kaptain.oouthapp.oouthapp.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final PatientRepository patientRepository;
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;

    @GetMapping("/patient")
    public ResponseEntity<ApiResponse> showPatientProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) throw new RuntimeException("No currently authenticated user!");

        Jwt principal = (Jwt) authentication.getPrincipal();

        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(patientRepository.findByPatientId(principal.getSubject())
                                        .orElseThrow(PatientNotFoundException::new))
                                .build()
                );
    }

    @GetMapping("/doctor")
    public ResponseEntity<ApiResponse> showDoctorProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) throw new RuntimeException("No currently authenticated user!");

        Jwt principal = (Jwt) authentication.getPrincipal();

        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(doctorRepository.findByDoctorId(principal.getSubject())
                                        .orElseThrow(DoctorNotFoundException::new))
                                .build()
                );
    }

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse> showAdminProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) throw new RuntimeException("No currently authenticated user!");

        Jwt principal = (Jwt) authentication.getPrincipal();

        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(adminRepository.findByEmail(principal.getSubject())
                                        .orElseThrow(AdminNotFoundException::new))
                                .build()
                );
    }

}
