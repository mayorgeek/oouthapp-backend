package com.kaptain.oouthapp.oouthapp.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DoctorRegisterRequest {
    private String name;
    private String password;
}
