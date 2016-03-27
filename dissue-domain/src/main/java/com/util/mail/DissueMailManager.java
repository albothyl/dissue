package com.util.mail;

import com.util.mail.core.JinhyungJavaMailSenderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.Optional;

/**
 * Created by jinhyung on 2016. 3. 22..
 */
@Component
@ComponentScan
public class DissueMailManager {

    private static final String DEFAULT_DEHAUS_MAIL_ADDRESS = "haneu89@naver.com";

    @Autowired
    JinhyungJavaMailSenderImpl sender;

    public Optional<String> sendToUs(String from, String subject, String text) {
        return sendMail(from, DEFAULT_DEHAUS_MAIL_ADDRESS,  subject, text);
    }

    public Optional<String> sendFromUs(String to, String subject, String text) {
        return sendMail(DEFAULT_DEHAUS_MAIL_ADDRESS, to, subject, text);
    }

    private Optional<String> sendMail(String from, String to, String subject, String body) {
        try {
            sender.sendEmail(from, to, subject, body);
        } catch (MessagingException e) {
            return Optional.empty();
        }
        return Optional.of("success");
    }
}
