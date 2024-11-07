package com.team4.goorm.community.global.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

	HttpStatus getStatus();
	String getCode();
	String getMessage();
}
