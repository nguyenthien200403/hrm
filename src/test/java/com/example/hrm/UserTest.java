package com.example.hrm;

import com.example.hrm.model.User;
import com.example.hrm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByNameTest(){
        Optional<User> findResult = userRepository.findByName("nguyenngocthien200403@gmail.com");

        assertThat(findResult).isPresent();
    }
}
