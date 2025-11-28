package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.request.AuthRequest;
import com.example.hrm.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest){
        GeneralResponse<?> response = service.authenticate(authRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

//    @PostMapping("/register")
//    public ResponseEntity<?> login(@Valid @RequestBody AccountDTO dto){
//        //
//    }

}
