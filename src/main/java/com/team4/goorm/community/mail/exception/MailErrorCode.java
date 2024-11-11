package com.team4.goorm.community.mail.exception;

import com.team4.goorm.community.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MailErrorCode implements BaseErrorCode {

	MAIL_ERROR(HttpStatus.BAD_REQUEST, "MAIL_500_0", "메일 전송에 실패하였습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;
}
