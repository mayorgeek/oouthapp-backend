package com.kaptain.oouthapp.oouthapp.controller;

import com.kaptain.oouthapp.oouthapp.dtos.request.StoreDrugRequest;
import com.kaptain.oouthapp.oouthapp.entities.Drug;
import com.kaptain.oouthapp.oouthapp.models.ApiResponse;
import com.kaptain.oouthapp.oouthapp.repository.DrugRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drugs")
@RequiredArgsConstructor
public class DrugController {

    private final DrugRepository drugRepository;

    @PostMapping
    public ResponseEntity<ApiResponse> store(@RequestBody @Valid StoreDrugRequest request) {
        String reference = "M" + Math.round(Math.random() * 1000000);

        Drug drug = Drug.builder()
                .name(request.getName())
                .reference(reference)
                .treatment(request.getTreatment())
                .build();
        drugRepository.save(drug);

        String message = "Successfully stored drug data for medicine " + reference;

        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .message(message)
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> list() {
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .data(drugRepository.findAll())
                                .build()
                );
    }

}
