package com.team4.goorm.community.auth.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team4.goorm.community.auth.dto.request.LoginReqDto;
import com.team4.goorm.community.auth.dto.request.SignupReqDto;
import com.team4.goorm.community.auth.dto.response.LoginRespDto;
import com.team4.goorm.community.auth.dto.response.TokenRespDto;
import com.team4.goorm.community.auth.exception.AuthErrorCode;
import com.team4.goorm.community.auth.exception.AuthException;
import com.team4.goorm.community.auth.jwt.utils.JwtUtil;
import com.team4.goorm.community.member.domain.Member;
import com.team4.goorm.community.member.exception.MemberErrorCode;
import com.team4.goorm.community.member.exception.MemberException;
import com.team4.goorm.community.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public void signup(SignupReqDto req) {
		validateEmail(req.getEmail());

		Member member = req.toEntity();
		member.setEncryptedPassword(passwordEncoder.encode(req.getPassword()));

		memberRepository.save(member);
	}

	private void validateEmail(String email) {
		if (memberRepository.existsByEmail(email)) {
			throw new AuthException(AuthErrorCode.EMAIL_ALREADY_EXISTS);
		}
	}

	public LoginRespDto login(LoginReqDto req) {
		Member member = memberRepository.findByEmail(req.getEmail())
			.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

		verifyPassword(req.getPassword(), member.getPassword());
		TokenRespDto tokenRespDto = jwtUtil.issueAccessToken(member.getEmail(), member.getUsername());

		return new LoginRespDto(tokenRespDto.getAccessToken());
	}

	private void verifyPassword(String rawPassword, String encodedPassword) {
		if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
			throw new AuthException(AuthErrorCode.INVALID_CREDENTIALS);
		}
	}
}