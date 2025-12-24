package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ThoiGianChamCong")
public class TimeTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_thoi_gian")
    private Long id;

    @Column(name = "gio_vao_qui_dinh")
    private LocalTime startTime;

    @Column(name ="gio_ra_qui_dinh")
    private LocalTime endTime;

    @Column(name ="ghi_chu")
    private String note;

    @Builder.Default
    @Column(name = "id_chuc_nang")
    private int idFunction = 1;
}
