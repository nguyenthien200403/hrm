package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.InviteEmployee;

import com.example.hrm.service.InviteEmpService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setting")
public class SettingController {
    @Autowired
    private InviteEmpService inviteEmpService;

    @PostMapping("/invite-employee")
    public ResponseEntity<GeneralResponse<?>> createInvite(@RequestBody InviteEmployee inviteEmployee) {
        GeneralResponse<?> response = inviteEmpService.createInvite(inviteEmployee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
