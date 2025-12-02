package com.example.hrm.repository;


import com.example.hrm.model.Identification;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdentificationRepository extends JpaRepository<Identification, String> {
    @NonNull
    Optional<Identification> findById( @NonNull String id);
}
