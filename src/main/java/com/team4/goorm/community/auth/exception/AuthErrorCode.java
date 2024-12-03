package com.team4.goorm.community.auth.exception;

import org.springframework.http.HttpStatus;

import com.team4.goorm.community.global.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

	EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "AUTH_400_0", "이미 가입된 이메일입니다."),
	USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "AUTH_400_1", "이미 사용중인 닉네임입니다."),
	PASSWORD_NOT_CHANGED(HttpStatus.BAD_REQUEST, "AUTH_400_2", "이미 사용중인 비밀번호입니다."),
	INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH_401_0", "잘못된 이메일 또는 비밀번호입니다."),
	;

	private final HttpStatus status;
	private final String code;
	private final String message;
}
