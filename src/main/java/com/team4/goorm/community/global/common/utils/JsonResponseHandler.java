package com.team4.goorm.community.global.common.utils;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.goorm.community.global.common.dto.SuccessResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JsonResponseHandler {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private static void setDefaultResponse(HttpServletResponse response, HttpStatus httpStatus) {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(httpStatus.value());
		response.setCharacterEncoding("UTF-8");
	}

	public static void sendSuccessResponse(HttpServletResponse response, HttpStatus httpStatus, Object body) throws
		IOException {
		setDefaultResponse(response, httpStatus);
		String responseBody = objectMapper.writeValueAsString(SuccessResponse.success(body));
		response.getWriter().write(responseBody);
	}

	public static void sendErrorResponse(HttpServletResponse response, HttpStatus httpStatus, Object body)
		throws IOException {
		setDefaultResponse(response, httpStatus);
		String responseBody = objectMapper.writeValueAsString(body);
		response.getWriter().write(responseBody);
	}
}
