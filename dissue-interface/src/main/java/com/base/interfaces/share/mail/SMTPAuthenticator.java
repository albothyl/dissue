package com.base.interfaces.share.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

@Component
public class SMTPAuthenticator extends Authenticator {
	@Value("${mail.smtp.authenticator.username}")
	private String username;
	@Value("${mail.smtp.authenticator.password}")
	private String password;

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}
}
