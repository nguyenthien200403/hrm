package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.EmployeeDTO;
import com.example.hrm.model.Account;
import com.example.hrm.request.EmployeeRequest;
import com.example.hrm.security.JwtService;
import com.example.hrm.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class EmployeeController {


    private final EmployeeService employeeService ;
    private final JwtService jwtService;

    @PostMapping("/individuals")
    public ResponseEntity<?> create(@Valid @RequestBody EmployeeRequest request){
        GeneralResponse<?> response = employeeService.create(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping("/admin/employees/{id}")
    public ResponseEntity<?> showDetailByID(@PathVariable String id){
        GeneralResponse<?> response = employeeService.getEmployeeById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/admin/verifications/{id}")
    public ResponseEntity<?> verifyEmployee(@PathVariable String id, @RequestParam String nameDepart){
        GeneralResponse<?> response = employeeService.verifyEmployee(id, nameDepart);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/employees/amount-active")
    public ResponseEntity<?> amountEmployeeActive(){
        GeneralResponse<?> response = employeeService.amountEmployeeByStatus("1", "Amount Employee Active");
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/employees/amount-processing")
    public ResponseEntity<?> amountEmployeeProcessing(){
        GeneralResponse<?> response = employeeService.amountEmployeeByStatus("2", "Amount Employee Processing");
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping("/admin/employees")
    public ResponseEntity<?> getAllByStatusAndDepartment(@RequestParam String status,
                                                         @RequestParam(required = false) String name){
        GeneralResponse<?> response = employeeService.getAllByStatusAndDepartment(status, name);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/employees/search")
    public ResponseEntity<?> searchEmployeesBy(@RequestParam String keyword,
                                               @RequestParam String name,
                                               @RequestParam String status){
        GeneralResponse<?> response = employeeService.searchEmployeesBy(keyword, name, status);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/admin/employees/resign/{id}")
    public ResponseEntity<?> resign(@PathVariable String id){
        GeneralResponse<?> response = employeeService.resignConfirmation(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/personal")
    public ResponseEntity<?> getPersonal(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        GeneralResponse<?> response = employeeService.getEmployeeById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/personal")
    public ResponseEntity<?> update(@Valid @RequestBody EmployeeDTO request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        GeneralResponse<?> response = employeeService.update(id, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
