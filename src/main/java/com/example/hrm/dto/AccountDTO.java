package com.example.hrm.dto;

import com.example.hrm.model.Account;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountDTO {
    private String nameAccount;
    private String idEmployee;
    private String nameEmployee;
    private String nameRole;

    public AccountDTO(Account account){
        this.nameAccount = account.getNameAccount();
        this.idEmployee = account.getEmployee().getId();
        this.nameEmployee = account.getEmployee().getName();
        this.nameRole = account.getAuthorities().toString();
    }
}
