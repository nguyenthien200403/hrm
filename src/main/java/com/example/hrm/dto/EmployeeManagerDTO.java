package com.example.hrm.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeManagerDTO {
    private String id;
    private String name;
    private Boolean gender;
    private LocalDate birthDate;
    private String email;
    private String nation;
    private String ethnic;
    private String phone;
    private String habit;
    private String statusMarital;
    private String position;
    private IdentificationDTO identification;
    private List<AddressDTO> addresses;
    private List<RelativeDTO> relatives;
    private BankDTO bank;
}
