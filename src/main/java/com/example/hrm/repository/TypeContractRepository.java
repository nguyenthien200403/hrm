package com.example.hrm.repository;

import com.example.hrm.model.TypeContract;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeContractRepository extends JpaRepository<TypeContract, Long> {
    Optional<TypeContract> findByName(String name);
}
