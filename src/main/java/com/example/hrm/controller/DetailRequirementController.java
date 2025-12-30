package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.Account;
import com.example.hrm.request.DetailRequirementRequest;
import com.example.hrm.service.DetailRequirementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DetailRequirementController {
    private final DetailRequirementService service;

    @PostMapping("/personal/requirements-detail")
    private ResponseEntity<?> create(@RequestBody @Valid DetailRequirementRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        GeneralResponse<?> response = service.create(id, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/personal/requirements-detail/{id}")
    private ResponseEntity<?> update(@PathVariable Long id,  @RequestBody @Valid DetailRequirementRequest request){
        GeneralResponse<?> response = service.update(id, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/personal/requirements-detail/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id){
        GeneralResponse<?> response = service.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/requirements/{id}/confirm")
    private ResponseEntity<?> confirm(@PathVariable Long id, @RequestParam String note, @RequestParam String status){
        GeneralResponse<?> response = service.confirm(id, note, status);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
