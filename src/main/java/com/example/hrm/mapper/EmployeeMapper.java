package com.example.hrm.mapper;

import com.example.hrm.dto.*;
import com.example.hrm.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "nameDepart", source = "department.name")
    @Mapping(target = "position", expression = "java(getPosition(employee))")
    @Mapping(target = "hasAccount", expression = "java(employee.getAccount() != null)")
    EmployeeDTO toDto(Employee employee);

    //Employee toEntity(EmployeeDTO dto);

    default String getPosition(Employee employee){
        return employee.getContracts().stream()
                .sorted()
                .findFirst()
                .map(Contract :: getPosition)
                .orElse(null);
    }

//    BankDTO toDto(Bank bankAccount);
//    Bank toEntity(BankDTO dto);
//
//    AddressDTO toDto(Address address);
//    Address toEntity(AddressDTO dto);
//
//    IdentificationDTO toDto(Identification identification);
//    Identification toEntity(IdentificationDTO dto);
//
//    RelativeDTO toDto(Relatives relative);
//    Relatives toEntity(RelativeDTO dto);


    // Method update: chỉ map các field đơn giản, không đụng tới quan hệ phức tạp
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "birthDate", ignore = true)
    @Mapping(target = "ethnic", ignore = true)
    @Mapping(target = "wage", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "relatives", ignore = true)
    @Mapping(target = "bank", ignore = true)
    @Mapping(target = "identification", ignore = true)
    void updateEntityFromDto(EmployeeDTO dto, @MappingTarget Employee entity);

    void updateAddressFromDto(AddressDTO dto, @MappingTarget Address entity);

    void updateRelativeFromDto(RelativeDTO dto, @MappingTarget Relatives entity);

    void updateBankFromDto(BankDTO dto, @MappingTarget Bank entity);

    void updateIdentificationFromDto(IdentificationDTO dto, @MappingTarget Identification entity);

}
