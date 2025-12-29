package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.RequirementDTO;
import com.example.hrm.mapper.RequirementMapper;
import com.example.hrm.model.Requirement;
import com.example.hrm.model.TypeRequirement;
import com.example.hrm.repository.RequirementRepository;
import com.example.hrm.repository.TypeContractRepository;
import com.example.hrm.repository.TypeRequirementRepository;
import com.example.hrm.request.RequirementRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequirementService {
    private final RequirementRepository requirementRepository;
    private final RequirementMapper requirementMapper;
    private final TypeRequirementRepository typeRequirementRepository;

    @Transactional(readOnly = true)
    public GeneralResponse<?> getRequirementByType(String type) {
        List<Requirement> list = requirementRepository.findRequirementByType(type);

        if (list.isEmpty()) {
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }

        // map sang DTO
        List<RequirementDTO> dto = list.stream()
                .map(requirementMapper::toDTO)
                .collect(Collectors.toList());

        // trả về response
        return new GeneralResponse<>(HttpStatus.OK.value(), "List", dto);
    }

    //create
    public GeneralResponse<?> create(RequirementRequest request ) {

        boolean existsByName = requirementRepository.existsByName(request.getName());
        if(existsByName){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(), "Exists Namee Requirement", null);
        }

        Optional<TypeRequirement> type = typeRequirementRepository.findByName(request.getType());
        if(type.isEmpty()){
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "Wrong Type Name", null);
        }

        var requirement = Requirement.builder()
                                    .name(request.getName())
                                    .timeFrame(request.getTimeFrame())
                                    .overtimeRate(request.getOvertimeRate())
                                    .describe(request.getDescribe())
                                    .typeRequirement(type.get())
                                    .build();
        requirementRepository.save(requirement);

        // trả về response
        return new GeneralResponse<>(HttpStatus.CREATED.value(), "Successful Create", null);
    }

    //update
    public GeneralResponse<?> update(Long id, RequirementRequest request){
        boolean exists = requirementRepository.existsByNameAndIdNot(request.getName(), id);
        if(exists){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(), "Exists Name Requirement", null);
        }

        Optional<TypeRequirement> type = typeRequirementRepository.findByName(request.getType());
        if(type.isEmpty()){
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "Wrong Type Name", null);
        }

        Optional<Requirement> findResult = requirementRepository.findById(id);
        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found  Requirement with Id: " + id, null);
        }

        Requirement requirement = findResult.get();
        requirement.setName(requirement.getName());
        requirement.setTimeFrame(request.getTimeFrame());
        requirement.setOvertimeRate(request.getOvertimeRate());
        requirement.setDescribe(request.getDescribe());

        requirementRepository.save(requirement);
        return new GeneralResponse<>(HttpStatus.OK.value(), "Successful Update", null);
    }

    //delete
    public GeneralResponse<?> delete(Long id){
        boolean checkRequirementHasDetail = requirementRepository.checkRequirementHasDetail(id);

        if(checkRequirementHasDetail){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(), "Exists Details Requirement", null);
        }
        Optional<Requirement> findResult = requirementRepository.findById(id);
        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found  Requirement with Id: " + id, null);
        }
        Requirement requirement = findResult.get();
        requirementRepository.delete(requirement);
        return new GeneralResponse<>(HttpStatus.OK.value(), "Successful Delete", null);
    }

}
