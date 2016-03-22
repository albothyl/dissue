package com.util.mail.core;

import org.springframework.scheduling.annotation.Async;

import javax.mail.MessagingException;

/**
 * Created by jinhyung on 2016. 3. 22..
 */
public interface MailSender {

    @Async
    public void sendEmail(String senderAddress, String receiverAddress, String title, String body) throws MessagingException;
}
