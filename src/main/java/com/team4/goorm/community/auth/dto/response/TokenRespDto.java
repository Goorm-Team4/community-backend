package com.team4.goorm.community.auth.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TokenRespDto {
	private String accessToken;

	@Builder
	public TokenRespDto(String email, String username, String accessToken) {
		this.accessToken = accessToken;
	}
}
