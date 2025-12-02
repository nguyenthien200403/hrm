package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ChungMinhThu")
public class Identification {
    @Id
    @Column(name = "id_chung_minh_thu")
    private String id;

    @Column(name = "ngay_cap")
    private LocalDate date;

    @Column(name = "noi_cap")
    private String place;

    @OneToOne
    @JoinColumn(name = "id_nhan_vien")
    private Employee employee;
}
