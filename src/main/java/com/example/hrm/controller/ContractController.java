package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.request.ContractRequest;
import com.example.hrm.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @PostMapping("/admin/contracts")
    public ResponseEntity<?> create(@Valid @RequestBody ContractRequest request){
        GeneralResponse<?> response = contractService.create(request);

        return ResponseEntity.status(response.getStatus()).body(response);

    }

}
