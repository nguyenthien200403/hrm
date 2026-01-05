package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.Account;
import com.example.hrm.request.DetailRequirementRequest;
import com.example.hrm.service.DetailRequirementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

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

    @GetMapping("/requirements/approved")
    private ResponseEntity<?> getAllDetailRequirementWithStatusAndDateConfirm(){
        GeneralResponse<?> response = service.getAllDetailRequirementWithStatusAndDateConfirm();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/personal/requirements-detail/amount")
    private ResponseEntity<?> getAmountDetailRequirementByDateAndEmployee(@RequestParam int month, @RequestParam int year){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        GeneralResponse<?> response = service.getAmountDetailRequirementByDateAndEmployee(id, month, year);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping("/personal/requirements-detail")
    private ResponseEntity<?> getAllDetailRequirementByEmployee(@RequestParam String status, @RequestParam String type,
                                                                @RequestParam int month, @RequestParam int year){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        GeneralResponse<?> response = service.getAllDetailRequirementByEmployee(id, status, type,month, year);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping("/manager/requirements-detail/amount")
    private ResponseEntity<?> getAmountDetailRequirementByDateAndManager(@RequestParam int month, @RequestParam int year){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        GeneralResponse<?> response = service.getAmountDetailRequirementByDateAndManager(id, month, year);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping("/manager/requirements-detail")
    private ResponseEntity<?> getAllDetailRequirementByManager(@RequestParam String status, @RequestParam String type,
                                                                @RequestParam int month, @RequestParam int year){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        GeneralResponse<?> response = service.getAllDetailRequirementByManager(id, status, type,month, year);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping("/admin/requirements-detail/amount")
    private ResponseEntity<?> getAmountDetailRequirementByDateAndAdmin(@RequestParam int month, @RequestParam int year){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        GeneralResponse<?> response = service.getAmountDetailRequirementByDateAndAdmin(id, month, year);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/requirements-detail")
    private ResponseEntity<?> getAllDetailRequirementByAdmin(@RequestParam String status, @RequestParam String type,
                                                               @RequestParam int month, @RequestParam int year){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        GeneralResponse<?> response = service.getAllDetailRequirementByAdmin(id, status, type,month, year);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/personal/leave-quota")
    private ResponseEntity<?> getRemainingStandardLeaveDays(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        GeneralResponse<?> response = service.getRemainingStandardLeaveDays(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
