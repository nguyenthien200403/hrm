package com.example.hrm.dto;

import com.example.hrm.model.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AddressDTO {
    @NotBlank(message = "Not Null")
    private String addressType;

    @NotBlank(message = "Not Null")
    private String street;

    @NotBlank(message = "Not Null")
    private String ward;

    @NotBlank(message = "Not Null")
    private String district;

    @NotBlank(message = "Not Null")
    private String province;

//    public AddressDTO(Address address){
//        this.addressType = address.getAddressType();
//        this.street = address.getStreet();
//        this.ward = address.getWard();
//        this.district = address.getDistrict();
//        this.province = address.getProvince();
//    }
}
