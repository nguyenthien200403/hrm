package com.example.hrm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RecruitmentDTO {
    @NotBlank(message = "NOT NULL")
    @Email(message = "Email is not in the correct format")
    private String email;

    @NotBlank(message = "NOT NULL")
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
