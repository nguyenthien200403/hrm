package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.TypeRequirementDTO;
import com.example.hrm.mapper.RequirementMapper;
import com.example.hrm.model.TypeRequirement;
import com.example.hrm.projection.BasicInfoProjection;
import com.example.hrm.repository.TypeRequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeRequirementService {
    private final TypeRequirementRepository repository;

    public GeneralResponse<?> create(String name){
        Optional<TypeRequirement> findResult =  repository.findByName(name);

        if(findResult.isPresent()){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(), "Existed Type Requirement", null);
        }

        TypeRequirement typeRequirement = new TypeRequirement();
        typeRequirement.setName(name);
        repository.save(typeRequirement);
        return new GeneralResponse<>(HttpStatus.CREATED.value(), "Successful Create New Type Requirement", name);
    }

    public GeneralResponse<?> getAllNameTypeRequirement(){
        List<BasicInfoProjection> list = repository.findAllByName();
        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(), "List", list);
    }

//    public GeneralResponse<?> getAllTypeRequirement(){
//        List<TypeRequirement> list = repository.findAll();
//        if(list.isEmpty()){
//            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
//        }
//        List<TypeRequirementDTO> dto = list.stream()
//                                            .map(RequirementMapper::toDTO)
//                                            .toList();
//        return new GeneralResponse<>(HttpStatus.OK.value(), "List", dto);
//    }
}
