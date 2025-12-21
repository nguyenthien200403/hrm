package com.example.hrm.dto;

import com.example.hrm.model.Relatives;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RelativeDTO {
    @NotBlank(message = "NOT NULL")
    private String name;

    @NotBlank(message = "NOT NULL")
    private String relation;

    @NotBlank(message = "NOT NULL")
    @Pattern(regexp = "\\d{10}", message = "LENGTH 10")
    private String phone;

    @Past(message = "Birth date must be in the past")
    @NotNull(message = "NOT NULL")
    private LocalDate dateOfBirth;

    @NotNull(message = "NOT NULL")
    private Boolean gender;

//    public RelativeDTO(Relatives relatives){
//        this.name = relatives.getName();
//        this.dateOfBirth = relatives.getDateOfBirth();
//        this.gender = relatives.getGender();
//        this.relation = relatives.getRelation();
//        this.phone = relatives.getPhone();
//    }
}
