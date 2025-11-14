package com.example.hrm.repository;

import com.example.hrm.model.InviteEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InviteEmpRepository extends JpaRepository<InviteEmployee, Long> {
    @Query("SELECT a FROM InviteEmployee a WHERE a.email = :email")
    Optional<InviteEmployee> findByEmail(@Param("email") String email);
}
