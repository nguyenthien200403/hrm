package com.example.hrm.repository;

import com.example.hrm.model.Attendance;
import com.example.hrm.projection.AttendanceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;


public interface AttendanceRepository extends JpaRepository<Attendance, String> {
    List<Attendance> findByTimeOutIsNull();

    List<Attendance> findByStatusIsNull();

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

    @Query(value = "SELECT  dbo.CountLateAttendancesByDateWork(:workDate, :graceMinutes)", nativeQuery = true)
    int countLateAttendancesByDateWork(@Param("workDate") LocalDate date,
                                       @Param("graceMinutes") int timeThreshold);



    @Transactional(readOnly = true)
    @Procedure(procedureName = "sp_FindAllAttendanceByManager")
    List<Attendance> getAllAttendanceByManager(@Param("managerId") String id,
                                                         @Param("month") int month,
                                                         @Param("year") int year,
                                                         @Param("employeeName") String employeeName);

    @Transactional(readOnly = true)
    @Procedure(procedureName = "sp_FindAllAttendanceByAdmin")
    List<Attendance> getAllAttendanceByAdmin(@Param("adminId") String id,
                                               @Param("month") int month,
                                               @Param("year") int year,
                                               @Param("employeeName") String employeeName);



    @Query(value = "SELECT dbo.CountWorkingByDateManager(:workDate, :departmentId)", nativeQuery = true)
    int countWorkingByDateManager(@Param("workDate") LocalDate date,
                                  @Param("departmentId") String departmentId);

    @Query(value = "SELECT dbo.CountLateAttendancesByDateManager(:workDate, :graceMinutes, :departmentId)", nativeQuery = true)
    int countLateAttendancesByDateManager(@Param("workDate") LocalDate date,
                                          @Param("graceMinutes") int timeThreshold,
                                          @Param("departmentId") String departmentId);



}
