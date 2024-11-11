package com.team4.goorm.community.auth.jwt;

import static com.team4.goorm.community.auth.exception.JwtErrorCode.*;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.team4.goorm.community.global.common.dto.ErrorResponse;
import com.team4.goorm.community.global.common.utils.JsonResponseHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 인가(Authorization) 예외가 발생했을 때 예외 처리 -> 인증된 사용자가 필요한 권한없이 접근하려고 하는 경우
 */
@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.warn("Authorization Exception: {}", accessDeniedException.getMessage());

		ErrorResponse<Object> errorResponse = ErrorResponse.failure(ACCESS_DENIED.getCode(),
			ACCESS_DENIED.getMessage());

		JsonResponseHandler.sendErrorResponse(response, HttpStatus.FORBIDDEN, errorResponse);
	}
}
