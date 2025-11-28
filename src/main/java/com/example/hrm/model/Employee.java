package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@ToString(exclude = {"relatives", "bank"})
@Entity
@Table(name = "NhanVien")
public class Employee {
    @Id
    @Column(name = "id_nhan_vien", unique = true)
    private String id;

    @Column(name = "ho_ten")
    private String name;

    @Column(name = "gioi_tinh")
    private Boolean gender;

    @Column(name = "ngay_sinh")
    private LocalDate birthDate;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "quoc_tich")
    private String nation;

    @Column(name = "dan_toc")
    private String ethnic;

    @Column(name = "so_dien_thoai", unique = true)
    private String phone;

    @Column(name = "chung_minh_thu", unique = true)
    private String identification;

    @Column(name = "noi_cap")
    private String issuePlace;

    @Column(name = "ngay_cap")
    private LocalDate issueDate;

    @Column(name = "tam_tru")
    private String tempAddress;

    @Column(name = "thuong_tru")
    private String permanent ;

    @Column(name = "so_thich")
    private String habit;

    @Column(name = "tinh_trang_hon_nhan")
    private String statusMarital;

    @Column(name = "muc_luong")
    private BigDecimal salary;

    @Column(name = "trang_thai")// 0:nghỉ việc - 1:hoạt động - 2:đang đợi duyệt - 3:từ chối
    private String status = "2";

    @Column(name = "id_phong_ban")
    private String idDepart;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Relatives> relatives;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Bank bank;
}

