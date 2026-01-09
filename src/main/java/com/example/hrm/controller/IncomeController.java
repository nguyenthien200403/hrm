package com.example.hrm.controller;


import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.Account;
import com.example.hrm.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IncomeController {
    private final IncomeService service;

    @GetMapping("/personal/incomes")
    public ResponseEntity<?> getAllIncomeByEmployee(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        GeneralResponse<?> response = service.getAllIncomeByEmployee(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/incomes")
    public ResponseEntity<?> getAllIncomeByDepartmentName(@RequestParam String departName){
        GeneralResponse<?> response = service.getAllIncomeByDepartmentName(departName);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
