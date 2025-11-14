package com.example.hrm;

import com.example.hrm.model.InviteEmployee;
import com.example.hrm.repository.InviteEmpRepository;
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
public class InviteEmplRepositoryTest {

    @Autowired
    private InviteEmpRepository inviteEmpRepository;

    @Test
    public void testFindByEmail(){
        String email = "nguyenngocthien200403@gmail.com";
        Optional<InviteEmployee> findResult = inviteEmpRepository.findByEmail(email);
        assertThat(findResult).isPresent();
    }

    @Test
    public void testCreate(){
        InviteEmployee inviteEmployee = new InviteEmployee();
        inviteEmployee.setEmail("nguyenvana@gmail.com");
        inviteEmployee.setName("Nguyen Van A");


        assertThat(inviteEmpRepository.save(inviteEmployee));
    }
}
