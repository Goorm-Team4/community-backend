package com.team4.goorm.community.global.common.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface PageReqDto {

	int getPage();
	int getSize();
	String getSortBy();
	String getDirection();

	default Pageable toPageable() {
		Sort.Direction direction = Sort.Direction.fromString(getDirection());
		Sort sort = Sort.by(direction, getSortBy());
		return PageRequest.of(getPage() - 1, getSize(), sort);
	}
}
