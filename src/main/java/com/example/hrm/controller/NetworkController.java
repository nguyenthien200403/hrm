package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.request.NetworkRequest;
import com.example.hrm.service.NetworkService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NetworkController {
    private final NetworkService service;

    @GetMapping("/admin/networks")
    private ResponseEntity<?> getAllNetwork(){
        GeneralResponse<?> response = service.getAllNetwork();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/admin/networks")
    private ResponseEntity<?> create(@Valid @RequestBody NetworkRequest request){
        GeneralResponse<?> response = service.create(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/admin/networks/{id}")
    private ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody NetworkRequest request){
        GeneralResponse<?> response = service.update(id, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/admin/networks/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id){
        GeneralResponse<?> response = service.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
