package com.example.hrm.repository;

import com.example.hrm.model.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RequirementRepository extends JpaRepository<Requirement, Long>{

    @Transactional(readOnly = true)
    @Procedure(procedureName = "sp_FindRequirementByType")
    List<Requirement> findRequirementByType(@Param("type") String type);

    boolean existsByName(String name);

    @Query(value = "SELECT  dbo.fn_CheckRequirementHasDetail(:id)", nativeQuery = true)
    boolean checkRequirementHasDetail(@Param("id") Long id);

    boolean existsByNameAndIdNot(String name, Long id);

}
