package com.team4.goorm.community.auth.presentation;

import com.team4.goorm.community.auth.application.AuthService;
import com.team4.goorm.community.auth.domain.CustomUserDetails;
import com.team4.goorm.community.auth.dto.request.LoginReqDto;
import com.team4.goorm.community.auth.dto.request.SignupReqDto;
import com.team4.goorm.community.auth.dto.response.DuplicateCheckRespDto;
import com.team4.goorm.community.auth.dto.response.LoginRespDto;
import com.team4.goorm.community.auth.dto.response.MailVerificationRespDto;
import com.team4.goorm.community.global.common.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Auth", description = "인증 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	@Operation(summary = "닉네임 중복 검사")
	@GetMapping("/username/validation")
	public ResponseEntity<SuccessResponse<DuplicateCheckRespDto>> validateUniqueUsername(
			@Parameter(description = "닉네임", example = "구름")
			@RequestParam String username) {
		return ResponseEntity.ok(SuccessResponse.success(
				authService.checkUsernameDuplicate(username)));
	}

	@Operation(summary = "일반 회원가입", description = "Contnet-type info는 application/json으로 요청해주세요. (이미지는 url말고 파일 보내주셔야합니다.)")
	@PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<SuccessResponse<String>> signup(
			@RequestPart("info") SignupReqDto request,
			@RequestPart(value = "image", required = false) MultipartFile profileImage) {
		authService.signup(request, profileImage);
		return ResponseEntity.ok(SuccessResponse.success("회원가입 성공"));
	}

	@Operation(summary = "일반 로그인")
	@PostMapping("/login")
	public ResponseEntity<SuccessResponse<LoginRespDto>> login(@RequestBody LoginReqDto request) {
		LoginRespDto resp = authService.login(request);
		return ResponseEntity.ok(SuccessResponse.success(resp));
	}

	@Operation(summary = "이메일 인증코드 전송")
	@PostMapping("/email/verification")
	public ResponseEntity<SuccessResponse<String>> sendVerificationEmail(
			@Parameter(description = "이메일")
			@RequestParam String email) {
		authService.sendVerificationEmail(email);
		return ResponseEntity.ok(SuccessResponse.success("인증 이메일 전송에 성공하였습니다."));
	}

	@Operation(summary = "이메일 인증코드 검증")
	@GetMapping("/email/verification")
	public ResponseEntity<SuccessResponse<MailVerificationRespDto>> verifyAuthCode(
			@Parameter(description = "이메일")
			@RequestParam String email,
			@Parameter(description = "인증코드")
			@RequestParam String authCode) {

		MailVerificationRespDto response = authService.verifyAuthCode(email, authCode);
		return ResponseEntity.ok(SuccessResponse.success(response));
	}

	@Operation(summary = "이메일 임시 비밀번호 전송")
	@PostMapping("/password/temp")
	public ResponseEntity<SuccessResponse<String>> sendTempPasswordEmail(
			@AuthenticationPrincipal CustomUserDetails user) {

		authService.sendTempPassword(user.getEmail());
		return ResponseEntity.ok(SuccessResponse.success("임시 비밀번호 발급에 성공하였습니다."));
	}

	@Operation(summary = "비밀번호 변경")
	@PatchMapping("/password")
	public ResponseEntity<SuccessResponse<String>> changePassword(
			@RequestBody ChangePasswordReqDto request,
			@AuthenticationPrincipal CustomUserDetails user) {

		authService.changePassword(request, user.getEmail());
		return ResponseEntity.ok(SuccessResponse.success("비밀번호 변경에 성공하였습니다."));
	}
}
