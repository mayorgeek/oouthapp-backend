package com.kaptain.oouthapp.oouthapp.controller.auth;

import com.kaptain.oouthapp.oouthapp.dtos.request.NewAppointmentRequest;
import com.kaptain.oouthapp.oouthapp.entities.Appointment;
import com.kaptain.oouthapp.oouthapp.models.ApiResponse;
import com.kaptain.oouthapp.oouthapp.repository.AppointmentRepository;
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

    @PostMapping
    public ResponseEntity<ApiResponse> saveAppointment(@RequestBody @Valid NewAppointmentRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) throw new RuntimeException("No currently authenticated user!");

        Jwt principal = (Jwt) authentication.getPrincipal();

        Appointment appointment = Appointment.builder()
                .patientId(principal.getSubject())
                .fullName(request.getName())
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

    @GetMapping
    public ResponseEntity<ApiResponse> listAppointments() {
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(appointmentRepository.findAll())
                                .build()
                );
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> countAppointments() {
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(appointmentRepository.count())
                                .build()
                );
    }

}
