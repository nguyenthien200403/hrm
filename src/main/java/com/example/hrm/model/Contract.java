package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HopDong")
public class Contract {
    @Id
    @Column(name = "id_hop_dong")
    private String id;

    @Builder.Default
    @Column(name = "ngay_tao")
    private LocalDate dateCreate = LocalDate.now();

    @Column(name = "ngay_bat_dau")
    private LocalDate dateBegin;

    @Column(name = "ngay_ket_thuc")
    private LocalDate dateEnd;

    @Column(name = "chuc_vu")
    private String position;

    @Column(name = "luong_thoa_thuan")
    private BigDecimal salary;

    @Column(name = "thoi_han")
    private int term;

    @Column(name = "ngay_ky")
    private LocalDateTime dateSign;

    @Column(name = "ghi_chu")
    private String note;

    @Column(name = "embed_src")
    private String embedSrc;

    @Column(name = "trang_thai")
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "id_loai_hop_dong")
    private TypeContract typeContract;


    @Override
    public String toString() {
        return "Contract{" +
                "id='" + id + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
