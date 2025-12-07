package com.example.hrm.controller;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.request.ContractRequest;
import com.example.hrm.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @PostMapping("/admin/contracts")
    public ResponseEntity<?> create(@Valid @RequestBody ContractRequest request){
        GeneralResponse<?> response = contractService.create(request);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/contracts/employee")
    public ResponseEntity<?> getAllContractsByEmployeeId(@RequestParam String id){
        GeneralResponse<?> response = contractService.getAllContractsByEmployeeId(id);
        return ResponseEntity.status(response.getStatus()).body(response);

    }

    @GetMapping("/admin/contracts")
    public ResponseEntity<?> getAllContractsWithDateSign(@RequestParam Boolean signed, @RequestParam String type){
        GeneralResponse<?> response = contractService.getAllByDateSignAndType(signed, type);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/contracts/{id}")
    public ResponseEntity<?> getDetailsByIdContract(@PathVariable String id){
        GeneralResponse<?> response = contractService.getDetailsByIdContract(id);
        return ResponseEntity.status(response.getStatus()).body(response);

    }

    @PutMapping("/admin/contracts/{id}")
    public ResponseEntity<?> update(@PathVariable String id,@Valid @RequestBody ContractRequest request){
        GeneralResponse<?> response = contractService.update(request, id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/admin/contracts/search")
    public ResponseEntity<?> searchContractsBy(@RequestParam String keyword, @RequestParam String type){
        GeneralResponse<?> response = contractService.searchContractsBy(keyword, type);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


}
