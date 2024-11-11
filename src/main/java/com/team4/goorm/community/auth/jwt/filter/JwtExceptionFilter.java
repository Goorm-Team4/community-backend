package com.team4.goorm.community.auth.jwt.filter;

import static com.team4.goorm.community.auth.exception.JwtErrorCode.*;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.team4.goorm.community.auth.exception.JwtException;
import com.team4.goorm.community.global.common.dto.ErrorResponse;
import com.team4.goorm.community.global.common.utils.JsonResponseHandler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (JwtException e) {
			log.warn("CustomJwtException: {}", e.getMessage());

			ErrorResponse<Object> errorResponse = ErrorResponse.failure(
				e.getCode(),
				e.getMessage()
			);

			JsonResponseHandler.sendErrorResponse(
				response,
				e.getStatus(),
				errorResponse
			);
		} catch (Exception e) {
			log.error("JwtException: {}", e.getMessage());

			ErrorResponse<Object> errorResponse = ErrorResponse.failure(
				JWT_ERROR.getCode(),
				JWT_ERROR.getMessage()
			);

			JsonResponseHandler.sendErrorResponse(
				response,
				HttpStatus.INTERNAL_SERVER_ERROR,
				errorResponse
			);
		}
	}
}
