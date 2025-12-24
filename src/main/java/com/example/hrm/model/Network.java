package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "MangNoiBo")
public class Network {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_mang")
    private Long id;

    private String ssid;

    @Column(name ="mac_router")
    private String macRouter;

    @Column(name ="ip_public")
    private String ipPublic;

    @Builder.Default
    @Column(name = "id_chuc_nang")
    private int idFunction = 2;
}
