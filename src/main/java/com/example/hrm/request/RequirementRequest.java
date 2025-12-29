package com.example.hrm.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class RequirementRequest {
    @NotBlank(message = "Not Null")
    private String name;
    private Integer timeFrame;
    private BigDecimal overtimeRate;
    private String describe;

    @NotBlank(message = "Not Null")
    private String type;
}
