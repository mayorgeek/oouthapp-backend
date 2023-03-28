package com.kaptain.oouthapp.oouthapp.controller.auth;

import com.kaptain.oouthapp.oouthapp.dtos.request.NewTransactionRequest;
import com.kaptain.oouthapp.oouthapp.entities.Transaction;
import com.kaptain.oouthapp.oouthapp.models.ApiResponse;
import com.kaptain.oouthapp.oouthapp.repository.TransactionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionRepository transactionRepository;

    @PostMapping
    public ResponseEntity<ApiResponse> saveTransaction(@RequestBody @Valid NewTransactionRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) throw new RuntimeException("No currently authenticated user!");

        Jwt principal = (Jwt) authentication.getPrincipal();

        Transaction transaction = Transaction.builder()
                .referenceId(String.valueOf(UUID.randomUUID()))
                .patientId(principal.getSubject())
                .paymentDate(request.getPaymentDate())
                .paymentTime(request.getPaymentTime())
                .amount(Double.valueOf(request.getAmount()))
                .build();
        transactionRepository.save(transaction);
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .message("Transaction Successful")
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listTransactions() {
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(transactionRepository.findAll())
                                .build()
                );
    }

}
