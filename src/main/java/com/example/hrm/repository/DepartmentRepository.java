package com.example.hrm.repository;

import com.example.hrm.model.Department;
import com.example.hrm.projection.DepartmentProjection;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {

    @Query("SELECT d.name AS name FROM Department d")
    List<DepartmentProjection> findAllByName();

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Department d " +
            "WHERE d.id = :id OR d.name = :name")
    boolean existsByIdOrName(@Param("id") String id, @Param("name") String name);

    boolean existsByNameAndIdNot(String name, String id);

    @Nonnull
    Optional<Department> findById(@Nonnull String id);

    Department findByName(String name);
}
