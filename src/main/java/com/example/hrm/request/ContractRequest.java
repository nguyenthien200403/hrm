package com.example.hrm.request;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ContractRequest {

    @NotNull(message = "Not Null")
    private LocalDate dateBegin;

    private LocalDate dateEnd;

    @NotBlank(message = "Not Null")
    private String position;

    private BigDecimal salary;

    private int term;

    private String note;

    private String  idEmployee;

    @NotBlank(message = "Not Null")
    private String nameTypeContract;

}
