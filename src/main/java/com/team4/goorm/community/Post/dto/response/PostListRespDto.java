package com.team4.goorm.community.Post.dto.response;

import com.team4.goorm.community.Post.domain.Post;
import com.team4.goorm.community.Post.dto.PostSummaryDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "게시글 전체 조회 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostListRespDto {

    @Schema(description = "총 페이지 수", example = "10")
    private int totalPage;
    @Schema(description = "게시글 리스트")
    private List<PostSummaryDto> posts;

    public PostListRespDto(int totalPage, List<PostSummaryDto> posts) {
        this.totalPage = totalPage;
        this.posts = posts;
    }

    public static PostListRespDto from(Page<Post> posts) {
        return new PostListRespDto(posts.getTotalPages(),
                posts.getContent().stream()
                        .map(PostSummaryDto::from)
                        .toList());
    }
}
