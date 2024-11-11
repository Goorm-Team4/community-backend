package com.team4.goorm.community.mail.config;

import com.team4.goorm.community.mail.utils.MailProperties;
import com.team4.goorm.community.mail.utils.MailProperties.Properties.Mail.Smtp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

import static com.team4.goorm.community.mail.utils.MailProperties.*;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MailConfig {

    private final MailProperties mailProperties;
    
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setJavaMailProperties(getMailProperties());

        return mailSender;
    }

    private Properties getMailProperties() {
        Smtp smtp = mailProperties.getProperties().getMail().getSmtp();
        Properties properties = new Properties();
        properties.put(AUTH, smtp.isAuth());
        properties.put(STARTTLS_ENABLE, smtp.getStarttls().isEnable());
        properties.put(STARTTLS_REQUIRED, smtp.getStarttls().isRequired());
        properties.put(CONNECTIONTIMEOUT, smtp.getConnectiontimeout());
        properties.put(TIMEOUT, smtp.getTimeout());
        properties.put(WRITETIMEOUT, smtp.getWritetimeout());

        return properties;
    }
}
