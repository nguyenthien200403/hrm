package com.example.hrm;

import com.example.hrm.model.OTP;
import com.example.hrm.repository.OTPRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class OTPTest {
    @Autowired
    private OTPRepository otpRepository;

    public String generateOtp(){
        int otp = 100000 + new Random().nextInt(900000); // 6 số
        return String.valueOf(otp);
    }

    @Test
    public void createOTPTest(){
        String email = "nguyenngocthien200403@gmail.com";

        var otp = OTP.builder()
                .otp(generateOtp())
                .email(email)
                .expiryDate(LocalDateTime.now().plusMinutes(10))
                .build();

        assertThat(otpRepository.save(otp));
    }

    @Test
    public void findByEmailTest(){
        String email = "nguyenngocthien200403@gmail.com";
        Optional<OTP> findResult = otpRepository.findByEmail(email);
        assertThat(findResult).isPresent();
    }

    @Test
    public void deleteTestSuccess(){
        String email = "nguyenngocthien200403@gmail.com";



    }
}
