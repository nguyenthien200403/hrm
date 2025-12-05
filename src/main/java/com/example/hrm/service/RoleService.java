package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.projection.BasicInfoProjection;
import com.example.hrm.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public GeneralResponse<?> getAllNameRole(){
        List<BasicInfoProjection> list = roleRepository.findAllBy();
        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }
        return new GeneralResponse<>(HttpStatus.OK.value(), "List Name Role", list);
    }
}
