package com.team4.goorm.community.member.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

	private final HttpStatus status;
	private final String code;


	public MemberException(MemberErrorCode errorCode) {
		super(errorCode.getMessage());
		this.status = errorCode.getStatus();
		this.code = errorCode.getCode();
	}

}