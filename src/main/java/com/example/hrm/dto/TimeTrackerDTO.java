package com.example.hrm.dto;

import com.example.hrm.model.TimeTracker;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class TimeTrackerDTO {
    @NotNull(message = "Not Null")
    private LocalTime start;

    @NotNull(message = "Not Null")
    private LocalTime end;

    private String note;

    public TimeTrackerDTO(TimeTracker timeTracker){
        this.start = timeTracker.getStartTime();
        this.end = timeTracker.getEndTime();
        this.note = timeTracker.getNote();
    }
}
