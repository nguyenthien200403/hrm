package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.EmployeeDTO;
import com.example.hrm.dto.RecruitmentDTO;
import com.example.hrm.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee/fill-info")
    public ResponseEntity<?> create(@Valid @RequestBody EmployeeDTO dto){
        String email = "";
        GeneralResponse<?> response = employeeService.create(dto, email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/employees/new")
    public ResponseEntity<?> showAllByStatusProcessing(){
        GeneralResponse<?> response = employeeService.getAllByStatus("2");
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/employees/resigned")
    public ResponseEntity<?> showAllByStatusResigned(){
        GeneralResponse<?> response = employeeService.getAllByStatus("0");
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/admin/employee/detail")
    public ResponseEntity<?> showDetailByID(@RequestParam String id){
        GeneralResponse<?> response = employeeService.getEmployeeByID(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
