package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.Recruitment;
import com.example.hrm.repository.RecruitmentRepository;

import com.example.hrm.request.RecruitmentRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;

    private static final Logger logger = LoggerFactory.getLogger(RecruitmentService.class);

    public GeneralResponse<?> verifyEmail(String email){
        Optional<Recruitment> findResult = recruitmentRepository.findByEmail(email);
        if(findResult.isPresent()){
            Recruitment recruitment = findResult.get();
            if(!recruitment.getStatus()){
                return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"Email Not Found",null);
            }
            return new GeneralResponse<>(HttpStatus.OK.value(), "Verification Success", recruitment);
        }
        return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Email", null);
    }


    public GeneralResponse<?> create(RecruitmentRequest request){
        Optional<Recruitment> findResult = recruitmentRepository.findByEmail(request.getEmail());
        if(findResult.isPresent()){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(),"Email existed",null);
        }
        try{
            Recruitment recruitment = new Recruitment();
            recruitment.setEmail(request.getEmail());
            recruitment.setName(request.getName());
            recruitment.setDate(LocalDate.now());

            recruitmentRepository.save(recruitment);
            return new GeneralResponse<>(HttpStatus.CREATED.value(),"Success", recruitment);

        }catch (DataIntegrityViolationException e){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(),"Email existed",null);
        }catch (Exception e) {
            logger.error("Error when create recruitment: ", e);
            return new GeneralResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error occurred",null);
        }
    }
}
