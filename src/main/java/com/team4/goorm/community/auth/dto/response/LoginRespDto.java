package com.team4.goorm.community.auth.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LoginRespDto {

	private String accessToken;

	public LoginRespDto(String accessToken) {
		this.accessToken = accessToken;
	}
}
