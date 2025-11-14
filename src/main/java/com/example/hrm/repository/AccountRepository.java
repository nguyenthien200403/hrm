package com.example.hrm.repository;

import com.example.hrm.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  AccountRepository extends JpaRepository<Account, Long> {
//    @Query("SELECT a FROM Account a WHERE a.nameAccount = :nameAccount")
//    Optional<Account> findByNameAccount(@Param("nameAccount") String nameAccount);

    Optional<Account> findByNameAccount(String nameAccount);

}
