package com.team4.goorm.community.auth.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TokenRespDto {
	private String email;
	private String username;
	private String accessToken;

	@Builder
	public TokenRespDto(String email, String username, String accessToken) {
		this.email = email;
		this.username = username;
		this.accessToken = accessToken;
	}
}
