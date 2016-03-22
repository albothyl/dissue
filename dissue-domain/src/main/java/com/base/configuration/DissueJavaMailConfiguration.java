package com.base.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by jinhyung on 2016. 3. 22..
 */
@Configuration
public class DissueJavaMailConfiguration {

    @Bean
    MailSender mailSender() {

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.ssl.enable", "true");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername("dissue.test@gmail.com");
        javaMailSender.setPassword("kjh65443818");
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(465);
        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }
}
