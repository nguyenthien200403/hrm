package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.Account;
import com.example.hrm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<GeneralResponse<?>> showAllAccount(){
        GeneralResponse<?> response = accountService.getAllAccount();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

//    @GetMapping("/search-account")
//    public ResponseEntity<GeneralResponse<?>> showAccountByName(@RequestParam String name){
//        GeneralResponse<?> response = accountService.getAccountByName(name);
//        return ResponseEntity.status(response.getStatus()).body(response);
//    }


}
