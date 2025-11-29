package com.example.hrm.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Not Null")
    private String idEmployee;

    @NotBlank(message = "Not Null")
    private String nameRole;
}
