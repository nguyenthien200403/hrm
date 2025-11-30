package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.TypeContract;
import com.example.hrm.repository.TypeContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeContractService {
    private final TypeContractRepository repository;

    public GeneralResponse<?> getAllTypeContract(){
        List<TypeContract> list = repository.findAll();
        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(), "Contract type", list);
    }

    public GeneralResponse<?> create(String name){
        Optional<TypeContract> findResult = repository.findByName(name);
        if(findResult.isPresent()){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(), "Existed name", null);
        }
        TypeContract typeContract = new TypeContract();
        typeContract.setName(name);

        repository.save(typeContract);
        return new GeneralResponse<>(HttpStatus.CREATED.value(), "Success", name);
    }
}
