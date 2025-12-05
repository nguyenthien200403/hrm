package com.example.hrm.repository;

import com.example.hrm.model.TypeContract;
import com.example.hrm.projection.BasicInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeContractRepository extends JpaRepository<TypeContract, Long> {
    Optional<TypeContract> findByName(String name);

    //@Query("SELECT ct.name AS name FROM TypeContract ct")
    List<BasicInfoProjection> findAllBy();
}
