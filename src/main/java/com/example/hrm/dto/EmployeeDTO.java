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
    private String identification;
    private String issuePlace;
    private LocalDate issueDate;
    private String tempAddress;
    private String permanent ;
    private String habit;
    private String statusMarital;
    private BigDecimal wage;
    private String idDepart;
    private List<RelativeDTO> relatives;
    private BankDTO bank;

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.birthDate = employee.getBirthDate();
        this.phone = employee.getPhone();
        this.gender = employee.getGender();
        this.identification = employee.getIdentification();
        this.issueDate = employee.getIssueDate();
        this.issuePlace = employee.getIssuePlace();
        this.nation = employee.getNation();
        this.ethnic = employee.getEthnic();
        this.habit = employee.getHabit();
        this.permanent = employee.getPermanent();
        this.tempAddress = employee.getTempAddress();
        this.statusMarital = employee.getStatusMarital();
        this.wage = employee.getWage();
        this.idDepart = employee.getIdDepart();
        this.relatives = employee.getRelatives() != null
                ? employee.getRelatives().stream().map(RelativeDTO::new).collect(Collectors.toList())
                : new ArrayList<>();
        this.bank = employee.getBank() != null ? new BankDTO(employee.getBank()) : null;
    }
}
