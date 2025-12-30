package com.example.hrm.mapper;

import com.example.hrm.dto.RequirementDTO;
import com.example.hrm.model.Requirement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;


@Mapper(componentModel = "spring")
public interface RequirementMapper {
    @Mapping(target = "nameType", source = "typeRequirement.name")
    RequirementDTO toDTO(Requirement requirement);

    List<RequirementDTO> toDTOs(List<Requirement> requirements);

//    public static TypeRequirementDTO toDTO(TypeRequirement entity) {
//        TypeRequirementDTO dto = new TypeRequirementDTO();
//        dto.setName(entity.getName());
//        dto.setRequirements(entity.getRequirements()
//                .stream()
//                .map(RequirementMapper :: mapRequirement)
//                .collect(Collectors.toList()));
//        return dto; }
//
//    private static RequirementDTO mapRequirement(Requirement requirement) {
//        RequirementDTO dto = new RequirementDTO();
//        dto.setName(requirement.getName());
//        dto.setDescribe(requirement.getDescribe());
//        String type = requirement.getTypeRequirement().getName();
//        if ("Nghỉ phép có lương".equalsIgnoreCase(type)) {
//            dto.setTimeFrame(requirement.getTimeFrame());
//        }
//        if ("Tăng ca".equalsIgnoreCase(type)) {
//            dto.setOvertimeRate(requirement.getOvertimeRate());
//        }
//        return dto;
//    }
}
