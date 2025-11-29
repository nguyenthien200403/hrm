package com.example.hrm.dto;

import com.example.hrm.model.Department;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DepartmentDTO {
    private String  id;
    private String name;
    private LocalDate date;
    private String describe;
    private int amountEmployee;

    public DepartmentDTO(Department department){
        this.id = department.getId();
        this.name = department.getName();
        this.date = department.getDate();
        this.describe = department.getDescribe();
        this.amountEmployee = department.getEmployee() != null ? department.getEmployee().size() : 0;
    }
}
