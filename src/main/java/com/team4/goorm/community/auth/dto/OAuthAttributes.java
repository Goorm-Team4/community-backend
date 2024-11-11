package com.team4.goorm.community.auth.dto;

import static com.team4.goorm.community.member.domain.SocialType.*;

import java.util.Map;

import com.team4.goorm.community.member.domain.SocialType;
import com.team4.goorm.community.member.domain.Member;
import com.team4.goorm.community.member.domain.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

	private Map<String, Object> attributes;
	private String nameAttributesKey;
	private String username;
	private String email;
	private String socialId;
	private SocialType socialType;
	private String profileImageUrl;

	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributesKey, String username, String email,
		String socialId, SocialType socialType, String profileImageUrl) {
		this.attributes = attributes;
		this.nameAttributesKey = nameAttributesKey;
		this.username = username;
		this.email = email;
		this.socialId = socialId;
		this.socialType = socialType;
		this.profileImageUrl = profileImageUrl;
	}

	public static OAuthAttributes of(String socialName, Map<String, Object> attributes) {
		if ("kakao".equals(socialName)) {
			return ofKakao("id", attributes);
		}

		return null;
	}

	private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
		Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> profile = (Map<String, Object>) account.get("profile");

		String socialId = String.valueOf(attributes.get(userNameAttributeName));

		return OAuthAttributes.builder()
			.username(String.valueOf(profile.get("nickname")))
			.profileImageUrl(String.valueOf(profile.get("profile_image_url")))
			.nameAttributesKey(userNameAttributeName)
			.attributes(attributes)
			.socialId(socialId)
			.socialType(KAKAO)
			.email(socialId + "@kakao.com")
			.build();
	}

	public Member toEntity() {
		return Member.builder()
			.username(username)
			.socialId(socialId)
			.socialType(socialType)
			.email(email)
			.profileImageUrl(profileImageUrl)
			.role(Role.ROLE_USER)
			.build();
	}
}
