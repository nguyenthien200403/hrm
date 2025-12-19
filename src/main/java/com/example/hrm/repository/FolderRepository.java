package com.example.hrm.repository;

import com.example.hrm.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    @Query("SELECT f FROM Folder as f WHERE f.idEmployee =:id")
    Optional<Folder> findByIdEmployee(String id);
}
