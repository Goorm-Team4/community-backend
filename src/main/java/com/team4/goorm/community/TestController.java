package com.team4.goorm.community;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {

	@GetMapping("/test")
	public String test() {
		return "test";
	}
}
