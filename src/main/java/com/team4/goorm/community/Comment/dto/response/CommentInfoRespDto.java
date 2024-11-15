package com.team4.goorm.community.Comment.dto.response;

import com.team4.goorm.community.Comment.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "댓글 정보 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentInfoRespDto {

    @Schema(description = "댓글 ID", example = "1")
    private Long commentId;
    @Schema(description = "내용", example = "내용")
    private String content;
    @Schema(description = "작성일", example = "2024-08-01T12:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "좋아요 수", example = "10")
    private Long likeCount;

    @Builder
    public CommentInfoRespDto(Long commentId, String content, LocalDateTime createdAt, Long likeCount) {
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
    }

    public static CommentInfoRespDto from(Comment comment) {
        return CommentInfoRespDto.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .likeCount(comment.getLikeCount())
                .build();
    }
}
