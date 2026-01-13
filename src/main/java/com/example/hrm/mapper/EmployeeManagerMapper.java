package com.example.hrm.mapper;

import com.example.hrm.dto.EmployeeManagerDTO;
import com.example.hrm.model.Contract;
import com.example.hrm.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeManagerMapper {
    @Mapping(target = "position", expression = "java(getPosition(employee))")
    EmployeeManagerDTO toDto(Employee employee);

    default String getPosition(Employee employee){
        return employee.getContracts().stream()
                .sorted()
                .findFirst()
                .map(Contract:: getPosition)
                .orElse(null);
    }
}
