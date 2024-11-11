package com.team4.goorm.community.global.exception;

import com.team4.goorm.community.auth.exception.AuthException;
import com.team4.goorm.community.global.common.dto.ErrorResponse;
import com.team4.goorm.community.image.exception.ImageException;
import com.team4.goorm.community.mail.exception.MailException;
import com.team4.goorm.community.member.exception.MemberException;
import com.team4.goorm.community.post.exception.PostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MemberException.class)
	public ResponseEntity<ErrorResponse<Void>> handleMemberException(MemberException e) {
		log.warn("Member Exception: {}", e.getMessage());

		return ResponseEntity.status(e.getStatus()).body(ErrorResponse.failure(e.getCode(), e.getMessage()));
	}

	@ExceptionHandler(AuthException.class)
	public ResponseEntity<ErrorResponse<Void>> handleAuthException(AuthException e) {
		log.warn("Auth Exception: {}", e.getMessage());

		return ResponseEntity.status(e.getStatus()).body(ErrorResponse.failure(e.getCode(), e.getMessage()));
	}


	@ExceptionHandler(MailException.class)
	public ResponseEntity<ErrorResponse<Void>> handleMailException(MailException e) {
		log.warn("Mail Exception: {}", e.getMessage());

		return ResponseEntity.status(e.getStatus()).body(ErrorResponse.failure(e.getCode(), e.getMessage()));
	}

	@ExceptionHandler(PostException.class)
	public ResponseEntity<ErrorResponse<Void>> handlePostException(PostException e) {
		log.warn("Post Exception: {}", e.getMessage());

		return ResponseEntity.status(e.getStatus()).body(ErrorResponse.failure(e.getCode(), e.getMessage()));
	}

	@ExceptionHandler(ImageException.class)
	public ResponseEntity<ErrorResponse<Void>> handleImageException(ImageException e) {
		log.warn("Image Exception: {}", e.getMessage());

		return ResponseEntity.status(e.getStatus()).body(ErrorResponse.failure(e.getCode(), e.getMessage()));
	}


	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse<Void>> handleException(Exception e) {
		log.warn(e.getMessage(), e);

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		return ResponseEntity.internalServerError()
			.body(ErrorResponse.failure(String.valueOf(status.value()), status.getReasonPhrase()));
	}
}
