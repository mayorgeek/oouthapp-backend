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
public class StorePaymentRequest {
    @NotNull
    private String patientId;
    @NotNull
    private String paymentId;
    @NotNull
    private String time;
    @NotNull
    private String updatedBy;
}
