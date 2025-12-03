package com.example.hrm.sendEmail;


public interface EmailService {
    void sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);

}
