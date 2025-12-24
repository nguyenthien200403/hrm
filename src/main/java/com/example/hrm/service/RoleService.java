package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.Role;
import com.example.hrm.projection.BasicInfoProjection;
import com.example.hrm.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    public GeneralResponse<?> getAllNameRole(){
        List<BasicInfoProjection> list = repository.findAllBy();
        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(), "List Name Role", list);
    }

    public GeneralResponse<?> create(String name, String describe){
        Optional<Role> findResult = repository.findByName(name);
        if(findResult.isPresent()){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(), "Existed Name: " + name, null);
        }
        var role = Role.builder()
                .name(name)
                .describe(describe)
                .build();

        repository.save(role);
        return new GeneralResponse<>(HttpStatus.CREATED.value(), "Success create", null);
    }
}
