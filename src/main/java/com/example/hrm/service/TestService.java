package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.Test;
import com.example.hrm.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public GeneralResponse<?> create(Test test){
        try {
            Test newTest = testRepository.save(test);
            return new GeneralResponse<>(HttpStatus.OK, "Success Create","Test", newTest);
        }catch (Exception e){
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST, e.getMessage()," Test", null);
        }
    }
}
