package com.team4.goorm.community.auth.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.team4.goorm.community.auth.domain.OAuth2UserImpl;
import com.team4.goorm.community.auth.dto.response.TokenRespDto;
import com.team4.goorm.community.auth.jwt.utils.JwtUtil;
import com.team4.goorm.community.member.domain.Member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtUtil jwtUtil;

	@Value("${spring.security.oauth2.client.front-redirect-url}")
	private String redirectUrl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws
		IOException, ServletException {

		OAuth2UserImpl principal = (OAuth2UserImpl) authentication.getPrincipal();
		Member member = principal.getMember();

		TokenRespDto tokenRespDto = jwtUtil.issueAccessToken(member.getEmail(), member.getUsername());

		String fullRedirectUrl = UriComponentsBuilder
			.fromUriString(redirectUrl)
			.queryParam("accessToken", tokenRespDto.getAccessToken())
			.build()
			.toUriString();

		response.sendRedirect(fullRedirectUrl);
	}
}