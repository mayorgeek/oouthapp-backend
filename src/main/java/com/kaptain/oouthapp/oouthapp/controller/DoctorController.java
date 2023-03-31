package com.kaptain.oouthapp.oouthapp.controller;

import com.kaptain.oouthapp.oouthapp.models.ApiResponse;
import com.kaptain.oouthapp.oouthapp.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorRepository doctorRepository;

    @GetMapping
    public ResponseEntity<ApiResponse> list() {
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(doctorRepository.findAll())
                                .build()
                );
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> count() {
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(doctorRepository.count())
                                .build()
                );
    }

}
