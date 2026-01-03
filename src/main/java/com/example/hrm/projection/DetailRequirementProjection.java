package com.example.hrm.projection;

import java.time.LocalDate;

public interface DetailRequirementProjection {
    String getEmployeeId();
    String getEmployeeName();
    String getDepartmentName();
    String getRequirementName();
    LocalDate getDateBegin();
    LocalDate getDateEnd();
}
