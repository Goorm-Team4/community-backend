package com.team4.goorm.community.Post.dto.request;

import com.team4.goorm.community.global.common.dto.PageReqDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostPageReqDto implements PageReqDto {

    private int page;
    private final int size = 3 * 7;
    private String sortBy;
    private String direction;

    public PostPageReqDto(int page, String sortBy, String direction) {
        this.page = page;
        this.sortBy = sortBy;
        this.direction = direction;
    }
}
