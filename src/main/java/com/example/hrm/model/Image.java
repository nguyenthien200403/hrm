package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image")
    private Long idImage;

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    @Column(name = "file_name", nullable = false)
    private String fileName;

//    @Column(name = "file_path", nullable = false)
//    private String filePath;

    @Builder.Default
    @Column(name = "upload_time",nullable = false, updatable = false)
    private LocalDateTime uploadTime = LocalDateTime.now();
}
