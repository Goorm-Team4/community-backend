package com.team4.goorm.community.auth.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

	private final HttpStatus status;
	private final String code;

	public AuthException(AuthErrorCode errorCode) {
		super(errorCode.getMessage());
		this.status = errorCode.getStatus();
		this.code = errorCode.getCode();
	}
}
