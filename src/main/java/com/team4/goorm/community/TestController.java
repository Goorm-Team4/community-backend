package com.team4.goorm.community;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Test Controller", description = "테스트 API")
@Slf4j
@RestController
public class TestController {

	@Operation(summary = "테스트")
	@GetMapping("/test")
	public String test(
		@Parameter(description = "메시지", example = "테스트입니다.")
		@RequestParam("msg") String msg) {
		return msg;
	}
}
