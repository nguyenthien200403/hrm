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
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM Employee e WHERE e.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM Employee e WHERE e.phone = :phone")
    boolean existsByPhone(@Param("phone") String phone);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM Employee e WHERE e.identification = :identification")
    boolean existsByIdentification(@Param("identification") String identification);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM Employee e WHERE e.id = :id")
    boolean existById(@Param("id")String id);

    List<EmployeeProjection> findAllByStatus(String status);


    @NonNull
    Optional<Employee> findById(@NonNull String id);


    @Modifying
    @Query("UPDATE Employee SET status = :status, idDepart = :idDepart WHERE id = :id")
    int updateById(@Param("status") String status, @Param("idDepart") String idDepart, String id);

}
