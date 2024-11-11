package com.team4.goorm.community.auth.presentation;

import com.team4.goorm.community.auth.application.AuthService;
import com.team4.goorm.community.auth.domain.CustomUserDetails;
import com.team4.goorm.community.auth.dto.request.LoginReqDto;
import com.team4.goorm.community.auth.dto.request.SignupReqDto;
import com.team4.goorm.community.auth.dto.response.LoginRespDto;
import com.team4.goorm.community.auth.dto.response.MailVerificationRespDto;
import com.team4.goorm.community.global.common.dto.SuccessResponse;
import com.team4.goorm.community.mail.application.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;
	private final MailService mailService;

	@Operation(summary = "일반 회원가입")
	@PostMapping("/signup")
	public ResponseEntity<SuccessResponse<String>> signup(@RequestBody SignupReqDto req) {
		authService.signup(req);
		return ResponseEntity.ok(SuccessResponse.success("회원가입 성공"));
	}

	@Operation(summary = "일반 로그인")
	@PostMapping("/login")
	public ResponseEntity<SuccessResponse<LoginRespDto>> login(@RequestBody LoginReqDto req) {
		LoginRespDto resp = authService.login(req);
		return ResponseEntity.ok(SuccessResponse.success(resp));
	}

	@Operation(summary = "이메일 인증코드 전송")
	@PostMapping("/emails/verification")
	public ResponseEntity<SuccessResponse<String>> sendVerificationEmail(
			@Parameter(description = "이메일")
			@RequestParam String email) {
		authService.sendVerificationEmail(email);
		return ResponseEntity.ok(SuccessResponse.success("인증 이메일 전송에 성공하였습니다."));
	}

	@Operation(summary = "이메일 인증코드 검증")
	@GetMapping("/emails/verification")
	public ResponseEntity<SuccessResponse<MailVerificationRespDto>> verificationEmail(
			@Parameter(description = "이메일")
			@RequestParam String email,
			@Parameter(description = "인증코드")
			@RequestParam String authCode) {

		MailVerificationRespDto response = authService.verifyCode(email, authCode);
		return ResponseEntity.ok(SuccessResponse.success(response));
	}

	@Operation(summary = "이메일 임시 비밀번호 전송(비밀번호 찾기)")
	@PostMapping("/password/temp")
	public ResponseEntity<SuccessResponse<String>> sendTempPasswordEmail(
			@AuthenticationPrincipal CustomUserDetails user) {

		authService.sendTempPassword(user.getEmail());
		return ResponseEntity.ok(SuccessResponse.success("임시 비밀번호 발급에 성공하였습니다."));
	}
}
