package com.team4.goorm.community.Post.dto.request;

import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Post.domain.Category;
import com.team4.goorm.community.Post.domain.Post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostCreateReqDto {
    @Schema(description = "제목", example = "제목")
    private String title;
    @Schema(description = "내용", example = "내용")
    private String content;
    @Schema(description = "카테고리", example = "카테고리1")
    private Category category;
    @Schema(description = "게시글 썸네일 URL", example = "http://goorm.com/image.jpg")
    private String thumbnailImageUrl;
    @Schema(description = "작성자 ID", example = "1")
    private Long memberId;

    public Post toEntity(Member member, String thumbnailImageUrl) {
        return Post.builder()
                .title(title)
                .content(content)
                .category(category)
                .thumbnailImageUrl(thumbnailImageUrl)
                .member(member)
                .build();
    }

    public Post toEntity(Member member) {
        return Post.builder()
                .title(title)
                .content(content)
                .category(category)
                .thumbnailImageUrl(thumbnailImageUrl)
                .member(member)
                .build();
    }
}
