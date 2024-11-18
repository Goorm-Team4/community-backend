package com.team4.goorm.community.Member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "프로필 수정 요청 DTO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UpdateMemberProfileReqDto {

    @Schema(description = "닉네임", example = "구름")
    private String username;
    @Schema(description = "프로필 이미지 URL", example = "http://goorm.com/profile.jpg")
    private String profileImageUrl;
}
