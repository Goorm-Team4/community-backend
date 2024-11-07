package com.team4.goorm.community.auth.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {

	@Getter
	private final String email;
	private final String username;
	private final String authority;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add((GrantedAuthority) () -> authority);
		return collection;
	}

	@Override
	public String getPassword() {
		return "";
	}

	@Override
	public String getUsername() {
		return username;
	}
}
