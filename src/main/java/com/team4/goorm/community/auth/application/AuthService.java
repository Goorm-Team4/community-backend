package com.team4.goorm.community.auth.application;

import com.team4.goorm.community.auth.dto.request.LoginReqDto;
import com.team4.goorm.community.auth.dto.request.SignupReqDto;
import com.team4.goorm.community.auth.dto.response.LoginRespDto;
import com.team4.goorm.community.auth.dto.response.MailVerificationRespDto;
import com.team4.goorm.community.auth.dto.response.TokenRespDto;
import com.team4.goorm.community.auth.exception.AuthErrorCode;
import com.team4.goorm.community.auth.exception.AuthException;
import com.team4.goorm.community.auth.jwt.utils.JwtUtil;
import com.team4.goorm.community.auth.presentation.ChangePasswordReqDto;
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
	private static final SecureRandom RANDOM = new SecureRandom();
	private static final String AUTH_CODE_PREFIX = "AUTH_CODE_";
	private static final int AUTH_CODE_LENGTH = 6;
	private static final char[] CHAR_SET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static final int PASSWORD_LENGTH = 12;

	public void signup(SignupReqDto req) {
		Member member = req.toEntity();
		member.setEncryptedPassword(passwordEncoder.encode(req.getPassword()));

		memberRepository.save(member);
	}

	public LoginRespDto login(LoginReqDto req) {
		Member member = memberRepository.findByEmail(req.getEmail())
			.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

		verifyPassword(req.getPassword(), member.getPassword());
		TokenRespDto tokenRespDto = jwtUtil.issueAccessToken(member.getEmail(), member.getUsername());

		return new LoginRespDto(tokenRespDto.getAccessToken());
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

		// 인증코드 redis에 저장
		// -> key: "AUTHCODE_" + email
		// -> value: auth code
		redisUtil.setValue(AUTH_CODE_PREFIX + toEmail,
				authCode,
				codeExpirationTime, TimeUnit.MILLISECONDS);
	}

	public MailVerificationRespDto verifyCode(String email, String authCode) {
		String key = AUTH_CODE_PREFIX + email;
		boolean sucess = false;

		if (redisUtil.hasKey(key)) {
			String redisAuthCode = redisUtil.getValue(key).toString();
			sucess = redisAuthCode.equals(authCode);

			// redis에 저장된 인증코드 삭제
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

	public void sendTempPassword(String email) {
		Member member = memberRepository.findByEmail(email)
				.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

		String subject = "[Community] 임시 비밀번호 안내 메일입니다.";
		String tempPassword = generateTempPassword();
		String content = "<p>안녕하세요. Community 임시비밀번호 안내 메일 입니다.</p>" +
				"<p>로그인 후에 비밀번호를 변경해주세요.</p>" +
				"<p><strong>임시 비밀번호: " + tempPassword + "</strong></p>";

		member.setEncryptedPassword(passwordEncoder.encode(tempPassword));

		mailService.sendEmail(email, subject, content);
	}

	public void changePassword(ChangePasswordReqDto request, String email) {
		Member member = memberRepository.findByEmail(email)
				.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

		verifyPassword(request.getCurrentPassword(), member.getPassword());

		// 새로운 비밀번호로 변경
		member.setEncryptedPassword(request.getNewPassword());
	}

	private void validateEmail(String email) {
		if (memberRepository.existsByEmail(email)) {
			throw new AuthException(AuthErrorCode.EMAIL_ALREADY_EXISTS);
		}
	}

	private void verifyPassword(String rawPassword, String encodedPassword) {
		if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
			throw new AuthException(AuthErrorCode.INVALID_CREDENTIALS);
		}
	}

	/**
	 * 랜덤 인증코드 생성
	 */
	private String generateAuthCode() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < AUTH_CODE_LENGTH; i++) {
			builder.append(RANDOM.nextInt(10));
		}
		return builder.toString();
	}

	/**
	 * 랜덤 임시 비밀번호 생성
	 */
	public static String generateTempPassword(){
		StringBuilder str = new StringBuilder(PASSWORD_LENGTH);
		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			int idx = RANDOM.nextInt(CHAR_SET.length);
			str.append(CHAR_SET[idx]);
		}
		return str.toString();
	}
}