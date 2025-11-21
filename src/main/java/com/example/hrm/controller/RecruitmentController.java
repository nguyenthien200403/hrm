package com.example.hrm.controller;


import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.RecruitmentDTO;
import com.example.hrm.service.RecruitmentService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecruitmentController {

    @Autowired
    private RecruitmentService recruitmentService;

    @PostMapping("/email-verify")
    public ResponseEntity<?> verifyEmail(@RequestParam String email){
        GeneralResponse<?> response = recruitmentService.verifyEmail(email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/admin/recruitment")
    public ResponseEntity<?> create(@Valid @RequestBody RecruitmentDTO dto){
        GeneralResponse<?> response = recruitmentService.create(dto);
        return  ResponseEntity.status(response.getStatus()).body(response);
    }
}
