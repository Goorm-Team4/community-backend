package com.team4.goorm.community.auth.application;

import com.team4.goorm.community.auth.dto.request.LoginReqDto;
import com.team4.goorm.community.auth.dto.request.SignupReqDto;
import com.team4.goorm.community.auth.dto.response.DuplicateCheckRespDto;
import com.team4.goorm.community.auth.dto.response.LoginRespDto;
import com.team4.goorm.community.auth.dto.response.MailVerificationRespDto;
import com.team4.goorm.community.auth.dto.response.TokenRespDto;
import com.team4.goorm.community.auth.exception.AuthErrorCode;
import com.team4.goorm.community.auth.exception.AuthException;
import com.team4.goorm.community.auth.jwt.utils.JwtUtil;
import com.team4.goorm.community.auth.presentation.ChangePasswordReqDto;
import com.team4.goorm.community.global.utils.RedisUtil;
import com.team4.goorm.community.image.service.AmazonS3Service;
import com.team4.goorm.community.mail.application.MailService;
import com.team4.goorm.community.member.application.MemberQueryService;
import com.team4.goorm.community.member.domain.Member;
import com.team4.goorm.community.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

	private final MemberQueryService memberQueryService;
	private final MemberRepository memberRepository;
	private final MailService mailService;
	private final AmazonS3Service amazonS3Service;
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

	public void signup(SignupReqDto request, MultipartFile profileImage) {
		// s3에 이미지 업로드
		String imageUrl = amazonS3Service.uploadImage(profileImage);
		Member member = request.toEntity(imageUrl);
		member.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));

		memberRepository.save(member);
	}

	public LoginRespDto login(LoginReqDto request) {
		Member member = memberQueryService.findMemberByEmail(request.getEmail());

		verifyPassword(request.getPassword(), member.getPassword());
		TokenRespDto tokenRespDto = jwtUtil.issueAccessToken(member.getEmail(), member.getUsername());

		return new LoginRespDto(tokenRespDto.getAccessToken());
	}

	@Transactional(readOnly = true)
	public void sendVerificationEmail(String toEmail) {
		memberQueryService.validateUniqueEmail(toEmail);

		String subject = "[Community] 회원가입 인증 메일입니다.";
		String authCode = generateAuthCode();
		String content = "<p>안녕하세요. Community 회원가입 인증 메일입니다.</p>" +
				"<p>아래의 인증코드를 입력해 주세요:</p>" +
				"<p><strong>인증코드: " + authCode + "</strong></p>";

		mailService.sendEmail(toEmail, subject, content);

		// 인증코드 redis에 저장
		// -> key: "AUTHCODE_" + email
		// -> value: auth code
		redisUtil.setValue(AUTH_CODE_PREFIX + toEmail,
				authCode,
				codeExpirationTime, TimeUnit.MILLISECONDS);
	}

	public MailVerificationRespDto verifyAuthCode(String email, String authCode) {
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
					.success(true)
					.message("이메일 인증에 성공하였습니다.")
					.build();
		} else {
			return MailVerificationRespDto.builder()
					.success(false)
					.message("이메일 인증에 실패하였습니다.")
					.build();
		}
	}

	public void sendTempPassword(String email) {
		Member member = memberQueryService.findMemberByEmail(email);

		String subject = "[Community] 임시 비밀번호 안내 메일입니다.";
		String tempPassword = generateTempPassword();
		String content = "<p>안녕하세요. Community 임시 비밀번호 안내 메일입니다.</p>" +
				"<p>로그인 후에 비밀번호를 변경해주세요.</p>" +
				"<p><strong>임시 비밀번호: " + tempPassword + "</strong></p>";

		member.setEncryptedPassword(passwordEncoder.encode(tempPassword));

		mailService.sendEmail(email, subject, content);
	}

	public void changePassword(ChangePasswordReqDto request, String email) {
		Member member = memberQueryService.findMemberByEmail(email);

		verifyPassword(request.getCurrentPassword(), member.getPassword());

		// 새로운 비밀번호로 변경
		member.setEncryptedPassword(request.getNewPassword());
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

	@Transactional(readOnly = true)
	public DuplicateCheckRespDto checkUsernameDuplicate(String username) {
		if (memberQueryService.existsByUsername(username)) {
			return DuplicateCheckRespDto.builder()
					.duplicate(true)
					.message("이미 사용중인 닉네임입니다.")
					.build();
		} else {
			return DuplicateCheckRespDto.builder()
					.duplicate(false)
					.message("사용 가능한 닉네임입니다.")
					.build();
		}

	}
}