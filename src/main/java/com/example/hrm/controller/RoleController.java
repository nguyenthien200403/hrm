package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.service.RoleService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/admin/roles")
    public ResponseEntity<?> getAllNameRole(){
        GeneralResponse<?> response = roleService.getAllNameRole();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/admin/roles")
    public ResponseEntity<?> create(@RequestParam String name, @RequestParam String describe){
        GeneralResponse<?> response = roleService.create(name, describe);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
