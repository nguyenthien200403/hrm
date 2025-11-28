package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "employee")
@Entity
@Table(name = "NganHang")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ngan_hang")
    private Long id;

    @Column(name = "ten_ngan_hang")
    private String nameBank;

    @Column(name = "dai_ly")
    private String agent;

    @Column(name = "so_tai_khoan")
    private String numberAccountBank;

    @Column(name = "so_dinh_tuyen")
    private String numberRout;

    @Column(name = "ten_tai_khoan")
    private String nameAccountBank;

    @Column(name = "tinh")
    private String province;

    @OneToOne
    @JoinColumn(name = "id_nhan_vien")
    private Employee employee;
}
