package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "HopDong")
public class Contract {
    @Id
    @Column(name = "id_hop_dong")
    private String id;

    @Column(name = "ngay_bat_dau")
    private LocalDate dateBegin;

    @Column(name = "ngay_ket_thuc")
    private LocalDate dateEnd;

    @Column(name = "chuc_vu")
    private String position;

    @Column(name = "luong_thoa_thuan")
    private BigDecimal salary;

    @Column(name = "thoi_han_hop_dong")
    private int term;

    @Column(name = "ngay_ky")
    private LocalDate dateSign;

    @Column(name = "ghi_chu")
    private String note;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "id_loai_hop_dong")
    private TypeContract typeContract;
}
