package com.team4.goorm.community.mail.application;

import com.team4.goorm.community.mail.exception.MailErrorCode;
import com.team4.goorm.community.mail.exception.MailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String toEmail, String title, String text) {
        SimpleMailMessage email = createEmail(toEmail, title, text);
        try {
            mailSender.send(email);
        } catch (RuntimeException e) {
            throw new MailException(MailErrorCode.MAIL_ERROR);
        }
    }

    private SimpleMailMessage createEmail(String toEmail, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }
}
