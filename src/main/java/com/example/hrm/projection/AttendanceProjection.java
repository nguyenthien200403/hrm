package com.example.hrm.projection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public interface AttendanceProjection {
    String getId();
    LocalDate getDateWork();
    LocalTime getTimeIn();
    LocalTime getTimeOut();
    BigDecimal getTotalTime();
    String getStatus();
    String getNote();
}
