package com.example.hrm.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneralResponse<T> {
    private int status;
    private String msg;
    private T data;
}
