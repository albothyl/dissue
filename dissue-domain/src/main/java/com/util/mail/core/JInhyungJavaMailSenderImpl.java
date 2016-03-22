package com.util.mail.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by jinhyung on 2016. 3. 22..
 */
@Component
public class JinhyungJavaMailSenderImpl implements MailSender{

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String senderAddress, String receiverAddress, String title, String body) throws MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setSubject(title);
        helper.setTo(receiverAddress);
        helper.setFrom(senderAddress);
        helper.setText(body, true);
        javaMailSender.send(message);

    }
}
