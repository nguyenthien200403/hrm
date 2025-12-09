package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "LoaiYeuCau")
public class TypeRequirement {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id_loai_yeu_cau")
    private Long id;

    @Column(name = "ten_loai_yeu_cau")
    private String name;

    @OneToMany(mappedBy = "typeRequirement")
    private List<Requirement> requirement;
}

