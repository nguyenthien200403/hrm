package com.example.hrm.repository;

import com.example.hrm.model.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementRepository extends JpaRepository<Requirement, Long>{
}
