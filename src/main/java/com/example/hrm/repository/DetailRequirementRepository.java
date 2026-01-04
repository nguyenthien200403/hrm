package com.example.hrm.repository;

import com.example.hrm.model.DetailRequirement;

import com.example.hrm.projection.DetailRequirementProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Optional;

public interface DetailRequirementRepository extends JpaRepository<DetailRequirement,Long> {

    @Transactional(readOnly = true)
    @Procedure(procedureName = "sp_FindAllDetailRequirementWithStatusAndDateConfirm")
    List<DetailRequirementProjection> findAllDetailRequirementWithStatusAndDateConfirm();



    @Transactional(readOnly = true)
    @Procedure(procedureName = "sp_FindAllDetailRequirementByEmployee")
    List<DetailRequirement> findAllDetailRequirementByEmployee(@Param("employeeId") String id,
                                                  @Param("status") String status,
                                                  @Param("nameType") String type,
                                                  @Param("month") int month,
                                                  @Param("year") int year);



    @Transactional(readOnly = true)
    @Procedure(procedureName = "sp_FindAllDetailRequirementByManager")
    List<DetailRequirement> findAllDetailRequirementByManager(@Param("employeeId") String id,
                                                               @Param("status") String status,
                                                               @Param("nameType") String type,
                                                               @Param("month") int month,
                                                               @Param("year") int year);


    @Transactional(readOnly = true)
    @Procedure(procedureName = "sp_FindAllDetailRequirementByAdmin")
    List<DetailRequirement> findAllDetailRequirementByAdmin(@Param("employeeId") String id,
                                                              @Param("status") String status,
                                                              @Param("nameType") String type,
                                                              @Param("month") int month,
                                                              @Param("year") int year);
}
