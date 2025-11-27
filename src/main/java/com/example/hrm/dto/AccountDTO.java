package com.example.hrm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountDTO {

    @NotBlank(message = "Not Null")
    private String nameAccount;

    @NotBlank(message = "Not Null")
    private String password;

    @NotBlank(message = "Not Null")
    private String idEmployee;

    @NotBlank(message = "Not Null")
    private String nameRole;
}
