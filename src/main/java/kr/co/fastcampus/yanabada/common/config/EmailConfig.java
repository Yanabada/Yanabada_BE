package kr.co.fastcampus.yanabada.common.config;

import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Slf4j
@Configuration
public class EmailConfig {

    @Value("${email.host}")
    private String host;
    @Value("${email.port}")
    private int port;
    @Value("${email.user}")
    private String user;
    @Value("${email.password}")
    private String password;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);    //todo: 상수 분리
        mailSender.setUsername(user);     //todo: 상수 분리
        mailSender.setPassword(password);

        Properties javaMailProperties = getMailProperties();
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

    private Properties getMailProperties() {
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties
            .put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.debug", "false");
        javaMailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        return javaMailProperties;
    }
}
