package com.team4.goorm.community.auth.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team4.goorm.community.auth.application.AuthService;
import com.team4.goorm.community.auth.dto.request.LoginReqDto;
import com.team4.goorm.community.auth.dto.request.SignupReqDto;
import com.team4.goorm.community.auth.dto.response.LoginRespDto;
import com.team4.goorm.community.global.common.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Auth", description = "인증 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

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
}
