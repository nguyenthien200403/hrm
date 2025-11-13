package com.example.hrm.repository;

import com.example.hrm.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  AccountRepository extends JpaRepository<Account, Long> {
    //Optional<Account> findByNameAcount(String nameAccount);
}
