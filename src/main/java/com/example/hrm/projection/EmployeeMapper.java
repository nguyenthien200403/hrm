package com.example.hrm.projection;

import com.example.hrm.dto.*;
import com.example.hrm.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "nameDepart", source = "department.name")
    EmployeeDTO toDto(Employee employee);
    Employee toEntity(EmployeeDTO dto);

    BankDTO toDto(Bank bankAccount);
    Bank toEntity(BankDTO dto);

    AddressDTO toDto(Address address);
    Address toEntity(AddressDTO dto);

    IdentificationDTO toDto(Identification identification);
    Identification toEntity(IdentificationDTO dto);

    RelativeDTO toDto(Relatives relative);
    Relatives toEntity(RelativeDTO dto);
}
