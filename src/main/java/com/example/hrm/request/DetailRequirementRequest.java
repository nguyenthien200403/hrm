package com.example.hrm.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DetailRequirementRequest {
    @NotNull(message = "Not Null")
    private LocalDate dateBegin;

    private LocalDate dateEnd;

    private BigDecimal amountHour;

    private String content;

    @NotBlank(message = "Not Null")
    private String requirement;
}
