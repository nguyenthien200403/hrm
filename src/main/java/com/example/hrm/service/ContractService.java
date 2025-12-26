package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.ContractDTO;
import com.example.hrm.model.Contract;
import com.example.hrm.model.Employee;
import com.example.hrm.model.TypeContract;
import com.example.hrm.projection.ContractProjection;
import com.example.hrm.repository.ContractRepository;
import com.example.hrm.repository.EmployeeRepository;
import com.example.hrm.repository.TypeContractRepository;
import com.example.hrm.request.ContractRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.math.RoundingMode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found: {Employee or Contract Type}", null);
        }

        Employee employee = findEmp.get();
        TypeContract typeContract = findT.get();

        String id = generalId(employee.getId());

        var contract = Contract.builder()
                .id(id)
                .dateBegin(request.getDateBegin())
                .dateEnd(request.getDateEnd())
                .position(request.getPosition())
                .salary(request.getSalary())
                .term(request.getTerm())
                .note(request.getNote())
                .employee(employee)
                .typeContract(typeContract)
                .build();
        

        contractRepository.save(contract);

        ContractDTO dto = new ContractDTO(contract);

        return new GeneralResponse<>(HttpStatus.CREATED.value(), "Success", dto);
    }

    @Transactional(readOnly = true)
    public GeneralResponse<?> getAllContractsByEmployeeId(String id){
        List<ContractProjection> list = contractRepository.findAllByEmployeeId(id);
        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(), "List", list);
    }

    @Transactional(readOnly = true)
    public GeneralResponse<?> getAllByDateSignAndType(boolean signed, String type){
        List<ContractProjection> list = contractRepository.findAllByDateSignAndType(signed, type);
        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(), "List", list);
    }




    public GeneralResponse<?> getDetailsByIdContract(String id){
        Optional<Contract> findResult = contractRepository.findById(id);
        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Contract with Id: " + id, null);
        }
        ContractDTO dto = new ContractDTO(findResult.get());
        return new GeneralResponse<>(HttpStatus.OK.value(), "Contracts Details with Id: " + id, dto);
    }


    public GeneralResponse<?> update(ContractRequest request, String id){
        Optional<Contract> findResult = contractRepository.findById(id);
        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Contract with Id: " + id, null);
        }
        Optional<TypeContract> findT = typeContractRepository.findByName(request.getNameTypeContract());

        if(findT.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Type Contract", null);

        }

        Contract contract = findResult.get();
        contract.setDateBegin(request.getDateBegin());
        contract.setDateEnd(request.getDateEnd());
        contract.setPosition(request.getPosition());
        contract.setTerm(request.getTerm());
        contract.setSalary(request.getSalary());
        contract.setTypeContract(findT.get());

        contractRepository.save(contract);
        return new GeneralResponse<>(HttpStatus.OK.value(), "SuccessFul Update", null);
    }

    public GeneralResponse<?> searchContractsBy(String keyword, String type){
        List<ContractProjection> list = contractRepository.searchContractsBy(keyword, type);
        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Contract", null);
        }

        return new GeneralResponse<>(HttpStatus.OK.value(), "Successful Search", list);
    }

    public GeneralResponse<?> confirmContract(String id){
        Optional<Contract> findResult = contractRepository.findById(id);
        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Contract", null);
        }

        Contract contract = findResult.get();
        TypeContract typeContract = contract.getTypeContract();

        if(Boolean.TRUE.equals(typeContract.getHasSalary()) && contract.getSalary() != null){
            Employee employee = contract.getEmployee();
            employee.setWage(calculateWage(contract.getSalary()));
            employeeRepository.save(employee);
        }
        contract.setDateSign(LocalDateTime.now());
        contractRepository.save(contract);

        return new GeneralResponse<>(HttpStatus.OK.value(), "Contract Confirmed Successfully", null);

    }

    private BigDecimal calculateWage(BigDecimal salary){
        BigDecimal hoursPerMonth = BigDecimal.valueOf(22 * 8);
        return salary.divide(hoursPerMonth, RoundingMode.HALF_UP);
    }

}
