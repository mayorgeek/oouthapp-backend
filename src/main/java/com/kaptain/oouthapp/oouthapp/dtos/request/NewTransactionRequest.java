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
public class NewTransactionRequest {
    @NotNull
    private String paymentDate;
    @NotNull
    private String paymentTime;
    @NotNull
    private String amount;
}