package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.Account;
import com.example.hrm.request.AttendanceRequest;
import com.example.hrm.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService service;

    @GetMapping("personal/confirmations/date-work")
    private ResponseEntity<?> checkDateWork(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        LocalDate date = LocalDate.now();
        GeneralResponse<?> response = service.checkDateWork(id, date);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("personal/check-in")
    private ResponseEntity<?> checkIn(@Valid @RequestBody AttendanceRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        String msg = "Check-In Successful";
        GeneralResponse<?> response = service.checkIn(id, request, msg);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @PostMapping("admin/attendances/{id}")
    private ResponseEntity<?> create(@PathVariable String id, @Valid @RequestBody AttendanceRequest request){
        String msg = "Create Successful";
        GeneralResponse<?> response = service.create(id, request, msg);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @PutMapping("personal/check-out/{id}")
    private ResponseEntity<?> checkOut(@PathVariable String id, @RequestParam LocalTime end){
        GeneralResponse<?> response = service.checkOut(id, end);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping("/personal/attendances")
    private ResponseEntity<?> getAllByIdEmployeeAndDateWork(@RequestParam int month, @RequestParam int year){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        GeneralResponse<?> response = service.getAllByIdEmployeeAndDateWork(id, month, year);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/attendances/search")
    private ResponseEntity<?> searchByIdEmployeeAndDateWork(@RequestParam String id, @RequestParam LocalDate date){
        GeneralResponse<?> response = service.searchByIdEmployeeAndDateWork(id, date);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/admin/attendances/{id}")
    private ResponseEntity<?> update(@PathVariable String id, @RequestBody AttendanceRequest request){
        GeneralResponse<?> response = service.update(id, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/attendances/amount")
    private ResponseEntity<?> countAttendancesByDateWork(){
        GeneralResponse<?> response = service.countAttendancesByDateWork();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/attendances/late")
    private ResponseEntity<?> countLateAttendancesByDateWork(){
        GeneralResponse<?> response = service.countLateAttendancesByDateWork();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/attendances")
    private ResponseEntity<?> getAllAttendanceByAdmin(@RequestParam int month,
                                                      @RequestParam int year,
                                                      @RequestParam String employeeName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        GeneralResponse<?> response = service.getAllAttendanceByAdmin(id, month, year, employeeName);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping("/manager/attendances")
    private ResponseEntity<?> getAllAttendanceByManager(@RequestParam int month,
                                                      @RequestParam int year,
                                                      @RequestParam String employeeName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        GeneralResponse<?> response = service.getAllAttendanceByManager(id, month, year, employeeName);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


}
