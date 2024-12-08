package com.team4.goorm.community.Post.dto;

import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Member.dto.response.ProfileSummaryDto;
import com.team4.goorm.community.Post.domain.Category;
import com.team4.goorm.community.Post.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSummaryDto {

    @Schema(description = "게시글 ID", example = "1")
    private Long postId;
    @Schema(description = "제목", example = "제목")
    private String title;
    @Schema(description = "카테고리", example = "카테고리1")
    private Category category;
    @Schema(description = "게시글 썸네일 URL", example = "http://goorm.com/image.jpg")
    private String imageUrl;
    @Schema(description = "작성일", example = "2024-08-01T12:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "좋아요 수", example = "10")
    private Long likeCount = 0L;
    @Schema(description = "댓글 수", example = "5")
    private Long  commentCount = 0L;
    @Schema(description = "작성자 정보")
    private ProfileSummaryDto author;

    @Builder
    public PostSummaryDto(Long postId, String title, Category category, String imageUrl, LocalDateTime createdAt, Long likeCount, Long commentCount, ProfileSummaryDto author) {
        this.postId = postId;
        this.title = title;
        this.category = category;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.author = author;
    }

    public static PostSummaryDto from(Post post) {
        Member member = post.getMember();
        return PostSummaryDto.builder()
                .postId(post.getPostId())
                .author(ProfileSummaryDto.from(member))
                .title(post.getTitle())
                .category(post.getCategory())
                .imageUrl(post.getThumbnailImageUrl())
                .createdAt(post.getCreatedAt())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .build();
    }
}
