package com.example.hrm.controller;


import com.example.hrm.config.GeneralResponse;
import com.example.hrm.request.RequirementRequest;
import com.example.hrm.service.RequirementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RequirementController {
    private final RequirementService service;

    @GetMapping("/personal/requirements")
    public ResponseEntity<?> getRequirementByType(@RequestParam(required = false) String type){
        GeneralResponse<?> response = service.getRequirementByType(type);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/admin/requirements")
    public ResponseEntity<?> create(@RequestBody @Valid RequirementRequest request){
        GeneralResponse<?> response = service.create(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/admin/requirements/{id}")
    public ResponseEntity<?> create(@PathVariable Long id, @Valid @RequestBody RequirementRequest request){
        GeneralResponse<?> response = service.update(id, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/admin/requirements/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        GeneralResponse<?> response = service.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
