package com.example.hrm;

import com.example.hrm.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class EmployeeTest {

    @Autowired
    private EmployeeRepository employeeRepository;

//    @Autowired
//    private EmployeeService employeeService;

    @Test
    public void findByEmailTest(){
        String email = "nguyengocthien200403@gmail.com";
        boolean result = employeeRepository.existsByEmail(email);
        System.out.println(result);
        assertThat(result);
    }

//    private void assertThat(boolean result) {
//        if(result){
//            System.out.println("Yes");
//        }else
//            System.out.println("No");
//    }

//    @Test
//    public void findByIdentificationTest(){
//        String identification = "082203001924";
//        boolean result = employeeRepository.existsByIdentification(identification);
//        assertThat(result);
//
//    }

    @Test
    public void findByPhoneTest(){
        String phone = "0123456789";
        boolean result = employeeRepository.existsByPhone(phone);
        assertThat(result);
    }

    @Test
    public void findByIdTest(){
        String id = "192420112025133247";
        boolean result = employeeRepository.existById(id);
        assertThat(result);
    }

//    @Test
//    public void updateSuccessTest(){
//        String status = "1";
//        String idDepart ="hashfa";
//        String id = "192420112025133247";
//        int update = employeeRepository.updateById(status,idDepart,id);
//        assertThat(update);
//    }



//    @Test
//    public void createTest(){
//        EmployeeDTO dto = new EmployeeDTO();
//        dto.setName("Nguyễn Ngọc THiện");
//        dto.setGender(true);
//        dto.setBirthDate(LocalDate.of(2003,Calendar.APRIL,20));
//        dto.setEmail("nguyenngocthien200403@gmail.com");
//        dto.setNation("Vietnam");
//        dto.setEthnic("Kinh");
//        dto.setPhone("0962419209");
//        dto.setPermanent("Ấp Dương Phú, Xã Tân Hòa, Tỉnh Đồng Tháp");
//        dto.setTempAddress("Chung Cư CC7, Khu 6B, ấp 45, Xã Bình Hưng, Thành phố Hồ Chí Minh");
//        dto.setIdentification("082203001924");
//        dto.setIssuePlace("Cục Cảnh Sát");
//        dto.setIssueDate(LocalDate.of(2021,Calendar.APRIL,21));
//        dto.setHabit("Âm Nhạc, Thể Thao, Du Lịch");
//        dto.setStatusMarital("Độc Thân");
//
//        GeneralResponse<?> response = employeeService.create(dto);
//        System.out.println("Status: " + response.getStatus());
//        System.out.println("Message: " + response.getMsg());
//    }



}
