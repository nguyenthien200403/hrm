package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.request.EmployeeRequest;
import com.example.hrm.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
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


    @GetMapping("/admin/employees/{id}")
    public ResponseEntity<?> showDetailByID(@PathVariable String id){
        GeneralResponse<?> response = employeeService.getEmployeeByID(id);
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


}
