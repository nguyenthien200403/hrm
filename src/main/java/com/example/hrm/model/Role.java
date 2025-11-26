package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "VaiTro")
public class Role {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_vai_tro")
    private Long id;

    @Column(name = "ten_vai_tro")
    private String nameRole;

    @Column(name = "mo_ta")
    private String describe;
}
