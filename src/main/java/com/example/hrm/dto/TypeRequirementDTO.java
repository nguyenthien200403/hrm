package com.example.hrm.dto;

import lombok.Data;

import java.util.List;

@Data
public class TypeRequirementDTO {
    private String name;
    private List<RequirementDTO> requirements;
}
