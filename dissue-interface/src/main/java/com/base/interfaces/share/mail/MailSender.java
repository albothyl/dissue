package com.base.interfaces.share.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import static javax.mail.Message.RecipientType.TO;

@Component
public class MailSender {
	
	@Autowired
	private SMTPAuthenticator smtpAuthenticator;

	public boolean send(Mail mail) {
				
		Session mailSession = Session.getDefaultInstance(getSMTPProperties(), smtpAuthenticator);
	
		Message msg = new MimeMessage(mailSession);
		try {
			msg.setFrom(new InternetAddress(mail.getSender()));
			msg.setRecipients(TO, InternetAddress.parse(mail.getReceiver(), false));
			msg.setSubject(mail.getSubject());
			msg.setText(mail.getContent());
			msg.setSentDate(new Date());

			Transport.send(msg);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private Properties getSMTPProperties() {
		Properties properties = new Properties();
		// 프로토콜 설정
		properties.put("mail.transport.protocol", "smtp");
		// gmail SMTP 서비스 주소(호스트)
		properties.put("mail.smtp.host", "smtp.gmail.com");
		// gmail SMTP 서비스 포트 설정
		properties.put("mail.smtp.port", "465");
		// 로그인 할때 Transport Layer Security(TLS)를 사용할 것인지 설정
		properties.put("mail.smtp.starttls.enable","true");
		// gmail 인증용 Secure Socket Layer(SSL) 설정
		properties.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		// SMTP 인증을 설정
		properties.put("mail.smtp.auth", "true");

		return properties;
	}

}
