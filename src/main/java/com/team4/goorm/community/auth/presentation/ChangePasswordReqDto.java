package com.team4.goorm.community.auth.presentation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangePasswordReqDto {

    private String currentPassword;
    private String newPassword;
}
