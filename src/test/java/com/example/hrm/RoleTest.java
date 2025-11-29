package com.example.hrm;

import com.example.hrm.model.Role;
import com.example.hrm.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleTest{
    @Autowired
    private  RoleRepository roleRepository;

    @Test
    public void findByName(){
        String nameRole = "admin";
        Optional<Role> findResult = roleRepository.findByNameRole(nameRole);
        findResult.ifPresent(System.out :: println);
        assertThat(findResult).isPresent();
    }
}
