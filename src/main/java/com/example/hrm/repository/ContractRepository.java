package com.example.hrm.repository;

import com.example.hrm.model.Contract;
import com.example.hrm.projection.ContractProjection;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {
    @Query("SELECT c.id AS id, " +
            "c.employee.id AS employeeId, " +
            "c.employee.name AS employeeName, " +
            "c.note AS note " +
            "FROM Contract c " +
            "WHERE (:signed = true AND c.dateSign IS NOT NULL) " +
            "   OR (:signed = false AND c.dateSign IS NULL) " +
            "   AND (:type IS NULL OR c.typeContract.name = :type)")
    List<ContractProjection> findAllContractsByDateSign(@Param("signed") boolean signed, @Param("type") String type);

//    @Query("SELECT c.id AS id, " +
//            "c.employee.id AS employeeId, " +
//            "c.employee.name AS employeeName, " +
//            "c.typeContract.name AS typeContractName " +
//            "FROM Contract c")
//    List<ContractProjection> findAllContracts();

    @NonNull
    Optional<Contract> findById(@NonNull String id);

    @Query("SELECT c.id AS id, " +
            "c.employee.id AS employeeId, " +
            "c.employee.name AS employeeName, " +
            "c.note AS note " +
            "FROM Contract c " +
            "WHERE (:keyword IS NULL OR " +
            "   LOWER(c.id) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "   LOWER(c.employee.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "   LOWER(c.employee.id) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "   AND (:type IS NULL OR c.typeContract.name = :type)")
    List<ContractProjection> searchContractsBy(@Param("keyword") String keyword,
                                                             @Param("type") String type);



}
