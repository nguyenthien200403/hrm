package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.InviteEmployee;
import com.example.hrm.repository.InviteEmpRepository;
import com.example.hrm.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InviteEmpService {

    @Autowired
    private InviteEmpRepository inviteEmpRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public GeneralResponse<?> checkEmail(String email){
        if(email.isEmpty()){
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST,"Invalid Param Email","InviteEmployee", null);
        }
        Optional<InviteEmployee> findResult = inviteEmpRepository.findByEmail(email);
        if(findResult.isPresent()){
            InviteEmployee inviteEmployee = findResult.get();
            if(!inviteEmployee.getStatus()){
                return new GeneralResponse<>(HttpStatus.BAD_REQUEST, "BLOCKED", "InviteEmployee", inviteEmployee.getEmail());
            }
            String token = jwtUtil.generateToken(inviteEmployee);
            return new GeneralResponse<>(HttpStatus.OK, "token", "InviteEmployee", token);
        }
        return new GeneralResponse<>(HttpStatus.BAD_REQUEST,"No Email","InviteEmployee", null);
    }

}
