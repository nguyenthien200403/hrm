package com.example.hrm;


import com.example.hrm.model.Account;
import com.example.hrm.repository.AccountRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;


import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class AccountTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void findByNameAccountTest(){
        String nameAccount = "nguyenngocthien200403@gmail.com";
        Optional<Account> findResult = accountRepository.findByNameAccount(nameAccount);
        assertThat(findResult).isPresent();
    }

    @Test
    public void findByEmployeeEmailTest(){
        String email = "nguyenngocthien200403@gmail.com";
        Optional<Account> findResult = accountRepository.findByEmployeeEmail(email);
        assertThat(findResult).isPresent();

    }
}
