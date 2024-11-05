package com.team4.goorm.community.post.exception;

import org.springframework.http.HttpStatus;

import com.team4.goorm.community.global.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostErrorCode implements BaseErrorCode {

	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_404_0", "존재하지 않는 게시글입니다."),
	CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_404_1", "존재하지 않는 카테고리입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
