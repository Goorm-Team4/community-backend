package com.team4.goorm.community.Post.dto.response;

import com.team4.goorm.community.Post.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "게시글 리스트 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostListRespDto {

    private int totalPage;
    private List<PostInfoRespDto> posts;

    public PostListRespDto(int totalPage, List<PostInfoRespDto> posts) {
        this.totalPage = totalPage;
        this.posts = posts;
    }

    public static PostListRespDto from(Page<Post> posts) {
        return new PostListRespDto(posts.getTotalPages(),
                posts.getContent().stream()
                        .map(PostInfoRespDto::from)
                        .toList());
    }
}
