package com.team4.goorm.community.Member.dto.response;

import com.team4.goorm.community.Member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProfileSummaryDto {
    @Schema(description = "회원 ID", example = "1")
    private Long memberId;
    @Schema(description = "닉네임", example = "구름")
    private String username;
    @Schema(description = "프로필 이미지 URL", example = "http://goorm.com/image.jpg")
    private String profileImageUrl;

    public ProfileSummaryDto(Long memberId, String username, String profileImageUrl) {
        this.memberId = memberId;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
    }

    public static ProfileSummaryDto from(Member member) {
        return new ProfileSummaryDto(member.getMemberId(), member.getUsername(), member.getProfileImageUrl());
    }
}
