package com.team4.goorm.community.Post.dto;

import com.team4.goorm.community.Comment.domain.Comment;
import com.team4.goorm.community.Member.dto.response.ProfileSummaryDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostCommentInfoDto {
    @Schema(description = "댓글 ID", example = "1")
    private Long commentId;
    @Schema(description = "댓글 내용", example = "댓글 내용")
    private String content;
    @Schema(description = "댓글 작성일", example = "2024-08-01T12:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "댓글 좋아요 수", example = "10")
    private Long likeCount;
    @Schema(description = "댓글 작성자가 현재 로그인한 사용자인지 여부", example = "false")
    private boolean isAuthor;
    @Schema(description = "댓글 작성자 정보")
    private ProfileSummaryDto author;

    public boolean getIsAuthor() {
        return isAuthor;
    }

    @Builder
    public PostCommentInfoDto(Long commentId, String content, LocalDateTime createdAt, Long likeCount, boolean isAuthor, ProfileSummaryDto author) {
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.isAuthor = isAuthor;
        this.author = author;
    }

    public static PostCommentInfoDto from(Comment comment, Long memberId) {
        boolean isAuthor = Objects.equals(memberId, comment.getMember().getMemberId());
        return PostCommentInfoDto.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .likeCount(comment.getLikeCount())
                .isAuthor(isAuthor)
                .author(ProfileSummaryDto.from(comment.getMember()))
                .build();
    }
}
