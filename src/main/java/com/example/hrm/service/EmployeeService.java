package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.*;
import com.example.hrm.model.*;
import com.example.hrm.projection.EmployeeMapper;
import com.example.hrm.projection.EmployeeProjection;
import com.example.hrm.repository.*;
import com.example.hrm.request.EmployeeRequest;

import com.example.hrm.sendEmail.EmailDetails;
import com.example.hrm.sendEmail.EmailService;
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
    private final IdentificationRepository identificationRepository;
    private final ContractRepository contractRepository;
    private final EmployeeMapper employeeMapper;
    private final EmailService emailService;


    public GeneralResponse<?> getEmployeeById(String id){
        Optional<Employee> findResult = employeeRepository.findById(id);
        if(findResult.isPresent()){
            EmployeeDTO dto = employeeMapper.toDto(findResult.get());
            return new GeneralResponse<>(HttpStatus.OK.value(), "Detail Employee with Id: " + id, dto);
        }
        return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Employee with Id: " + id, null);
    }



    public GeneralResponse<?> getAllByStatusAndDepartment(String status, String name){
        List<EmployeeProjection> list;

        if ("1".equals(status)) {
            // Trạng thái làm việc → cần lọc theo phòng ban
            if (name == null || name.isBlank()) {
                return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "Name Department Not Null", null);
            }
            list = employeeRepository.findAllByStatusAndDepartment(status, name);
        } else {
            // Trạng thái khác → không cần lọc phòng ban
            list = employeeRepository.findAllByStatus(status);
        }

        if (list.isEmpty()) {
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(), "List Employee", list);
    }

    public GeneralResponse<?> searchEmployeesBy(String keyword, String name, String status){
        List<EmployeeProjection> list = employeeRepository.searchEmployeesBy(keyword, name, status);

        if (list.isEmpty()) {
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(), "List Employee", list);
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
        Optional<Identification> findResult = identificationRepository.findById(request.getIdentification().getId());
        if (findResult.isPresent()){
            return new GeneralResponse<>(status, "Existed Identification", null);
        }
        return null;
    }
    
    public GeneralResponse<?> create(EmployeeRequest request){
        try{
            String id = generalId(request.getIdentification().getId());
            GeneralResponse<?> check = checkDataInput(request, id);

            if(check != null){
                return check;
            }

            update(request.getEmailRecruit());

            Employee employee = convertToEmployee(request, id);
            employeeRepository.save(employee);
            //EmployeeDTO dto = new EmployeeDTO(employee);

            return new GeneralResponse<>(HttpStatus.CREATED.value(),"Success", null);

        }catch (DataIntegrityViolationException e){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(),"Existed data", e.getMessage());
        }catch (Exception e) {
            logger.error("Error when create employee: ", e);
            return new GeneralResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error occurred",e.getMessage());
        }
    }

    @Transactional
    private void update(String email) {
        Optional<Recruitment> findResult = recruitmentRepository.findByEmail(email);
        int updated = recruitmentRepository.updateByEmail(email, false);
        if (updated > 0) {
            logger.info("Recruitment updated successfully for email {}", email);
        } else {
            logger.warn("No recruitment found with email {}", email);
        }
    }



    private static Employee convertToEmployee(EmployeeRequest request, String id) {
        Employee employee = Employee.builder()
                .id(id)
                .name(request.getName())
                .gender(request.getGender())
                .birthDate(request.getBirthDate())
                .email(request.getEmail())
                .nation(request.getNation())
                .ethnic(request.getEthnic())
                .phone(request.getPhone())
                .habit(request.getHabit())
                .statusMarital(request.getStatusMarital())
                .build();

        employee.setIdentification(mapIdentification(request.getIdentification(), employee));
        employee.setAddresses(mapAddress(request.getAddress(), employee));
        employee.setRelatives(mapRelatives(request.getRelatives(), employee));
        employee.setBank(mapBank(request.getBank(), employee));

        return employee;
    }


    private static List<Relatives> mapRelatives(List<RelativeDTO> dto, Employee employee) {
        return dto.stream()
                .map(r -> Relatives.builder()
                        .name(r.getName())
                        .relation(r.getRelation())
                        .dateOfBirth(r.getDateOfBirth())
                        .gender(r.getGender())
                        .phone(r.getPhone())
                        .employee(employee)
                        .build())
                .collect(Collectors.toList());
    }


    private static List<Address> mapAddress(List<AddressDTO> dto, Employee employee){
        return dto.stream()
                .map(ad -> Address.builder()
                        .addressType(ad.getAddressType())
                        .street(ad.getStreet())
                        .ward(ad.getWard())
                        .district(ad.getDistrict())
                        .province(ad.getProvince())
                        .employee(employee)
                        .build()).
                collect(Collectors.toList());
    }

    private static Bank mapBank(BankDTO dto, Employee employee){
      return Bank.builder()
              .nameBank(dto.getNameBank())
              .agent(dto.getAgent())
              .nameAccountBank(dto.getNameAccountBank())
              .numberAccountBank(dto.getNumberAccountBank())
              .numberRout(dto.getNumberRout())
              .province(dto.getProvince())
              .employee(employee)
              .build();
    }

    private static Identification mapIdentification(IdentificationDTO dto, Employee employee){
        return Identification.builder()
                .id(dto.getId())
                .place(dto.getPlace())
                .date(dto.getDate())
                .employee(employee)
                .build();
    }


    public GeneralResponse<?> verifyEmployee(String id, String nameDepart){

        Optional<Employee> findEmp = employeeRepository.findById(id);
        Optional<Department> findDepart = departmentRepository.findByName(nameDepart);
        if(findEmp.isEmpty() || findDepart.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"Not Found: {Employee or Department} ", null);
        }

        Employee employee = findEmp.get();
        employee.setStatus("1");
        employee.setDepartment(findDepart.get());

        employeeRepository.save(employee);

        sendAccountEmail(employee);

        return new GeneralResponse<>(HttpStatus.OK.value(), "Success", null);
    }

    private void sendAccountEmail(Employee employee){

        String email = employee.getEmail();
        String subject = "Chào mừng bạn gia nhập công ty";
        String body = "Xin chúc mừng bạn đã chính thức trở thành nhân viên của công ty.\n"
                + "Thông tin tài khoản và mật khẩu cá nhân dùng để đăng nhập vào hệ thống của công ty: " + email + "\n"
                + "Chúng tôi rất vui khi bạn đồng hành cùng đội ngũ!";

        var emailDetails = EmailDetails.builder()
                .recipient(email)
                .subject(subject)
                .msgBody(body)
                .build();

        emailService.sendSimpleMail(emailDetails);
    }


    public GeneralResponse<?> amountEmployeeByStatus(String status, String message){
        long amount = employeeRepository.countEmpByStatus(status);
        return new GeneralResponse<>(HttpStatus.OK.value(), message, amount);
    }


    public GeneralResponse<?> resignConfirmation(String id){
        Optional<Employee> findResult = employeeRepository.findById(id);
        if (findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"Not Found Employee with Id: " + id, null);
        }

        int result = contractRepository.checkContractResign(id, "Hợp đồng nghỉ việc");

        switch (result){
            case 0:
                //Không có hợp đồng nghỉ việc
                return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"Not Found The Resignation Contract", null);
            case 1:
                //Hợp đồng nghỉ việc chưa có ký
                return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"The Resignation Contract has no signing date", null);
            default:
                Employee employee = findResult.get();
                employee.setStatus("0");
                Account account = employee.getAccount();
                if (account != null) {
                    account.setStatus(false);
                }
                employeeRepository.save(employee);
                return new GeneralResponse<>(HttpStatus.OK.value(),"Successful Resignation Confirmation", null);
        }
    }

    @Transactional
    public GeneralResponse<?> update(String id, EmployeeDTO request){
        Optional<Employee> findResult = employeeRepository.findById(id);

        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"Not Found Employee with Id: " + id, null);
        }


        Employee updateEntity = employeeMapper.toEntity(request);

        updateEntity.setId(id);

        if(updateEntity.getIdentification() != null){
            updateEntity.getIdentification().setEmployee(updateEntity);
        }

        // --- Quan hệ 1-N: xóa rồi insert lại ---
        updateEntity.getAddresses().clear();
        if (request.getAddresses() != null) {
            request.getAddresses().forEach(adDto -> {
                Address ad = employeeMapper.toEntity(adDto);
                ad.setEmployee(updateEntity);
                updateEntity.getAddresses().add(ad);
            });
        }

        updateEntity.getRelatives().clear();
        if (request.getRelatives() != null) {
            request.getRelatives().forEach(rDto -> {
                Relatives r = employeeMapper.toEntity(rDto);
                r.setEmployee(updateEntity);
                updateEntity.getRelatives().add(r);
            });
        }

        if(updateEntity.getBank() != null){
            updateEntity.getBank().setEmployee(updateEntity);
        }

       Employee saved = employeeRepository.save(updateEntity);

        return new GeneralResponse<>(HttpStatus.OK.value(),"Successful Update ", employeeMapper.toDto(saved));
    }

}
