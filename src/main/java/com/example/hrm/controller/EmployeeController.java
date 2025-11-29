package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.request.EmployeeRequest;
import com.example.hrm.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class EmployeeController {


    private final EmployeeService employeeService ;

    @PostMapping("/individuals")
    public ResponseEntity<?> create(@Valid @RequestBody EmployeeRequest request){
        GeneralResponse<?> response = employeeService.create(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/employees-new")
    public ResponseEntity<?> showAllByStatusProcessing(){
        GeneralResponse<?> response = employeeService.getAllByStatus("2");
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/employees-resigned")
    public ResponseEntity<?> showAllByStatusResigned(){
        GeneralResponse<?> response = employeeService.getAllByStatus("0");
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/employees/{id}")
    public ResponseEntity<?> showDetailByID(@PathVariable String id){
        GeneralResponse<?> response = employeeService.getEmployeeByID(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
