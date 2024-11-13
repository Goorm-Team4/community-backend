package com.team4.goorm.community.Comment.exception;

import org.springframework.http.HttpStatus;

import com.team4.goorm.community.global.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentErrorCode implements BaseErrorCode{
    
        COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_404_0", "존재하지 않는 댓글입니다."),
        COMMENT_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "COMMENT_403_0", "권한이 없습니다.");
        
        private final HttpStatus httpStatus;
        private final String code;
        private final String message;
}
