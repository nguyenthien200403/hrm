package com.example.hrm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDTO {
    private String id;
    private String employeeId;
    private String employeeName;
    private String departmentName;
    private Integer month;
    private Integer year;
    private BigDecimal workSalary;
    private BigDecimal overtimeDailySalary;
    private BigDecimal overtimeWeekendSalary;
    private BigDecimal overtimeHolidaySalary;
    private BigDecimal leaveSalary;
    private BigDecimal totalSalary;
}
