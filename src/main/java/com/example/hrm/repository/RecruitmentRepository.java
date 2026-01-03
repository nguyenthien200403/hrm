package com.example.hrm.repository;

import com.example.hrm.model.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    @Query("SELECT a FROM Recruitment a WHERE a.email = :email")
    Optional<Recruitment> findByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("UPDATE Recruitment  SET status = :status WHERE email = :email")
    int updateByEmail(@Param("email") String email, @Param("status") Boolean status);

    @Query("SELECT re FROM Recruitment re WHERE re.date < :date AND re.status = true")
    List<Recruitment> findExpiredRecruitment(@Param("date")LocalDate date);

}
