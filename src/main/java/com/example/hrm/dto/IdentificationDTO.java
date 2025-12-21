package com.example.hrm.dto;


import com.example.hrm.model.Identification;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class IdentificationDTO {
    @NotBlank(message = "Not Null")
    @Pattern(regexp = "\\d{12}", message = "LENGTH 12")
    private String id;

    @NotNull(message = "Not Null")
    private LocalDate date;

    @NotBlank(message = "Not Null")
    private String place;

//    public IdentificationDTO(Identification identification){
//        this.id = identification.getId();
//        this.date = identification.getDate();
//        this.place = identification.getPlace();
//    }
}
