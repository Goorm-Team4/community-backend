package com.team4.goorm.community.Comment.exception;

import org.springframework.http.HttpStatus;

import com.team4.goorm.community.global.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ReplyErrorCode implements BaseErrorCode {
        
    
    REPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "Reply_404_0", "존재하지 않는 답글입니다."),
    REPLY_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "Reply_403_0", "권한이 없습니다.");
    
    private final HttpStatus status;
    private final String code;
    private final String message;
}
