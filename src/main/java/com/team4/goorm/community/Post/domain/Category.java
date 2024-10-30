package com.team4.goorm.community.Post.domain;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.team4.goorm.community.Post.exception.PostErrorCode;
import com.team4.goorm.community.Post.exception.PostException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category {

	C1("카테고리1"),
	C2("카테고리2"),
	C3("카테고리3");

	private final String name;
	private static final Map<String, Category> NAME_TO_ENUM_MAP = new HashMap<>();

	static {
		for (Category category : Category.values()) {
			NAME_TO_ENUM_MAP.put(category.name, category);
		}
	}

	@JsonValue // 직렬화 시 enum -> name 반환
	public String getName() {
		return name;
	}

	@JsonCreator // 역직렬화 시 name -> enum 반환
	public static Category fromName(String name) {
		Category category = NAME_TO_ENUM_MAP.get(name);

		if (category == null) {
			throw new PostException(PostErrorCode.CATEGORY_NOT_FOUND);
		}
		return category;
	}
}
