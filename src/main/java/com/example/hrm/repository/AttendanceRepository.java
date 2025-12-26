package com.example.hrm.repository;

import com.example.hrm.model.Attendance;
import com.example.hrm.projection.AttendanceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, String> {
    List<Attendance> findByTimeOutIsNull();


    @Transactional(readOnly = true)
    @Procedure(procedureName = "sp_FindAllByEmployeeIdAndDateWork")
    List<AttendanceProjection> findAllByIdEmployeeAndDateWork(@Param("idNhanVien") String id,
                                                              @Param("thang") int mount,
                                                              @Param("@nam") int year);

    @Transactional(readOnly = true)
    @Procedure(procedureName = "sp_FindByEmployeeIdAndDateWork")
    List<AttendanceProjection> findByIdEmployeeAndDateWork(@Param("idNhanVien") String id,
                                                               @Param("ngay_lam_viec") LocalDate date);

    @Query(value = "SELECT dbo.CountWorkingByDate(:workDate)", nativeQuery = true)
    int countAttendancesByDateWork(@Param("workDate") LocalDate date);

    @Query(value = "SELECT  dbo.CountLateAttendancesByDateWork(:workDate)", nativeQuery = true)
    int countLateAttendancesByDateWork(@Param("workDate") LocalDate date);
}
