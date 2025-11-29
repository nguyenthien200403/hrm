package com.example.hrm.request;


import com.example.hrm.model.Contract;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotBlank(message = "Not Null")
    private String  idEmployee;

    @NotBlank(message = "Not Null")
    private String nameTypeContract;

}
