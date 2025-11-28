package com.example.hrm.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RecruitmentRequest {
    @NotBlank(message = "NOT NULL")
    @Email(message = "Email is not in the correct format")
    private String email;

    @NotBlank(message = "NOT NULL")
    private String name;
}
