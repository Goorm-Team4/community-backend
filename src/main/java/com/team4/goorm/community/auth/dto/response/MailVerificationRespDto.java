package com.team4.goorm.community.auth.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailVerificationRespDto {

    private boolean isVerificationSuccess;
    private String message;

    @Builder
    public MailVerificationRespDto(boolean isVerificationSuccess, String message) {
        this.isVerificationSuccess = isVerificationSuccess;
        this.message = message;
    }
}
