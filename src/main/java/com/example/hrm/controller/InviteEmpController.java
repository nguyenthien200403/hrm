package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.Test;
import com.example.hrm.security.JwtUtil;
import com.example.hrm.service.InviteEmpService;
import com.example.hrm.service.TestService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invitation")
public class InviteEmpController {
    @Autowired
    private InviteEmpService inviteEmpService;

    @Autowired
    private TestService testService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/verify")
    public ResponseEntity<GeneralResponse<?>> verifyByEmail(@RequestParam String email){
        GeneralResponse<?> response = inviteEmpService.checkEmail(email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/fill-info")
    public ResponseEntity<GeneralResponse<?>> fillInfo(@RequestHeader("Authorization") String authHeader,
                                      @RequestBody Test test) {
        String token = authHeader.replace("Bearer ", "");
        Claims claims = jwtUtil.extractClaims(token);

        String email = claims.getSubject();
        GeneralResponse<?> response = inviteEmpService.checkEmail(email);
        if(response.getStatus() !=  HttpStatus.OK){
            return ResponseEntity.status(response.getStatus()).body(response);
        }

        GeneralResponse <?> response2 = testService.create(test, email);
        // xử lý cập nhật thông tin
        return ResponseEntity.status(response2.getStatus()).body(response2);
    }

}
