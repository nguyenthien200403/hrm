package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.RecruitmentDTO;
import com.example.hrm.model.Recruitment;
import com.example.hrm.repository.RecruitmentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;


@Service
public class RecruitmentService {
    @Autowired
    private RecruitmentRepository recruitmentRepository;

    private static final Logger logger = LoggerFactory.getLogger(RecruitmentService.class);


    public GeneralResponse<?> verifyEmail(String email){
        Optional<Recruitment> findResult = recruitmentRepository.findByEmail(email);
        if(findResult.isPresent()){
            Recruitment recruitment = findResult.get();
            if(!recruitment.getStatus()){
                return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"Blocked",null);
            }
            return new GeneralResponse<>(HttpStatus.OK.value(), "Access Token", recruitment);
        }
        return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "Email Not Found", null);
    }


    public GeneralResponse<?> create(RecruitmentDTO dto){
        Optional<Recruitment> findResult = recruitmentRepository.findByEmail(dto.getEmail());
        if(findResult.isPresent()){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(),"Email existed",null);
        }

        try{
            Recruitment recruitment = new Recruitment();
            recruitment.setEmail(dto.getEmail());
            recruitment.setName(dto.getName());
            recruitment.setDate(LocalDate.now());
            recruitmentRepository.save(recruitment);
            return new GeneralResponse<>(HttpStatus.OK.value(),"Success", recruitment);
        }catch (DataIntegrityViolationException e){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(),"Email existed",null);
        }catch (Exception e) {
            logger.error("Error when create recruitment: ", e);
            return new GeneralResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error occurred",null);
        }
    }

    @Transactional
    public void update(String email){
        try {
            Boolean status = false;
            int updated = recruitmentRepository.updateByEmail(email, status);
        }catch (IllegalArgumentException e){
            logger.error("Invalid data", e);
        }catch (Exception e){
            logger.error("Error when update recruitment", e);
        }

    }
}
