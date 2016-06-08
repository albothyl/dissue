package com.base.domain.security;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Authority {
	@Id
	private String email;
	@Enumerated(EnumType.STRING)
	private AuthorityType authority;
}
