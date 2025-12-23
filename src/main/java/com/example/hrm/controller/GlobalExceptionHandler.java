package com.example.hrm.controller;



import com.example.hrm.config.GeneralResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;





@RestControllerAdvice
public class GlobalExceptionHandler {

    // Bắt lỗi vi phạm constraint từ DB (UNIQUE, NOT NULL, CHECK, FOREIGN KEY...)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GeneralResponse<?>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Throwable rootCause = ex.getMostSpecificCause(); // chỉ có trong DataAccessException
        String message = rootCause.getMessage();

        GeneralResponse<?> response = new GeneralResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                message,
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Bắt lỗi validate từ Hibernate Validator (@NotNull, @Size, @Email...)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GeneralResponse<?>> handleConstraintViolation(ConstraintViolationException ex) {
        GeneralResponse<?> response = new GeneralResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed: " + ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Bắt lỗi chung (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralResponse<?>> handleGenericException(Exception ex) {
        GeneralResponse<?> response = new GeneralResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unexpected error: " + ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}





