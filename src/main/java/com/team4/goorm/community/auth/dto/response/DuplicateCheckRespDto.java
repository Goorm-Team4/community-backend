package com.team4.goorm.community.auth.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DuplicateCheckRespDto {
    private boolean duplicate;
    private String message;

    @Builder
    public DuplicateCheckRespDto(boolean duplicate, String message) {
        this.duplicate = duplicate;
        this.message = message;
    }
}
