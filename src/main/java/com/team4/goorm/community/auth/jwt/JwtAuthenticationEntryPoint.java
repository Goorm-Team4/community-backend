package com.team4.goorm.community.auth.jwt;

import static com.team4.goorm.community.auth.exception.JwtErrorCode.*;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.team4.goorm.community.global.common.dto.ErrorResponse;
import com.team4.goorm.community.global.common.utils.JsonResponseHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 인증(Authentication) 예외가 발생했을 때 예외 처리 -> 인증되지 않은 사용자가 접근하려고 하는 경우
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		log.warn("Authentication Exception: {}", authException.getMessage());

		ErrorResponse<Object> errorResponse = ErrorResponse.failure(UNAUTHORIZED_ACCESS.getCode(),
			UNAUTHORIZED_ACCESS.getMessage());

		JsonResponseHandler.sendErrorResponse(response, HttpStatus.UNAUTHORIZED, errorResponse);
	}
}
