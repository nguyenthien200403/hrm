package com.example.hrm.repository;


import com.example.hrm.model.TypeRequirement;
import com.example.hrm.projection.BasicInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TypeRequirementRepository extends JpaRepository<TypeRequirement, Long> {

    Optional<TypeRequirement> findByName(String name);

    @Query("SELECT t.name AS name FROM TypeRequirement t")
    List<BasicInfoProjection> findAllByName();

}
