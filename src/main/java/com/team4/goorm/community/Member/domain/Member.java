package com.team4.goorm.community.member.domain;

import com.team4.goorm.community.global.common.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Column(name = "social_id")
	private String socialId;

	@Enumerated(EnumType.STRING)
	private SocialType socialType;

	@Enumerated(EnumType.STRING)
	private Role role;

	private String email;

	@Size(min = 2, max = 20)
	@Column(nullable = false)
	private String username;

	@Size(min = 8, max = 20)
	private String password;

	@Column(name = "profile_image_url")
	private String profileImageUrl;

	@Builder
	public Member(String socialId, SocialType socialType, Role role, String email, String username, String password,
		String profileImageUrl) {
		this.socialId = socialId;
		this.socialType = socialType;
		this.role = role;
		this.email = email;
		this.username = username;
		this.password = password;
		this.profileImageUrl = profileImageUrl;
	}
}
