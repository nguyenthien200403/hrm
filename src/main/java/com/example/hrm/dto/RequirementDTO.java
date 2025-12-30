package com.example.hrm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequirementDTO {
    private Long id;
    private String name;
    private Integer timeFrame; // chỉ cho nghỉ phép có lương
    private BigDecimal overtimeRate;
    private String describe;
    private String nameType;
}
