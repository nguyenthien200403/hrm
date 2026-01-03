package com.example.hrm.mapper;

import com.example.hrm.dto.DetailRequirementDTO;


import com.example.hrm.model.DetailRequirement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetailRequirementMapper {
    @Mapping(target = "employeeName", source = "employee.name")
    @Mapping(target = "departmentName", source = "employee.department.name")
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "requirementName", source = "requirement.name")
    DetailRequirementDTO toDTO(DetailRequirement detailRequirement);

    List<DetailRequirement> toDTOs(List<DetailRequirement> detailRequirements);
}
