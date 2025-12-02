package com.example.hrm.dto;

import com.example.hrm.model.Employee;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class EmployeeDTO {
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
    private BigDecimal wage;
    private String nameDepart;

    private IdentificationDTO identification;
    private List<AddressDTO> address;
    private List<RelativeDTO> relatives;
    private BankDTO bank;

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.birthDate = employee.getBirthDate();
        this.phone = employee.getPhone();
        this.gender = employee.getGender();
        this.nation = employee.getNation();
        this.ethnic = employee.getEthnic();
        this.habit = employee.getHabit() != null ? employee.getHabit() : "";
        this.statusMarital = employee.getStatusMarital();
        this.wage = employee.getWage() != null ? employee.getWage() : BigDecimal.valueOf(0);
        this.nameDepart = employee.getDepartment() != null ? employee.getDepartment().getName() : "";
        this.identification = employee.getIdentification() != null ? new IdentificationDTO(employee.getIdentification()) : null;
        this.address = employee.getAddresses() != null
                ? employee.getAddresses().stream().map(AddressDTO:: new).collect(Collectors.toList())
                : new ArrayList<>();
        this.relatives = employee.getRelatives() != null
                ? employee.getRelatives().stream().map(RelativeDTO::new).collect(Collectors.toList())
                : new ArrayList<>();
        this.bank = employee.getBank() != null ? new BankDTO(employee.getBank()) : null;
    }
}
