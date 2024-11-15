package com.team4.goorm.community.auth.dto.request;

import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Member.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.team4.goorm.community.Member.domain.SocialType.LOCAL;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SignupReqDto {

	@Schema(description = "이메일", example = "test@test.ac.kr")
	private String email;
	@Schema(description = "닉네임", example = "구름")
	private String username;
	@Schema(description = "비밀번호", example = "test1234!!")
	private String password;
	@Schema(description = "프로필 이미지 URL")
	private String profileImageUrl;

	public Member toEntity() {
		return Member.builder()
				.email(email)
				.role(Role.ROLE_USER)
				.socialType(LOCAL)
				.username(username)
				.profileImageUrl(profileImageUrl)
				.build();
	}

	public Member toEntity(String profileImageUrl) {
		return Member.builder()
				.email(email)
				.role(Role.ROLE_USER)
				.socialType(LOCAL)
				.username(username)
				.profileImageUrl(profileImageUrl)
				.build();
	}
}