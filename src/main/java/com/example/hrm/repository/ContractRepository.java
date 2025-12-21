package com.example.hrm.repository;

import com.example.hrm.model.Contract;
import com.example.hrm.projection.ContractProjection;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {
    @Transactional(readOnly = true)
    @Procedure(procedureName = "sp_FindAllByDateSignAndType")
    List<ContractProjection> findAllByDateSignAndType(@Param("signed") boolean signed,
                                                      @Param("type") String type);



    @NonNull
    Optional<Contract> findById(@NonNull String id);

    @Query("SELECT c.id AS id, " +
            "c.typeContract.name AS typeContractName, " +
            "c.employee.id AS employeeId, " +
            "c.employee.name AS employeeName, " +
            "c.position AS position " +
            "FROM Contract c " +
            "WHERE (:keyword IS NULL OR " +
            "   LOWER(c.id) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "   LOWER(c.employee.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "   LOWER(c.employee.id) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "   AND (:type IS NULL OR c.typeContract.name = :type)")
    List<ContractProjection> searchContractsBy(@Param("keyword") String keyword,
                                               @Param("type") String type);

    @Query("SELECT c.id AS id, " +
            "c.typeContract.name AS typeContractName, " +
            "c.employee.id AS employeeId, " +
            "c.employee.name AS employeeName, " +
            "c.position AS position " +
            "FROM Contract c " +
            "WHERE c.employee.id = :idEmployee" )
    List<ContractProjection> findAllByEmployeeId(@Param("idEmployee") String idEmployee);

    @Query(value = "SELECT dbo.fn_CheckContractResign(:employeeId, :nameType)", nativeQuery = true)
    int checkContractResign(@Param("employeeId") String employeeId,
                            @Param("nameType") String nameTye);


}
