package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.docuseal.DocusealApiService;
import com.example.hrm.model.Account;
import com.example.hrm.request.ContractRequest;
import com.example.hrm.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;


    @PostMapping("/admin/contracts")
    public ResponseEntity<?> create(@Valid @RequestBody ContractRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String idAdmin = account.getEmployee().getId();
        GeneralResponse<?> response = contractService.create(idAdmin,request);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/{id}/contracts")
    public ResponseEntity<?> getAllContractsByEmployeeId(@PathVariable String id){
        GeneralResponse<?> response = contractService.getAllContractsByEmployeeId(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/contracts")
    public ResponseEntity<?> getAllContractsWithDateSign(@RequestParam(required = false) Boolean signed, @RequestParam String type){
        GeneralResponse<?> response = contractService.getAllContractsByDateSignAndType(signed, type);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/personal/contracts/{id}")
    public ResponseEntity<?> getDetailsByIdContract(@PathVariable String id){
        GeneralResponse<?> response = contractService.getDetailsByIdContract(id);
        return ResponseEntity.status(response.getStatus()).body(response);

    }

    @PutMapping("/admin/contracts/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody ContractRequest request){
        GeneralResponse<?> response = contractService.update(request, id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/contracts/search")
    public ResponseEntity<?> searchContractsBy(@RequestParam String keyword, @RequestParam String type){
        GeneralResponse<?> response = contractService.searchContractsBy(keyword, type);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/personal/contracts")
    public ResponseEntity<?> getAllContractsByPersonal(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();
        String id = account.getEmployee().getId();
        GeneralResponse<?> response = contractService.getAllContractsByEmployeeId(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("personal/contracts/{id}")
    public ResponseEntity<?> confirmContract(@PathVariable String id){
        LocalDateTime dateSign = LocalDateTime.now();
        GeneralResponse<?> response = contractService.confirmContract(id, dateSign);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
