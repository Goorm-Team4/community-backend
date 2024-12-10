package com.team4.goorm.community.Post.exception;

import org.springframework.http.HttpStatus;

import com.team4.goorm.community.global.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostErrorCode implements BaseErrorCode {

	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_404_0", "존재하지 않는 게시글입니다."),
	CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_404_1", "존재하지 않는 카테고리입니다."),
	POST_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "POST_403_0", "게시글 작성자만 수정, 삭제할 수 있습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;
}
