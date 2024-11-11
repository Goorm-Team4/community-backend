package com.team4.goorm.community.auth.application;

import com.team4.goorm.community.auth.dto.request.LoginReqDto;
import com.team4.goorm.community.auth.dto.request.SignupReqDto;
import com.team4.goorm.community.auth.dto.response.LoginRespDto;
import com.team4.goorm.community.auth.dto.response.MailVerificationRespDto;
import com.team4.goorm.community.auth.dto.response.TokenRespDto;
import com.team4.goorm.community.auth.exception.AuthErrorCode;
import com.team4.goorm.community.auth.exception.AuthException;
import com.team4.goorm.community.auth.jwt.utils.JwtUtil;
import com.team4.goorm.community.global.utils.RedisUtil;
import com.team4.goorm.community.mail.application.MailService;
import com.team4.goorm.community.member.domain.Member;
import com.team4.goorm.community.member.exception.MemberErrorCode;
import com.team4.goorm.community.member.exception.MemberException;
import com.team4.goorm.community.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

	private final MemberRepository memberRepository;
	private final MailService mailService;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;

	@Value("${spring.mail.code-expiration-time}")
	private long codeExpirationTime;

	private static final String AUTH_CODE_PREFIX = "AUTH_CODE_";
	private static final int AUTH_CODE_LENGTH = 6;

	public void signup(SignupReqDto req) {
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

	@Transactional(readOnly = true)
	public void sendVerificationEmail(String toEmail) {
		validateEmail(toEmail);

		String subject = "[Community] 회원가입 인증 메일입니다.";
		String authCode = generateAuthCode();
		String content = "<p>안녕하세요. Community 회원가입 인증 메일입니다.</p>" +
				"<p>아래의 인증 코드를 입력해 주세요:</p>" +
				"<p><strong>인증코드: " + authCode + "</strong></p>";

		mailService.sendEmail(toEmail, subject, content);

		// 이메일 인증 요청 시 인증 번호 Redis에 저장 (key = "AUTHCODE_" + Email/value = AuthCode)
		redisUtil.setValue(AUTH_CODE_PREFIX + toEmail,
				authCode,
				codeExpirationTime, TimeUnit.MILLISECONDS);
	}

	private String createRandomCode() {
		Random random = new SecureRandom();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < AUTH_CODE_LENGTH; i++) {
			builder.append(random.nextInt(10));
		}
		return builder.toString();
	}

	public MailVerificationRespDto verifyCode(String email, String authCode) {
		String key = AUTH_CODE_PREFIX + email;
		boolean sucess = false;

		if (redisUtil.hasKey(key)) {
			String redisAuthCode = redisUtil.getValue(key).toString();
			sucess = redisAuthCode.equals(authCode);

			redisUtil.deleteValue(key);
		}

		if (sucess) {
			return MailVerificationRespDto.builder()
					.isVerificationSuccess(true)
					.message("이메일 인증에 성공하였습니다.")
					.build();
		} else {
			return MailVerificationRespDto.builder()
					.isVerificationSuccess(false)
					.message("이메일 인증에 실패하였습니다.")
					.build();
		}
	}
}