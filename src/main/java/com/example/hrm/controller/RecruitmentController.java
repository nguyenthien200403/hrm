package com.example.hrm.controller;


import com.example.hrm.config.GeneralResponse;
import com.example.hrm.request.RecruitmentRequest;
import com.example.hrm.service.RecruitmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController

public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @PostMapping("/confirmations")
    public ResponseEntity<?> verifyEmail(@RequestParam String email){
        GeneralResponse<?> response = recruitmentService.verifyEmail(email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/admin/recruitments")
    public ResponseEntity<?> create(@Valid @RequestBody RecruitmentRequest request){
        GeneralResponse<?> response = recruitmentService.create(request);
        return  ResponseEntity.status(response.getStatus()).body(response);
    }
}
