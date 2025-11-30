package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.BankDTO;
import com.example.hrm.dto.EmployeeDTO;
import com.example.hrm.dto.RelativeDTO;
import com.example.hrm.model.Bank;
import com.example.hrm.model.Department;
import com.example.hrm.model.Employee;
import com.example.hrm.model.Relatives;
import com.example.hrm.projection.EmployeeProjection;
import com.example.hrm.repository.DepartmentRepository;
import com.example.hrm.repository.EmployeeRepository;
import com.example.hrm.repository.RecruitmentRepository;
import com.example.hrm.request.EmployeeRequest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private final RecruitmentRepository recruitmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;


    public GeneralResponse<?> getEmployeeByID(String id){
        Optional<Employee> findResult = employeeRepository.findById(id);
        if(findResult.isPresent()){
            EmployeeDTO employeeDTO = new EmployeeDTO(findResult.get());
            return new GeneralResponse<>(HttpStatus.OK.value(), "Detail Employee", employeeDTO);
        }
        return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "Not Found Id", null);
    }

    public GeneralResponse<?> getAllByStatus(String status, String message){
        List<EmployeeProjection> employees = employeeRepository.findAllByStatus(status);
        if(employees.isEmpty()){
            return new GeneralResponse<>(HttpStatus.OK.value(), "No data", null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(), message, employees);

    }

    public String generalId(String identification){
        String last4Digits = identification.substring(identification.length() - 4);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
        
        return last4Digits + timestamp;
    }

    public GeneralResponse<?> checkDataInput(EmployeeRequest request, String id){
        int status = HttpStatus.CONFLICT.value();

        if(employeeRepository.existById(id)){
            return new GeneralResponse<>(status, "Existed Id", null);
        }
        if(employeeRepository.existsByEmail(request.getEmail())){
            return new GeneralResponse<>(status, "Existed Email", null);
        }
        if (employeeRepository.existsByPhone(request.getPhone())){
            return new GeneralResponse<>(status, "Existed Phone", null);
        }
        if (employeeRepository.existsByIdentification(request.getIdentification())){
            return new GeneralResponse<>(status, "Existed Identification", null);
        }
        return null;
    }
    
    public GeneralResponse<?> create(EmployeeRequest request){
        try{
            String id = generalId(request.getIdentification());
            GeneralResponse<?> check = checkDataInput(request, id);

            if(check != null){
                return check;
            }

            update(request.getEmailRecruit());

            Employee employee = convertToEmployee(request, id);
            employeeRepository.save(employee);
            EmployeeDTO dto = new EmployeeDTO(employee);


            return new GeneralResponse<>(HttpStatus.CREATED.value(),"Success", dto);
        }catch (DataIntegrityViolationException e){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(),"Existed data", e.getMessage());
        }catch (Exception e) {
            logger.error("Error when create employee: ", e);
            return new GeneralResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error occurred",e.getMessage());
        }
    }

    @Transactional
    private
    void update(String email) {
        int updated = recruitmentRepository.updateByEmail(email, false);
        if (updated > 0) {
            logger.info("Recruitment updated successfully for email {}", email);
        } else {
            logger.warn("No recruitment found with email {}", email);
        }
    }



    private static Employee convertToEmployee(EmployeeRequest request, String id) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName(request.getName());
        employee.setGender(request.getGender());
        employee.setEmail(request.getEmail());
        employee.setBirthDate(request.getBirthDate());
        employee.setPhone(request.getPhone());
        employee.setNation(request.getNation());
        employee.setEthnic(request.getEthnic());
        employee.setIdentification(request.getIdentification());
        employee.setIssueDate(request.getIssueDate());
        employee.setIssuePlace(request.getIssuePlace());
        employee.setTempAddress(request.getTempAddress());
        employee.setPermanent(request.getPermanent());
        employee.setHabit(request.getHabit());
        employee.setStatusMarital(request.getStatusMarital());
        employee.setRelatives(mapRelatives(request.getRelatives(), employee));
        employee.setBank(mapBank(request.getBank(), employee));
        return employee;
    }

    private static List<Relatives> mapRelatives(List<RelativeDTO> relativeDTOS, Employee employee) {
        return relativeDTOS.stream()
                .map(r -> {
                    Relatives relative = new Relatives();
                    relative.setName(r.getName());
                    relative.setRelation(r.getRelation());
                    relative.setDateOfBirth(r.getDateOfBirth());
                    relative.setGender(r.getGender());
                    relative.setPhone(r.getPhone());
                    relative.setEmployee(employee);
                    return relative;
                })
                .collect(Collectors.toList());
    }

    private static Bank mapBank(BankDTO dto, Employee employee){
        Bank bank = new Bank();
        bank.setNameBank(dto.getNameBank());
        bank.setAgent(dto.getAgent());
        bank.setNameAccountBank(dto.getNameAccountBank());
        bank.setNumberAccountBank(dto.getNameAccountBank());
        bank.setProvince(dto.getProvince());
        bank.setNumberRout(dto.getNumberRout());
        bank.setEmployee(employee);
        return bank;
    }


    public GeneralResponse<?> verifyEmployee(String id, String nameDepart){

        Optional<Employee> findResult = employeeRepository.findById(id);
        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"Not Found Employee Id", null);
        }

        Department department = departmentRepository.findByName(nameDepart);
        if(department == null){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"Not Found Department Name", null);
        }

        Employee employee = findResult.get();
        employee.setStatus("1");
        employee.setDepartment(department);

        employeeRepository.save(employee);

        return new GeneralResponse<>(HttpStatus.OK.value(), "Success", null);
    }

    public GeneralResponse<?> amountEmployeeActive(String status, String message){
        long amount = employeeRepository.countEmpByStatus(status);
        return new GeneralResponse<>(HttpStatus.OK.value(), message, amount);
    }

}
