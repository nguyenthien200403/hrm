package com.example.hrm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    private String id;
    private LocalDate dateWork;
    private String employeeName;
    private String employeeId;
    private String departName;
    private LocalTime timeIn;
    private LocalTime timeOut;
    private BigDecimal totalTime;
    private String note;
    private String status;
}
