package com.team4.goorm.community.auth.exception;

import org.springframework.http.HttpStatus;

import com.team4.goorm.community.global.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtErrorCode implements BaseErrorCode {

	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT_401_0", "토큰이 만료되었습니다."),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "JWT_401_1", "잘못된 토큰입니다."),
	UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "JWT_401_2", "사용자 인증이 필요합니다."),
	ACCESS_DENIED(HttpStatus.FORBIDDEN, "JWT_403_0", "해당 요청에 대한 접근 권한이 없습니다."),
	JWT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "JWT_500_0", "JWT 에러가 발생하였습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;
}
