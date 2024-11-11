package com.team4.goorm.community.image.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ImageException extends RuntimeException {

    private HttpStatus status;
    private String code;

    public ImageException(ImageErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
    }
}
