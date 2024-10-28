package com.team4.goorm.community.global.common.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse<T> {

	private String code;
	private String message;

	public static <T> ErrorResponse<T> failure(String code, String message) {
		return ErrorResponse.<T>builder()
			.code(code)
			.message(message)
			.build();
	}
}
