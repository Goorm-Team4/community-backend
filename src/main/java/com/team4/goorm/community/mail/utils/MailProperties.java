package com.team4.goorm.community.mail.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
@ConfigurationProperties("spring.mail")
public class MailProperties {

    private String host;
    private int port;
    private String username;
    private String password;
    private Properties properties = new Properties();
    public static final String AUTH = "mail.smtp.auth";
    public static final String STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String STARTTLS_REQUIRED = "mail.smtp.starttls.required";
    public static final String CONNECTIONTIMEOUT = "mail.smtp.connectiontimeout";
    public static final String TIMEOUT = "mail.smtp.timeout";
    public static final String WRITETIMEOUT = "mail.smtp.writetimeout";

    @Getter @Setter
    public static class Properties {
        private Mail mail = new Mail();

        @Getter @Setter
        public static class Mail {
            private Smtp smtp = new Smtp();

            @Getter @Setter
            public static class Smtp {
                private boolean auth;
                private Starttls starttls = new Starttls();
                private int connectiontimeout;
                private int timeout;
                private int writetimeout;

                @Getter @Setter
                public static class Starttls {
                    private boolean enable;
                    private boolean required;
                }
            }
        }
    }
}