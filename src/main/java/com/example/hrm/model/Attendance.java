package com.example.hrm.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "BangCong")
public class Attendance {
    @Id
    @Column(name = "id_bang_cong")
    private String id;

    @Column(name ="ngay_lam_viec")
    private LocalDate dateWork;

    @Column(name = "gio_vao_thuc_te")
    private LocalTime timeIn;

    @Column(name = "gio_ra_thuc_te")
    private LocalTime timeOut;

    @Column(name = "tong_gio_lam")
    private BigDecimal totalTime;

    @Column(name ="ghi_chu")
    private String note;

    @Column(name = "id_nhan_vien")
    private String idEmployee;

}
