package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Folder")
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_folder")
    private Long idFolder;

    @Column(name = "folder_name", nullable = false)
    private String folderName;

    @Builder.Default
    @Column(name ="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    //    @OneToOne
//    @JoinColumn(name ="id_nhan_vien", unique = true)
    @Column(name = "id_nhan_vien")
    private String idEmployee;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;
}
