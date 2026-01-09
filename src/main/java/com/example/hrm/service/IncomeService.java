package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.IncomeDTO;
import com.example.hrm.mapper.IncomeMapper;
import com.example.hrm.model.Employee;
import com.example.hrm.model.Income;
import com.example.hrm.projection.IncomeProjection;
import com.example.hrm.repository.EmployeeRepository;
import com.example.hrm.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository repository;
    private final EmployeeRepository employeeRepository;
    private final IncomeMapper incomeMapper;

    private final JdbcTemplate jdbcTemplate;

    public void calculateIncomeForAllEmployees(int month, int year){
        jdbcTemplate.update("EXEC sp_CalculateIncomeAllEmployeesByMonth @thang=?, @nam=?", month, year);
    }

    @Transactional(readOnly = true)
    public GeneralResponse<?> getAllIncomeByEmployee(String employeeId){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Not Found Employee with Id: " + employeeId));

        List<IncomeProjection> list = repository.findAllByEmployee(employeeId);

        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"Empty" , null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(),"Income" , list);

    }

    @Transactional(readOnly = true)
    public GeneralResponse<?> getAllIncomeByDepartmentName(String departName){
        List<Income> list = repository.findAllByDepartmentName(departName);

        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }
        List<IncomeDTO> dto = list.stream().map(incomeMapper :: toDTO).collect(Collectors.toList());

        return new GeneralResponse<>(HttpStatus.OK.value(),"List", dto);
    }
}
