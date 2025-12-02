package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DiaChi")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dia_chi")
    private Long id;

    @Column(name = "loai_dia_chi")
    private String addressType;

    @Column(name = "duong_pho")
    private String street;

    @Column(name = "tinh")
    private String ward;

    @Column(name = "huyen")
    private String district;

    @Column(name = "thanh_pho")
    private String province;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private Employee employee;
}
