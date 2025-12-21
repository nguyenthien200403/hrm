package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.service.TypeContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TypeContractController {
    private final TypeContractService service;

    @GetMapping("/admin/contract-types")
    public ResponseEntity<?> getAllNameTypeContract(){
        GeneralResponse<?> response = service.getAllNameTypeContract();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/admin/contract-types")
    public ResponseEntity<?> create(@RequestParam String name, @RequestParam Boolean hasSalary){
        GeneralResponse<?> response = service.create(name, hasSalary);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
