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

//    public EmployeeDTO(Employee employee) {
//        this.id = employee.getId();
//        this.name = employee.getName();
//        this.email = employee.getEmail();
//        this.birthDate = employee.getBirthDate();
//        this.phone = employee.getPhone();
//        this.gender = employee.getGender();
//        this.nation = employee.getNation();
//        this.ethnic = employee.getEthnic();
//        this.habit = employee.getHabit() != null ? employee.getHabit() : "";
//        this.statusMarital = employee.getStatusMarital();
//        this.wage = employee.getWage() != null ? employee.getWage() : BigDecimal.valueOf(0);
//        this.nameDepart = employee.getDepartment() != null ? employee.getDepartment().getName() : "";
//        this.identification = employee.getIdentification() != null ? new IdentificationDTO(employee.getIdentification()) : null;
//        this.addresses = employee.getAddresses() != null
//                ? employee.getAddresses().stream().map(AddressDTO:: new).collect(Collectors.toList())
//                : new ArrayList<>();
//        this.relatives = employee.getRelatives() != null
//                ? employee.getRelatives().stream().map(RelativeDTO::new).collect(Collectors.toList())
//                : new ArrayList<>();
//        this.bank = employee.getBank() != null ? new BankDTO(employee.getBank()) : null;
//    }
}
