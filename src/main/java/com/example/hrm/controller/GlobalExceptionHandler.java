package com.example.hrm.controller;


import com.example.hrm.config.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex){
        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    assert fieldError.getDefaultMessage() != null;
                    return Map.of(
                            "field", fieldError.getField(),
                            "message",fieldError.getDefaultMessage()
                    );
                })
                .collect(Collectors.toList());
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                LocalDateTime.now(),
                errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralErrors(Exception ex) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_ERROR",
                LocalDateTime.now(),
                List.of(  Map.of("message", ex.getMessage()),
                        Map.of("cause", ex.getCause() != null ? ex.getCause().toString() : "null")
                )

        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
