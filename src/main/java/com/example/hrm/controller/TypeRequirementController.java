package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.service.TypeRequirementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TypeRequirementController {
    private final TypeRequirementService service;

    @PostMapping("/admin/requirement-types")
    public ResponseEntity<?> create(@RequestParam String name){
        GeneralResponse<?> response = service.create(name);
        return ResponseEntity.status(response.getStatus()). body(response);
    }

    @GetMapping("personal/requirement-types")
    private ResponseEntity<?> getAllNameTypeRequirement(){
        GeneralResponse<?> response = service.getAllNameTypeRequirement();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

//    @GetMapping("personal/requirement-types")
//    private ResponseEntity<?> getAllTypeRequirement(){
//        GeneralResponse<?> response = service.getAllTypeRequirement();
//        return ResponseEntity.status(response.getStatus()).body(response);
//    }
}
