package com.team4.goorm.community.Post.dto.request;

import com.team4.goorm.community.global.common.dto.PageDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostPageDto implements PageDto {

    private int page;
    private final int size = 3 * 7; // 한 페이지당 게시글 수
    private String sortBy;
    private String direction;

    public PostPageDto(int page, String sortBy, String direction) {
        this.page = page;
        this.sortBy = sortBy;
        this.direction = direction;
    }
}
