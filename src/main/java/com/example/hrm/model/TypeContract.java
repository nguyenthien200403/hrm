package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "LoaiHopDong")
public class TypeContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_loai_hop_dong")
    private Long id;

    @Column(name = "ten_loai_hop_dong")
    private String name;

    @Column(name = "co_luong")
    private Boolean hasSalary;

    @OneToMany(mappedBy = "typeContract")
    private List<Contract> contract;
}
