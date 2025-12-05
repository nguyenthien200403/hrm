package com.example.hrm.dto;

import com.example.hrm.model.Account;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountDTO {
    private String nameAccount;
    private LocalDate dateCreate;
    private String nameRole;

    public AccountDTO(Account account){
        this.nameAccount = account.getNameAccount();
        this.dateCreate = account.getDateCreate();
        this.nameRole = account.getRole().getName();
    }
}
