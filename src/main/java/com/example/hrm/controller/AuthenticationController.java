package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.request.AuthenticationRequest;
import com.example.hrm.request.RegisterRequest;
import com.example.hrm.service.AuthenticationService;
import com.example.hrm.service.OTPService;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final OTPService otpService;

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

    @PutMapping("/accounts")
    public ResponseEntity<?> changeInfo(@Valid @RequestBody AuthenticationRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GeneralResponse<?> response = service.changeInfo(authentication, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email){
        otpService.forgotPassword(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verifications/otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp){
        GeneralResponse<?> response = otpService.verifyOtp(email, otp);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String newPassword){
        GeneralResponse<?> response = service.resetPassword(email, newPassword);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/accounts")
    public ResponseEntity<?> getAccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nameAccount = authentication.getName();
        GeneralResponse<?> response = service.getAccount(nameAccount);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
