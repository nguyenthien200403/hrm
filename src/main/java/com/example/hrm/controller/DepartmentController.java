package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.request.DepartmentRequest;
import com.example.hrm.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/admin/departments")
    public ResponseEntity<?> getAll(){
        GeneralResponse<?> response = departmentService.getAll();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/admin/departments")
    public ResponseEntity<?> create(@Valid @RequestBody DepartmentRequest request){
        GeneralResponse<?> response = departmentService.create(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/admin/departments/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestParam String name, @RequestParam String describe){
        GeneralResponse<?> response = departmentService.update(id, name, describe);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/departments/name")
    public ResponseEntity<?> getAllByName(){
        GeneralResponse<?> response = departmentService.getAllByName();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
