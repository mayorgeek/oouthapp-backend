package com.kaptain.oouthapp.oouthapp.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientRegisterRequest {
    @NotNull
    private String name;
    @NotNull
    private String password;
//    @NotNull
//    private String city;
}
