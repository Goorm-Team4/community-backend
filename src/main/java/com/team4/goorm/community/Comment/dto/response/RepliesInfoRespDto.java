package com.team4.goorm.community.Comment.dto.response;

import com.team4.goorm.community.Comment.domain.Reply;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;

@Schema(description = "답글 정보 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RepliesInfoRespDto {
    @Schema(description = "답글 ID", example = "1")
    private Long replyId;
    @Schema(description = "내용", example = "내용")
    private String content;
    @Schema(description = "작성일", example = "2024-08-01T12:00:00")
    private String createdAt;
    @Schema(description = "좋아요 수", example = "10")
    private Long likeCount;
    
    @Builder
    public RepliesInfoRespDto(Long replyId, String content, String createdAt, Long likeCount) {
        this.replyId = replyId;
        this.content = content;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
    }

    public static RepliesInfoRespDto from(Reply reply) {
        return RepliesInfoRespDto.builder()
                .replyId(reply.getReplyId())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt().toString())
                .likeCount(reply.getLikeCount())
                .build();
    }

}
