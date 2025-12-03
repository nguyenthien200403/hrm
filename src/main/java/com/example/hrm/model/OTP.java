package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "otp")
    private String otp;

    @Column(name = "email")
    private String email;

    @Column(name = "thoi_gian")
    private LocalDateTime expiryDate;

    @Builder.Default
    @Column(name = "id_chuc_nang")
    private int idFunction = 4;
}
