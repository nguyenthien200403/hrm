package com.example.hrm.repository;

import com.example.hrm.model.Role;
import com.example.hrm.projection.BasicInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    //@Query("SELECT r.name AS name FROM Role r")
    List<BasicInfoProjection> findAllBy();
}
