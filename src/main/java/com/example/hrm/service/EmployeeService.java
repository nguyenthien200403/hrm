package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.EmployeeDTO;
import com.example.hrm.model.Employee;
import com.example.hrm.projection.EmployeeProjection;
import com.example.hrm.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private RecruitmentService recruitmentService;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    public GeneralResponse<?> getEmployeeByID(String id){
        Optional <Employee> findResult = employeeRepository.findById(id);
        if(findResult.isPresent()){
            Employee employee = findResult.get();
            return new GeneralResponse<>(HttpStatus.OK.value(), "Detail Employee", employee);
        }
        return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "Not Found Id", null);
    }

    public GeneralResponse<?> getAllByStatus(String status){
        List<EmployeeProjection> employees = employeeRepository.findAllByStatus(status);
        if(employees.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NO_CONTENT.value(), "No data", null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(),"List employee-new", employees);

    }

    public String generalId(String identification){
        String last4Digits = identification.substring(identification.length() - 4);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
        
        return last4Digits + timestamp;
    }
    public GeneralResponse<?> checkDataInput(EmployeeDTO dto, String id){
        int status = HttpStatus.CONFLICT.value();
        if(employeeRepository.existById(id)){
            return new GeneralResponse<>(status, "Existed Id", null);
        }
        if(employeeRepository.existsByEmail(dto.getEmail())){
            return new GeneralResponse<>(status, "Existed Email", null);
        }
        if (employeeRepository.existsByPhone(dto.getPhone())){
            return new GeneralResponse<>(status, "Existed Phone", null);
        }
        if (employeeRepository.existsByIdentification(dto.getIdentification())){
            return new GeneralResponse<>(status, "Existed Identification", null);
        }
        return null;
    }
    
    public GeneralResponse<?> create(EmployeeDTO dto, String email){
        try{
            String id = generalId(dto.getIdentification());
            GeneralResponse<?> check = checkDataInput(dto, id);
            if(check != null){
                return check;
            }

            Employee employee = convertToEmployee(dto, id);
            employeeRepository.save(employee);

            recruitmentService.update(email);

            return new GeneralResponse<>(HttpStatus.OK.value(),"Success", employee);
        }catch (DataIntegrityViolationException e){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(),"Existed data",null);
        }catch (Exception e) {
            logger.error("Error when create employee: ", e);
            return new GeneralResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error occurred",null);
        }

    }

    private static Employee convertToEmployee(EmployeeDTO dto, String id) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName(dto.getName());
        employee.setGender(dto.getGender());
        employee.setEmail(dto.getEmail());
        employee.setBirthDate(dto.getBirthDate());
        employee.setPhone(dto.getPhone());
        employee.setNation(dto.getNation());
        employee.setEthnic(dto.getEthnic());
        employee.setIdentification(dto.getIdentification());
        employee.setIssueDate(dto.getIssueDate());
        employee.setIssuePlace(dto.getIssuePlace());
        employee.setTempAddress(dto.getTempAddress());
        employee.setPermanent(dto.getPermanent());
        employee.setHabit(dto.getHabit());
        employee.setStatusMarital(dto.getStatusMarital());
        return employee;
    }
}
