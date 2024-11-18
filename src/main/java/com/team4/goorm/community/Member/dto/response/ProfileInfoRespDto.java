package com.team4.goorm.community.Member.dto.response;

import com.team4.goorm.community.Member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "프로필 정보 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileInfoRespDto {

    @Schema(description = "회원 ID", example = "1")
    private Long memberId;
    @Schema(description = "이메일", example = "test@teat.ac.kr")
    private String email;
    @Schema(description = "닉네임", example = "구름")
    private String username;
    @Schema(description = "프로필 이미지 URL", example = "http://goorm.com/profile.jpg")
    private String profileImageUrl;

    @Builder
    public ProfileInfoRespDto(Long memberId, String email, String username, String profileImageUrl) {
        this.memberId = memberId;
        this.email = email;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
    }

    public static ProfileInfoRespDto from(Member member) {
        return ProfileInfoRespDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .username(member.getUsername())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }
}
