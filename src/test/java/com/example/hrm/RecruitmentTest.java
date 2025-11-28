package com.example.hrm;

import com.example.hrm.model.Recruitment;
import com.example.hrm.repository.RecruitmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.test.annotation.Rollback;



import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RecruitmentTest {

    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Test
    public void createTest(){
        Recruitment recruitment = new Recruitment();
        recruitment.setEmail("nguyenngocthien200403@gmail.com");
        recruitment.setName("Nguyễn Ngọc Thiện");

        assertThat(recruitmentRepository.save(recruitment));
    }

    @Test
    public void getRecruitmentTest(){
        List<Recruitment> ls = recruitmentRepository.findAll();
        ls.forEach(System.out :: println);
        assertThat(ls);
    }

    @Test
    public void findByEmail(){
        String email = "buiminhphuc@gmail.com";
        Optional<Recruitment> findResult = recruitmentRepository.findByEmail(email);
        findResult.ifPresent(System.out::println);
        assertThat(findResult).isPresent();
    }

    @Test
    public void updateByEmailSuccessTest(){
        Boolean status = false;
        String email = "nguyenngoc@gmail.com";
        int updated = recruitmentRepository.updateByEmail(email, status);
        assertThat(updated);
    }
}
