package com.example.hrm.mapper;

import com.example.hrm.dto.IncomeDTO;
import com.example.hrm.model.Income;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IncomeMapper {
    @Mapping(target = "employeeName", source = "employee.name")
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "departmentName", source = "employee.department.name")
    IncomeDTO toDTO(Income income);

    List<Income> toDTOs(List<Income> incomes);
}
