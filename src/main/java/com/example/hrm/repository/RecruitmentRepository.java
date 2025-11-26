package com.example.hrm.repository;

import com.example.hrm.model.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    @Query("SELECT a FROM Recruitment a WHERE a.email = :email")
    Optional<Recruitment> findByEmail(@Param("email") String email);

    @Modifying
    @Query("UPDATE Recruitment  SET status = :status WHERE email = :email")
    int updateByEmail(@Param("email") String email, @Param("status") Boolean status);

}
