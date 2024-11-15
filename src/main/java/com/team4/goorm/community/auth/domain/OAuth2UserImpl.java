package com.team4.goorm.community.auth.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.team4.goorm.community.Member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuth2UserImpl implements OAuth2User, Serializable {

	private Map<String, Object> attributes;
	private Member member;

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getName() {
		return member.getUsername();
	}
}
