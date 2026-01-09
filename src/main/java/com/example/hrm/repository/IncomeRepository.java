package com.example.hrm.repository;


import com.example.hrm.model.Income;
import com.example.hrm.projection.IncomeProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, String> {

    @Transactional(readOnly = true)
    @Procedure(procedureName = "sp_FindAllIncomeByEmployeeId")
    List<IncomeProjection> findAllByEmployee(@Param("employeeId") String employeeId);

    @Transactional
    @Procedure(procedureName = "sp_FindAllIncomeByDepartmentName")
    List<Income> findAllByDepartmentName(@Param("departmentName")String nameDepart,
                                         @Param("month") int month,
                                         @Param("year")int year);
}
