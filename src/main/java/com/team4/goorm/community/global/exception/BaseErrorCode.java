package com.team4.goorm.community.global.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

	HttpStatus getHttpStatus();
	String getCode();
	String getMessage();
}
