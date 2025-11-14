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
public class AccountRepositoryTests {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void testFindByNameAccount(){
        String name = "CT56789";
        Optional<Account> findResult = accountRepository.findByNameAccount(name);
        assertThat(findResult).isPresent();
    }
}
