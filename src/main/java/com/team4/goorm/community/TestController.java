//package com.team4.goorm.community;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.team4.goorm.community.member.exception.MemberErrorCode;
//import com.team4.goorm.community.member.exception.MemberException;
//import com.team4.goorm.community.global.common.dto.SuccessResponse;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.extern.slf4j.Slf4j;
//
//@Tag(name = "Test Controller", description = "테스트 API")
//@Slf4j
//@RestController
//public class TestController {
//
//	@Operation(summary = "커스텀 응답 테스트")
//	@GetMapping("/test/success")
//	public ResponseEntity<SuccessResponse<String>> test(
//		@Parameter(description = "메시지", example = "테스트입니다.")
//		@RequestParam("msg") String msg) {
//
//		return ResponseEntity.ok(SuccessResponse.success(msg));
//	}
//
//	@Operation(summary = "커스텀 예외 테스트")
//	@GetMapping("/test/failure")
//	public ResponseEntity<SuccessResponse<String>> test2(){
//
//		throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
//	}
//}
