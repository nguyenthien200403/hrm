package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.InviteEmployee;
import com.example.hrm.model.Test;
import com.example.hrm.repository.InviteEmpRepository;
import com.example.hrm.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private InviteEmpRepository inviteEmpRepository;

    public GeneralResponse<?> create(Test test, String email){
        try {
            Test newTest = testRepository.save(test);
            Optional<InviteEmployee> findResult = inviteEmpRepository.findByEmail(email);
            if(findResult.isPresent()){
                InviteEmployee inviteEmployee = findResult.get();
                inviteEmployee.setStatus(false);
                inviteEmpRepository.save(inviteEmployee);
            }
            return new GeneralResponse<>(HttpStatus.OK, "Success Create","Test", newTest);
        }catch (Exception e){
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST, e.getMessage()," Test", null);
        }
    }
}
