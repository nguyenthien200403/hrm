package com.example.hrm.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthRequest {
    @NotBlank(message = "Not Null")
    private String email;
    @NotBlank(message = "Not Null")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setName(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
