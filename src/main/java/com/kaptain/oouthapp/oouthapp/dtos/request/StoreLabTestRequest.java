package com.kaptain.oouthapp.oouthapp.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StoreLabTestRequest {
    @NotNull
    private String patientId;
    @NotNull
    private String doctorId;
    @NotNull
    private String testReceived;
    @NotNull
    private String suggestedMedicine;
    @NotNull
    private String result;
}
