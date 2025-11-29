package com.example.hrm.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DepartmentRequest {
    @NotBlank(message = "Not Null")
    private String id;

    @NotBlank(message = "Not Null")
    private String name;

    private String describe;
}
