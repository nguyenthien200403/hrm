package com.example.hrm.config;

import org.springframework.http.HttpStatus;

public class GeneralResponse<T> {
    private HttpStatus status;
    private String msg;
    private String type; // "login" hoặc "department"
    private T data;

    public GeneralResponse(HttpStatus status, String msg, String type, T data) {
        this.status = status;
        this.msg = msg;
        this.type = type;
        this.data = data;
    }

    public GeneralResponse() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(T data) {
        this.data = data;
    }
}
