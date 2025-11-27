package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
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

    @Column(name = "ngay_tao")
    private LocalDate date = LocalDate.now();

    @Column(name = "trang_thai")
    private Boolean status = true;

    @Column(name = "id_chuc_nang")
    private int idFunction = 3;
}
