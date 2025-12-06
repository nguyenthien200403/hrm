package com.example.hrm.repository;

import com.example.hrm.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNameAccount(String nameAccount);

    boolean existsByNameAccountAndIdNot(String nameAccount,Long id);

    Optional<Account> findByEmployeeEmail(String email);


}
