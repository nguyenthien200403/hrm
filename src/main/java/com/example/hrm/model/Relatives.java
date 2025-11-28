package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString(exclude = "employee")
@Entity
@Table(name = "NguoiThan")
public class Relatives {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nguoi_than")
    private Long id;

    @Column(name = "ho_ten")
    private String name;

    @Column(name = "quan_he")
    private String relation;

    @Column(name = "so_dien_thoai")
    private String phone;

    @Column(name = "ngay_sinh")
    private LocalDate dateOfBirth;

    @Column(name = "gioi_tinh")
    private Boolean gender;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private Employee employee;
}
