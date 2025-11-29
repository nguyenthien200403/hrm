package com.example.hrm.dto;

import com.example.hrm.model.Contract;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ContractDTO {
    private String idContract;
    private String nameTypeContract;
    private String  idEmployee;
    private String nameEmp;
    private String phone;
    private String identification;
    private LocalDate dateBegin;
    private LocalDate dateEnd;
    private int term;
    private String position;
    private BigDecimal salary;
    private LocalDate dateSign;
    private String note;


    public ContractDTO(Contract contract){
        this.idContract = contract.getId();
        this.nameTypeContract = contract.getTypeContract().getName();
        this.idEmployee = contract.getEmployee().getId();
        this.nameEmp = contract.getEmployee().getName();
        this.phone = contract.getEmployee().getPhone();
        this.identification = contract.getEmployee().getIdentification();
        this.dateBegin = contract.getDateBegin();
        this.dateEnd = contract.getDateEnd();
        this.position = contract.getPosition();
        this.term = contract.getTerm();
        this.salary = contract.getSalary();
        this.dateSign = contract.getDateSign();
        this.note = contract.getNote();
    }


}
