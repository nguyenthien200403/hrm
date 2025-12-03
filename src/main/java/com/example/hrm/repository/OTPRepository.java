package com.example.hrm.repository;

import com.example.hrm.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;



public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM OTP otp WHERE otp.email = :email")
    void deleteByEmail(@Param("email")String email);
}
