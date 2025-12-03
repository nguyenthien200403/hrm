package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.model.OTP;
import com.example.hrm.repository.EmployeeRepository;
import com.example.hrm.repository.OTPRepository;
import com.example.hrm.sendEmail.EmailDetails;
import com.example.hrm.sendEmail.EmailService;
import com.example.hrm.sendEmail.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OTPService {
    private final OTPRepository otpRepository;
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;




    public String generateOtp(){
        int otp = 100000 + new Random().nextInt(900000); // 6 số
        return String.valueOf(otp);
    }

    public void forgotPassword(String email){

        if(!employeeRepository.existsByEmail(email)){
            return;
        }
        createAndSendOtpToEmail(email);
    }

    public void createAndSendOtpToEmail(String email){

        otpRepository.deleteByEmail(email);

        var otp = OTP.builder()
                .otp(generateOtp())
                .email(email)
                .expiryDate(LocalDateTime.now().plusMinutes(10))
                .build();

        otpRepository.save(otp);
        //Gửi otp qua email;
        sendOtpEmail(email,generateOtp());
    }

    public void sendOtpEmail(String email, String otp) {
        String subject = "Mã OTP đặt lại mật khẩu";
        String body = "Mã OTP của bạn là: " + otp + ". OTP có hiệu lực trong 10 phút.";

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(email);
        emailDetails.setSubject(subject);
        emailDetails.setMsgBody(body);

       emailService.sendSimpleMail(emailDetails);
    }


    public GeneralResponse<?> verifyOtp(String email, String otp){
        Optional<OTP> findResult = otpRepository.findByEmail(email);

        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "Wrong otp",null);
        }
        OTP resetOtp = findResult.get();

        if (resetOtp.getExpiryDate().isBefore(LocalDateTime.now())) {
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "OTP has expired",null);
        }

        if (!resetOtp.getOtp().equals(otp)) {
            return new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(), "Wrong otp",null);
        }

        return new GeneralResponse<>(HttpStatus.OK.value(), "Verified OTP",null);

    }
}
