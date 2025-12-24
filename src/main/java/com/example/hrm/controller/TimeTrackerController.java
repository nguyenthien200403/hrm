package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.TimeTrackerDTO;
import com.example.hrm.service.TimeTrackerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
public class TimeTrackerController {
    private final TimeTrackerService service;

    @GetMapping("/personal/time-tracker")
    private ResponseEntity<?> getTimeTracker(){
        GeneralResponse<?> response = service.getTimeTracker();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/admin/time-tracker")
    private ResponseEntity<?> setupTimeTracker(@Valid @RequestBody TimeTrackerDTO dto){
        GeneralResponse<?> response = service.setupTimeTracker(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
