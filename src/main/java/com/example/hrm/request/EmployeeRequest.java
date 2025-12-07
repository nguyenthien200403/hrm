package com.example.hrm.request;

import com.example.hrm.dto.AddressDTO;
import com.example.hrm.dto.BankDTO;
import com.example.hrm.dto.IdentificationDTO;
import com.example.hrm.dto.RelativeDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EmployeeRequest {

    @Email(message = "Email is not in the correct format")
    @NotBlank(message = "Not Null")
    private String emailRecruit;

    @NotBlank(message = "NOT NULL")
    private String name;

    @NotNull(message = "NOT NULL")
    private Boolean gender;

    @Past(message = "Birth date must be in the past")
    @NotNull(message = "NOT NULL")
    private LocalDate birthDate;

    @NotBlank(message = "NOT NULL")
    @Email(message = "Email is not in the correct format")
    private String email;

    @NotBlank(message = "NOT NULL")
    private String nation;

    @NotBlank(message = "NOT NULL")
    private String ethnic;

    @NotBlank(message = "NOT NULL")
    @Pattern(regexp = "\\d{10}", message = "LENGTH 10")
    private String phone;

    private String habit;

    @NotBlank(message = "NOT NULL")
    private String statusMarital;

    @Valid
    private IdentificationDTO identification;

    @Valid
    private List<AddressDTO> address;

    @Valid
    private List<RelativeDTO> relatives;

    @Valid
    private BankDTO bank;

}
