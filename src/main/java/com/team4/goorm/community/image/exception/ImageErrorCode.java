package com.team4.goorm.community.image.exception;

import com.team4.goorm.community.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ImageErrorCode implements BaseErrorCode {

    FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "IMAGE_500_0", "이미지 업로드에 실패하였습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
