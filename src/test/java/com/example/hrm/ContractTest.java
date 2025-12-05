package com.example.hrm;


import com.example.hrm.projection.ContractProjection;
import com.example.hrm.repository.ContractRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ContractTest {
    @Autowired
    private ContractRepository contractRepository;

//    @Test
//    public void findAllContracts(){
//        List<ContractProjection> list = contractRepository.findAllContracts(false);
//        assertThat(list);
//    }
}
