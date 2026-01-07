package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.docuseal.DocusealApiService;
import com.example.hrm.dto.ContractDTO;
import com.example.hrm.model.Contract;
import com.example.hrm.docuseal.DocuSeal;
import com.example.hrm.model.Employee;
import com.example.hrm.model.TypeContract;
import com.example.hrm.projection.ContractProjection;
import com.example.hrm.repository.ContractRepository;
import com.example.hrm.repository.EmployeeRepository;
import com.example.hrm.repository.TypeContractRepository;
import com.example.hrm.request.ContractRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


import java.math.BigDecimal;
import java.math.RoundingMode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {

    public final ContractRepository contractRepository;
    public final EmployeeRepository employeeRepository;
    public final TypeContractRepository typeContractRepository;
    private final DocusealApiService docusealApiService;

    @Value("${time.work}")
    private long TimeWork;

    @Value("${date.work}")
    private long DateWork;



    public String generalId(String idEmployee){
        String last4Digits = idEmployee.substring(idEmployee.length() - 4);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));

        return last4Digits + timestamp;
    }

    public GeneralResponse<?> create(String idAdmin, ContractRequest request) {
        Optional<Employee> findEmp = employeeRepository.findById(request.getIdEmployee());
        Optional<TypeContract> findT = typeContractRepository.findByName(request.getNameTypeContract());

        if (findEmp.isEmpty() || findT.isEmpty()) {
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),
                    "Not Found: {Employee or Contract Type}", null);
        }

        Employee employee = findEmp.get();
        TypeContract typeContract = findT.get();

        String generalId = generalId(employee.getId());

        Contract contract = Contract.builder()
                .dateBegin(request.getDateBegin())
                .dateEnd(request.getDateEnd())
                .position(request.getPosition())
                .salary(request.getSalary())
                .term(request.getTerm())
                .note(request.getNote())
                .employee(employee)
                .typeContract(typeContract)
                .build();

        DocuSeal docuSeal = docusealApiService.createSubmission(idAdmin, contract, generalId);

        if (docuSeal!= null) {
            contract.setId(docuSeal.getId());
            contract.setEmbedSrc(docuSeal.getEmbedSrc());
            contract.setStatus(docuSeal.getStatus());

            contractRepository.save(contract);
        } else {
            return new GeneralResponse<>(HttpStatus.BAD_GATEWAY.value(),
                    "Docuseal API error", null);
        }

        ContractDTO dto = new ContractDTO(contract);
        return new GeneralResponse<>(HttpStatus.CREATED.value(), "Success", dto);
    }



    @Transactional(readOnly = true)
    public GeneralResponse<?> getAllContractsByEmployeeId(String id){
        List<ContractProjection> list = contractRepository.findAllContractsByEmployeeId(id);
        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(), "List", list);
    }

    @Transactional(readOnly = true)
    public GeneralResponse<?> getAllContractsByDateSignAndType(Boolean signed, String type){
        List<ContractProjection> list = contractRepository.findAllContractsByDateSignAndType(signed, type);
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

    public void confirm(String id, LocalDateTime dateSign){
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found Contract with Id:" + id));



        TypeContract typeContract = contract.getTypeContract();

        if(Boolean.TRUE.equals(typeContract.getHasSalary()) && contract.getSalary() != null){
            Employee employee = contract.getEmployee();
            employee.setWage(calculateWage(contract.getSalary()));
            employeeRepository.save(employee);
        }
        contract.setDateSign(dateSign);
        contract.setStatus("completed");

        contractRepository.save(contract);
    }

    public GeneralResponse<?> confirmContract(String id, LocalDateTime dateSign){
        confirm(id, dateSign);
        return new GeneralResponse<>(HttpStatus.OK.value(), "Contract Confirmed Successfully", null);
    }

    private BigDecimal calculateWage(BigDecimal salary){
        BigDecimal hoursPerMonth = BigDecimal.valueOf(DateWork * TimeWork);
        return salary.divide(hoursPerMonth, RoundingMode.HALF_UP);
    }

}
