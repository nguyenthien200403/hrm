package com.example.hrm.dto;

import com.example.hrm.model.Recruitment;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecruitmentDTO {
    @NotBlank(message = "NOT NULL")
    @Email(message = "Email is not in the correct format")
    private String email;

    @NotBlank(message = "NOT NULL")
    private String name;

    public RecruitmentDTO(Recruitment recruitment){
        this.email = recruitment.getEmail();
        this.name = recruitment.getName();
    }
}
