package com.team4.goorm.community.Comment.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ReplyException extends RuntimeException {
    private final HttpStatus status;
    private final String code;

    public ReplyException(ReplyErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
    }
}
