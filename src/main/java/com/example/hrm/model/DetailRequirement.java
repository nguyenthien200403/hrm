package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ChiTietYeuCau")
public class DetailRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chi_tiet_yeu_cau")
    private Long id;

    @Builder.Default
    @Column(name = "ngay_gui")
    private LocalDateTime dateSend = LocalDateTime.now();

    @Column(name = "ngay_bat_dau")
    private LocalDate dateBegin;

    @Column(name = "ngay_ket_thuc")
    private LocalDate dateEnd;

    @Column(name = "so_gio_dang_ky")
    private BigDecimal amountHour;

    @Column(name = "so_ngay_dang_ky")
    private int amountDate;

    @Builder.Default
    @Column(name = "trang_thai")
    private String status = "2"; // 0:Từ chối - 1:Đã Duyệt - 2:đang đợi duyệt

    @Column(name = "noi_dung")
    private String content;

    @Builder.Default
    @Column(name = "ghi_chu")
    private String note = "";

    @ManyToOne
    @JoinColumn(name  = "id_nhan_vien")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "id_yeu_cau")
    private Requirement requirement;
}
