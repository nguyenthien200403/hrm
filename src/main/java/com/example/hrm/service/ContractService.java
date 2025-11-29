package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.ContractDTO;
import com.example.hrm.model.Contract;
import com.example.hrm.model.Employee;
import com.example.hrm.model.TypeContract;
import com.example.hrm.repository.ContractRepository;
import com.example.hrm.repository.EmployeeRepository;
import com.example.hrm.repository.TypeContractRepository;
import com.example.hrm.request.ContractRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {

    public final ContractRepository contractRepository;
    public final EmployeeRepository employeeRepository;
    public final TypeContractRepository typeContractRepository;


    public String generalId(String idEmployee){
        String last4Digits = idEmployee.substring(idEmployee.length() - 4);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));

        return last4Digits + timestamp;
    }

    public GeneralResponse<?> create(ContractRequest request){
        Optional<Employee> findEmp = employeeRepository.findById(request.getIdEmployee());
        Optional<TypeContract> findT = typeContractRepository.findByName(request.getNameTypeContract());

        if(findEmp.isEmpty() || findT.isEmpty()){
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "Not Found: {Employee or Contract Type}", null);
        }

        Employee employee = findEmp.get();
        TypeContract typeContract = findT.get();

        String id = generalId(employee.getId());

        Contract contract = new Contract();
        contract.setId(id);
        contract.setDateBegin(request.getDateBegin());
        contract.setDateEnd(request.getDateEnd());
        contract.setPosition(request.getPosition());
        contract.setSalary(request.getSalary());
        contract.setTerm(request.getTerm());
        contract.setNote(request.getNote());
        contract.setEmployee(employee);
        contract.setTypeContract(typeContract);

        contractRepository.save(contract);

        ContractDTO dto = new ContractDTO(contract);

        return new GeneralResponse<>(HttpStatus.CREATED.value(), "Success", dto);
    }

}
