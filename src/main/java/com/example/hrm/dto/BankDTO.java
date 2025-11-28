package com.example.hrm.dto;

import com.example.hrm.model.Bank;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankDTO {
    @NotBlank(message = "Not Null")
    private String nameBank;

    @NotBlank(message = "Not Null")
    private String agent;

    @NotBlank(message = "Not Null")
    private String numberAccountBank;

    @NotBlank(message = "Not Null")
    private String numberRout;

    @NotBlank(message = "Not Null")
    private String nameAccountBank;

    @NotBlank(message = "Not Null")
    private String province;

    public BankDTO(Bank bank){
        this.nameBank = bank.getNameBank();
        this.agent = bank.getAgent();
        this.nameAccountBank = bank.getNameAccountBank();
        this.numberAccountBank = bank.getNumberAccountBank();
        this.numberRout = bank.getNumberRout();
        this.province = bank.getProvince();
    }

}
