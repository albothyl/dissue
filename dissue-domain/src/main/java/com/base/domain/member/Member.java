package com.base.domain.member;

import com.base.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Member extends AbstractEntity {
	private String email;
	private String password;
	private String nickName;
	private DateTime updatedAt;
	private DateTime registedAt;
}
