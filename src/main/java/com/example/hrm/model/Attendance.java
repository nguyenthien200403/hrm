package com.example.hrm.model;


import jakarta.persistence.*;
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

    @Column(name = "gio_vao")
    private LocalTime timeIn;

    @Column(name = "gio_ra")
    private LocalTime timeOut;

    @Column(name = "tong_gio_lam")
    private BigDecimal totalTime;

    @Column(name ="ghi_chu")
    private String note;

    @Column(name = "trang_thai")
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private Employee employee;

}
