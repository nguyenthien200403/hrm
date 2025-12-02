package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PhongBan")
public class Department {
    @Id
    @Column(name = "id_phong_ban")
    private String  id;

    @Column(name = "ten_phong_ban")
    private String name;

    @Builder.Default
    @Column(name = "ngay_thanh_lap")
    private LocalDate date = LocalDate.now();

    @Column(name = "mo_ta")
    private String describe;

    @OneToMany(mappedBy = "department")
    private List<Employee> employee;

}
