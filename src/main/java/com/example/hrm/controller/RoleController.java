package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.service.RoleService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/admin/roles-name")
    public ResponseEntity<?> getAllNameRole(){
        GeneralResponse<?> response = roleService.getAllNameRole();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
