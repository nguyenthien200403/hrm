package com.example.hrm.service;

import com.example.hrm.Impl.RequirementCountProjectionImpl;
import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.DetailRequirementDTO;
import com.example.hrm.mapper.DetailRequirementMapper;
import com.example.hrm.model.Department;
import com.example.hrm.model.DetailRequirement;
import com.example.hrm.model.Employee;
import com.example.hrm.model.Requirement;
import com.example.hrm.projection.DetailRequirementProjection;
import com.example.hrm.projection.RequirementCountProjection;
import com.example.hrm.repository.DetailRequirementRepository;
import com.example.hrm.repository.EmployeeRepository;
import com.example.hrm.repository.RequirementRepository;
import com.example.hrm.request.DetailRequirementRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DetailRequirementService {
    private final DetailRequirementRepository repository;
    private final EmployeeRepository employeeRepository;
    private final RequirementRepository requirementRepository;
    private final DetailRequirementMapper detailRequirementMapper;

    @PersistenceContext
    private EntityManager entityManager;

    private int calculateAmountDate(LocalDate dateBegin, LocalDate dateEnd) {
        return (int) (ChronoUnit.DAYS.between(dateBegin, dateEnd) + 1);
    }

    private BigDecimal calculateAmountHour(int amountDate, BigDecimal amountHourPerDay) {
        return amountHourPerDay == null ? null : BigDecimal.valueOf(amountDate).multiply(amountHourPerDay);
    }

    private Requirement getRequirement(String requirementName) {
        return requirementRepository.findByName(requirementName)
                .orElseThrow(() -> new RuntimeException("Not Found Requirement"));
    }

    //create
    public GeneralResponse<?> create(String id, DetailRequirementRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found Employee with Id: " + id));

        Requirement requirement = getRequirement(request.getRequirement());

        LocalDate dateBegin = request.getDateBegin();
        LocalDate dateEnd = request.getDateEnd() == null ? dateBegin : request.getDateEnd();
        int amountDate = calculateAmountDate(dateBegin, dateEnd);
        BigDecimal amountHour = calculateAmountHour(amountDate, request.getAmountHour());

        DetailRequirement detailRequirement = DetailRequirement.builder()
                .dateBegin(dateBegin)
                .dateEnd(dateEnd)
                .amountHour(amountHour)
                .amountDate(amountDate)
                .content(request.getContent())
                .employee(employee)
                .requirement(requirement)
                .build();

        try {
            repository.save(detailRequirement);
            return new GeneralResponse<>(HttpStatus.CREATED.value(),
                    "Your request details have been successfully created and submitted", null);
        } catch (DataAccessException ex) { // Trigger rollback sẽ ném ra lỗi SQL
             return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "Requested days exceed remaining quota", null);
        }

    }


    //show admin

    //show manager


    //show confirm -- admin - manager  DashBoard
    @Transactional
    public GeneralResponse<?> getAllDetailRequirementWithStatusAndDateConfirm(){
        List<DetailRequirementProjection> list = repository.findAllDetailRequirementWithStatusAndDateConfirm();
        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"Empty" , null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(),"List Detail Requirement had been approved" , list);
    }


    //confirm -- admin - manager
    public GeneralResponse<?> confirm(Long id, String note, String status){
        Optional<DetailRequirement> findResult = repository.findById(id);
        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"Not Found Detail Requirement" , null);
        }

        DetailRequirement detailRequirement = findResult.get();
        detailRequirement.setDateConfirm(LocalDateTime.now());
        detailRequirement.setStatus(status);
        detailRequirement.setNote(note);

        String msg = "The request approved successfully.";
        if("0".equals(status)){
            msg = "The request has been denied.";
        }

        repository.save(detailRequirement);

        return new GeneralResponse<>(HttpStatus.OK.value(), msg, null);

    }


    //update personal
    public GeneralResponse<?> update(Long id, DetailRequirementRequest request) {
        DetailRequirement detail = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found Detail Requirement with Id: " + id));

        if (!"2".equals(detail.getStatus())) {
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(),
                    "Cannot be updated because this request has been confirmed", null);
        }

        Requirement requirement = getRequirement(request.getRequirement());

        LocalDate dateBegin = request.getDateBegin();
        LocalDate dateEnd = request.getDateEnd() == null ? dateBegin : request.getDateEnd();
        int amountDate = calculateAmountDate(dateBegin, dateEnd);
        BigDecimal amountHour = calculateAmountHour(amountDate, request.getAmountHour());

        detail.setDateSend(LocalDateTime.now());
        detail.setDateBegin(dateBegin);
        detail.setDateEnd(dateEnd);
        detail.setAmountDate(amountDate);
        detail.setAmountHour(amountHour);
        detail.setContent(request.getContent());
        detail.setRequirement(requirement);

        repository.save(detail);

        return new GeneralResponse<>(HttpStatus.OK.value(), "Updated successfully", null);
    }


    //delete personal
    public GeneralResponse<?> delete(Long id) {
        Optional<DetailRequirement> findResult= repository.findById(id);

        if (findResult.isEmpty()) {
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),
                    "Not Found Detail Requirement with Id: " + id, null);
        }

        DetailRequirement detail = findResult.get();

        if (!"2".equals(detail.getStatus())) {
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(),
                    "Cannot be deleted because this request has been confirmed", null);
        }

        repository.delete(detail);

        return new GeneralResponse<>(HttpStatus.OK.value(),
                "Deleted successfully", null);
    }



    @Transactional(readOnly = true)
    public RequirementCountProjection countDetailRequirementByDateAndRole(String procedureName,String employeeId, int month, int year) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery(procedureName);

        query.registerStoredProcedureParameter("employeeId", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("month", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("year", Integer.class, ParameterMode.IN);

        query.setParameter("employeeId", employeeId);
        query.setParameter("month", month);
        query.setParameter("year", year);
        Object[] result = (Object[]) query.getSingleResult();

        return new RequirementCountProjectionImpl(
                ((Number) result[0]).intValue(),
                result[1] == null ? 0 : ((Number) result[1]).intValue(),
                result[2] == null ? 0 : ((Number) result[2]).intValue(),
                result[3] == null ? 0 : ((Number) result[3]).intValue()
        );
    }

    public GeneralResponse<?> getAmountDetailRequirementByDateAndEmployee(String id, int month, int year) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found Employee with Id: " + id));

        String procedureName =  "sp_CountDetailRequirementByDateAndEmployee";

        RequirementCountProjection result = countDetailRequirementByDateAndRole(procedureName,id, month, year);

        if (result == null) {
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }

        return new GeneralResponse<>(HttpStatus.OK.value(),
                "The number of detail requirements in " + month + " - " + year, result);
    }



    //show personal
    @Transactional
    public GeneralResponse<?> getAllDetailRequirementByEmployee(String id, String status, String type,
                                                                int month, int year){
        List<DetailRequirement> list = repository.findAllDetailRequirementByEmployee(id, status, type,
                                                                                                month, year);
        if (list.isEmpty()) {
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }

        List<DetailRequirementDTO> dto = list.stream()
                                            .map(detailRequirementMapper::toDTO)
                                            .collect(Collectors.toList());

        return new GeneralResponse<>(HttpStatus.OK.value(), "List", dto);
    }





    public GeneralResponse<?> getAmountDetailRequirementByDateAndManager(String id, int month, int year) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found Employee with Id: " + id));

       String procedureName = "sp_CountDetailRequirementByDateAndManager";

        RequirementCountProjection result = countDetailRequirementByDateAndRole(procedureName, id, month, year);

        if (result == null) {
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }

        return new GeneralResponse<>(HttpStatus.OK.value(),
                "The number of detail requirements by " + employee.getDepartment().getName() + " in "
                        + month + " - " + year, result);
    }


    public GeneralResponse<?> getAmountDetailRequirementByDateAndAdmin(String id, int month, int year) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found Employee with Id: " + id));

        String procedureName = "sp_CountDetailRequirementByAdmin";

        RequirementCountProjection result = countDetailRequirementByDateAndRole(procedureName, id, month, year);

        if (result == null) {
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }

        return new GeneralResponse<>(HttpStatus.OK.value(),
                "The number of detail requirements of Managers in "
                        + month + " - " + year, result);
    }

    //show manager
    @Transactional
    public GeneralResponse<?> getAllDetailRequirementByManager(String id, String status, String type,
                                                                int month, int year){
        List<DetailRequirement> list = repository.findAllDetailRequirementByManager(id, status,
                                                         type, month, year);
        if (list.isEmpty()) {
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }

        List<DetailRequirementDTO> dto = list.stream()
                .map(detailRequirementMapper::toDTO)
                .collect(Collectors.toList());

        return new GeneralResponse<>(HttpStatus.OK.value(), "List", dto);
    }

    //show admin
    @Transactional
    public GeneralResponse<?> getAllDetailRequirementByAdmin(String id, String status, String type,
                                                               int month, int year){
        List<DetailRequirement> list = repository.findAllDetailRequirementByAdmin(id, status,
                type, month, year);
        if (list.isEmpty()) {
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }

        List<DetailRequirementDTO> dto = list.stream()
                .map(detailRequirementMapper::toDTO)
                .collect(Collectors.toList());

        return new GeneralResponse<>(HttpStatus.OK.value(), "List", dto);
    }

    public GeneralResponse<?> getRemainingStandardLeaveDays(String id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found Employee with Id: " + id));

        int year = LocalDate.now().getYear();

        int count = repository.countRemainingStandardLeaveDays(id, year);

        return new GeneralResponse<>(HttpStatus.OK.value(), "The number of days remaining to use standard leave request", count);

    }
}
