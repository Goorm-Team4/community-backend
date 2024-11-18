package com.team4.goorm.community.Comment.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CommentException extends RuntimeException {

    private final HttpStatus status;
    private final String code;

    public CommentException(CommentErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
    }

}
