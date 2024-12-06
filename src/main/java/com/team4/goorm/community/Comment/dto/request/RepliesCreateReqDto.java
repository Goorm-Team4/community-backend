package com.team4.goorm.community.Comment.dto.request;

import com.team4.goorm.community.Comment.domain.Comment;
import com.team4.goorm.community.Comment.domain.Reply;
import com.team4.goorm.community.Member.domain.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RepliesCreateReqDto {
    @Schema(description = "답글 내용", example = "답글 내용")
    private String content;

    
    public Reply toEntity(Comment comment) {
        return Reply.builder()
                .content(content)
                .comment(comment)
                .build();
    }
}
