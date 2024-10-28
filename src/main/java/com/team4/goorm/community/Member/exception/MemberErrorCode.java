package com.team4.goorm.community.Member.exception;

import org.springframework.http.HttpStatus;

import com.team4.goorm.community.common.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_404_0", "존재하지 않는 사용자입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
