package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TuyenDung")
public class Recruitment {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_tuyen_dung")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "ho_ten")
    private String name;

    @Builder.Default
    @Column(name = "ngay_tao")
    private LocalDate date = LocalDate.now();

    @Builder.Default
    @Column(name = "trang_thai")
    private Boolean status = true;

    @Builder.Default
    @Column(name = "id_chuc_nang")
    private int idFunction = 3;
}
