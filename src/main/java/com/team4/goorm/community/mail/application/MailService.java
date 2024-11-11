package com.team4.goorm.community.mail.application;

import com.team4.goorm.community.mail.exception.MailErrorCode;
import com.team4.goorm.community.mail.exception.MailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String toEmail, String title, String text) {
        try {
            MimeMessage email = createEmail(toEmail, title, text);
            mailSender.send(email);
        } catch (MessagingException e) {
            throw new MailException(MailErrorCode.MAIL_ERROR);
        }
    }

    private MimeMessage createEmail(String toEmail, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(text, true); // html 형식 활성화

        return message;
    }
}
