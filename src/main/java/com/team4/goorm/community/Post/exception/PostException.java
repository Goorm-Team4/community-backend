package com.team4.goorm.community.post.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class PostException extends RuntimeException {

	private final HttpStatus status;
	private final String code;

	public PostException(PostErrorCode errorCode) {
		super(errorCode.getMessage());
		this.status = errorCode.getStatus();
		this.code = errorCode.getCode();
	}
}
