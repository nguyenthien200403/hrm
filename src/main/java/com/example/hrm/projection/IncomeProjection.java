package com.example.hrm.projection;


import java.math.BigDecimal;

public interface IncomeProjection {
    String getId();
    Integer getMonth();
    Integer getYear();
    BigDecimal getWorkSalary();
    BigDecimal getOvertimeDailySalary();
    BigDecimal getOvertimeWeekendSalary();
    BigDecimal getOvertimeHolidaySalary();
    BigDecimal getLeaveSalary();
    BigDecimal getTotalSalary();
}
