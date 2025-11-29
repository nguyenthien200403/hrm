package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.request.AuthenticationRequest;
import com.example.hrm.request.RegisterRequest;
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

    @PostMapping("/authentications")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequest request){
        GeneralResponse<?> response = service.authenticate(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/admin/registrations")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
        GeneralResponse<?> response = service.register(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
