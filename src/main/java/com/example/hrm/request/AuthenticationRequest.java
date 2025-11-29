package com.example.hrm.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthenticationRequest {
    @NotBlank(message = "Not Null")
    private String nameAccount;

    @NotBlank(message = "Not Null")
    private String password;
}
