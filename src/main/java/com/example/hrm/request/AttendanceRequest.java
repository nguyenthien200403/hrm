package com.example.hrm.request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AttendanceRequest {
    private String ipPublic;

    private Boolean faceId;

    @NotNull(message = "Not Null")
    private LocalDate dateWork;

    @NotNull(message = "Not Null")
    private LocalTime timeIn;

    private LocalTime timeOut;

    private String note;
}
