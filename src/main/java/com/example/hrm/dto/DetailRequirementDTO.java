package com.example.hrm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailRequirementDTO {
    private String employeeName;
    private String departmentName;
    private String employeeId;
    private String requirementName;
    private LocalDateTime dateSend;
    private LocalDateTime dateConfirm;
    private LocalDate dateBegin;
    private LocalDate dateEnd;
    private BigDecimal amountHour;
    private Integer amountDate;
    private String content;
    private String note;
}
