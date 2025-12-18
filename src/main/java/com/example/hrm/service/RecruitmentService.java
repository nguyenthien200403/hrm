package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.RecruitmentDTO;
import com.example.hrm.model.Employee;
import com.example.hrm.model.Recruitment;
import com.example.hrm.repository.RecruitmentRepository;

import com.example.hrm.sendEmail.EmailDetails;
import com.example.hrm.sendEmail.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final EmailService emailService;


    private static final Logger logger = LoggerFactory.getLogger(RecruitmentService.class);

    public GeneralResponse<?> verifyEmail(String email){
        Optional<Recruitment> findResult = recruitmentRepository.findByEmail(email);
        if(findResult.isPresent()){
            Recruitment recruitment = findResult.get();
            LocalDate oneWeekLater = LocalDate.now().plusDays(7);
            LocalDate recruitmentDate = recruitment.getDate();
            if(!recruitment.getStatus() || !recruitmentDate.isAfter(oneWeekLater)){
                return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"Email Not Found",null);
            }
            RecruitmentDTO dto = new RecruitmentDTO(recruitment);
            return new GeneralResponse<>(HttpStatus.OK.value(), "Verification Success", dto);
        }
        return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found Email", null);
    }


    public GeneralResponse<?> create(RecruitmentDTO request){
        Optional<Recruitment> findResult = recruitmentRepository.findByEmail(request.getEmail());
        if(findResult.isPresent()){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(),"Email existed",null);
        }
        try{
           var recruitment = Recruitment.builder()
                   .email(request.getEmail())
                   .name(request.getName())
                   .build();

            recruitmentRepository.save(recruitment);
            sendRecruitment(recruitment);
            return new GeneralResponse<>(HttpStatus.CREATED.value(),"Success", recruitment);

        }catch (DataIntegrityViolationException e){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(),"Email existed",null);
        }catch (Exception e) {
            logger.error("Error when create recruitment: ", e);
            return new GeneralResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error occurred",null);
        }
    }

    private void sendRecruitment(Recruitment recruitment){
        String subject = "THÔNG BÁO KẾT QUẢ PHỎNG VẤN";
        String body = "Công ty StuTeach xin chân thành cảm ơn bạn đã tham gia buổi phỏng vấn vừa qua.\n" +
                "\n" +
                "Sau quá trình đánh giá, chúng tôi rất vui mừng thông báo rằng bạn đã vượt qua vòng phỏng vấn và chính thức được lựa chọn .\n" +
                " Chúng tôi tin rằng với năng lực và tinh thần làm việc của bạn, bạn sẽ đóng góp tích cực cho sự phát triển của công ty.\n" +
                "\n" +
                "Bạn hãy truy cập đường link bên dưới để hoàn tất thủ thục gia nhập.\n" +
                "\n" +
                "Trân trọng, \n" +
                "StuTeach";

        var emailDetails = EmailDetails.builder()
                .recipient(recruitment.getEmail())
                .subject(subject)
                .msgBody(body)
                .build();

        emailService.sendSimpleMail(emailDetails);
    }
}
