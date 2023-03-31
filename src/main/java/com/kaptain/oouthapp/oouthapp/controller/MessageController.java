package com.kaptain.oouthapp.oouthapp.controller;

import com.kaptain.oouthapp.oouthapp.dtos.request.NewMessageRequest;
import com.kaptain.oouthapp.oouthapp.entities.Message;
import com.kaptain.oouthapp.oouthapp.models.ApiResponse;
import com.kaptain.oouthapp.oouthapp.repository.MessageRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepository messageRepository;

    @PostMapping
    public ResponseEntity<ApiResponse> save(@RequestBody @Valid NewMessageRequest request) {

        Message message = Message.builder()
                .patientId(request.getPatientId())
                .body(request.getBody())
                .build();
        messageRepository.save(message);

        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .message("Message sent successfully!")
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> list() {
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(messageRepository.findAll())
                                .build()
                );
    }

    @GetMapping("/patient")
    public ResponseEntity<ApiResponse> listPatientMessages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) throw new RuntimeException("No currently authenticated user!");

        Jwt principal = (Jwt) authentication.getPrincipal();

        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(messageRepository.findAllByPatientId(principal.getSubject()))
                                .build()
                );
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> count() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) throw new RuntimeException("No currently authenticated user!");

        Jwt principal = (Jwt) authentication.getPrincipal();

        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(messageRepository.findAllByPatientId(principal.getSubject()).size())
                                .build()
                );
    }

}
