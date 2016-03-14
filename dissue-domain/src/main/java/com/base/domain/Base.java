package com.base.domain;

import com.base.share.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Table(schema = "superboard", name = "base")
@SuppressWarnings({ "PMD.UnusedPrivateField" })
public class Base extends AbstractEntity {
	private String content;
	@CreatedDate
	private DateTime createdAt;

	public static Base create(String content) {
		Base base = new Base();
		base.content = content;

		return base;
	}
}
