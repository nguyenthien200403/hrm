package com.example.hrm.repository;

import com.example.hrm.model.Employee;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.hrm.projection.EmployeeProjection;


import java.util.List;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Employee e WHERE e.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Employee e WHERE e.phone = :phone")
    boolean existsByPhone(@Param("phone") String phone);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Employee e WHERE e.id = :id")
    boolean existById(@Param("id")String id);


    @Query("SELECT COUNT(e) FROM Employee e WHERE e.status = :status")
    long countEmpByStatus(@Param("status") String status);


    @NonNull
    Optional<Employee> findById(@NonNull String id);


    @Query("SELECT e.id AS id," +
            " e.name AS name," +
            " e.email AS email," +
            " e.gender AS gender " +
            "FROM Employee e " +
            "WHERE (:status = '1' AND e.department.name = :name)")
    List<EmployeeProjection> findAllByStatusAndDepartment(@Param("status") String status,
                                                          @Param("name") String name);

    List<EmployeeProjection> findAllByStatus(String status);


    @Query("SELECT e.id AS id, " +
            "e.name AS name, " +
            "e.email AS email, " +
            "e.gender AS gender " +
            "FROM Employee e " +
            "WHERE ( " +
            "  LOWER(e.id) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "  LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            ") " +
            "AND (e.department.name = :name)" +
            "AND (e.status = :status)")
    List<EmployeeProjection> searchEmployeesBy(@Param("keyword") String keyword,
                                               @Param("name") String name,
                                               @Param("status") String status);

}
