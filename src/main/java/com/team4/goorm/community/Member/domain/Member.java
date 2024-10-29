package com.team4.goorm.community.Member.domain;

import com.team4.goorm.community.global.common.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Email
	@Column(nullable = false)
	private String email;

	@Size(min = 6, max = 20)
	@Column(nullable = false)
	private String loginId;

	@Size(min = 2, max = 20)
	@Column(nullable = false)
	private String username;

	@Size(min = 8, max = 20)
	@Column(nullable = false)
	private String password;
}
