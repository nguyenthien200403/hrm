package com.example.hrm.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.List;


@Data
@NoArgsConstructor
public class EmployeeDTO {
    private String id;
    private String name;
    private Boolean gender;
    private LocalDate birthDate;

    private boolean hasAccount;

    @Email(message = "Email is not in the correct format")
    @NotBlank(message = "Not Null")
    private String email;

    @NotBlank(message = "NOT NULL")
    private String nation;

    private String ethnic;

    @NotBlank(message = "NOT NULL")
    @Pattern(regexp = "\\d{10}", message = "LENGTH 10")
    private String phone;

    private String habit;

    @NotBlank(message = "NOT NULL")
    private String statusMarital;

    private String position;

    private BigDecimal wage;

    private String nameDepart;

    @Valid
    private IdentificationDTO identification;

    @Valid
    private List<AddressDTO> addresses;

    @Valid
    private List<RelativeDTO> relatives;

    @Valid
    private BankDTO bank;
}
