package com.team4.goorm.community.common.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

	HttpStatus getHttpStatus();
	String getCode();
	String getMessage();
}
