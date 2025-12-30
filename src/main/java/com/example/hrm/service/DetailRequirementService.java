package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.DetailRequirement;
import com.example.hrm.model.Employee;
import com.example.hrm.model.Requirement;
import com.example.hrm.repository.DetailRequirementRepository;
import com.example.hrm.repository.EmployeeRepository;
import com.example.hrm.repository.RequirementRepository;
import com.example.hrm.request.DetailRequirementRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DetailRequirementService {
    private final DetailRequirementRepository repository;
    private final EmployeeRepository employeeRepository;
    private final RequirementRepository requirementRepository;

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

        repository.save(detailRequirement);

        return new GeneralResponse<>(HttpStatus.CREATED.value(),
                "Your request details have been successfully created and submitted", null);
    }




    //show personal

    //show admin

    //show manager


    //show confirm -- admin - manager  DashBoard


    //confirm -- admin - manager
    public GeneralResponse<?> confirm(Long id, String note, String status){
        Optional<DetailRequirement> findResult = repository.findById(id);
        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"" , null);
        }

        DetailRequirement detailRequirement = findResult.get();
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
}
