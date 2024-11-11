package com.team4.goorm.community.mail.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MailException extends RuntimeException {

	private final HttpStatus status;
	private final String code;

	public MailException(MailErrorCode errorCode) {
		super(errorCode.getMessage());
		this.status = errorCode.getStatus();
		this.code = errorCode.getCode();
	}
}
