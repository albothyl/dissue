package com.base.interfaces.share.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "create")
public class Mail {
	private final String sender;
	private final String receiver;
	private final String subject;
	private final String content;
}
