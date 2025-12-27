package com.example.hrm.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "YeuCau")
public class Requirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_yeu_cau")
    private Long id;

    @Column(name = "ten_yeu_cau")
    private String name;

    @Column(name = "so_ngay_qui_dinh")
    private int timeFrame;

    @Column(name = "he_so_tang_ca")
    private BigDecimal overtimeRate;

    @Column(name = "mo_ta")
    private String describe;

    @ManyToOne
    @JoinColumn(name = "id_loai_yeu_cau")
    private TypeRequirement typeRequirement;

}
