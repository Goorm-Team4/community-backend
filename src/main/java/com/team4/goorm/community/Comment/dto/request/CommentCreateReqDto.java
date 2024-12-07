package com.team4.goorm.community.Comment.dto.request;

import com.team4.goorm.community.Comment.domain.Comment;
import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Post.domain.Post;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentCreateReqDto {
    @Schema(description = "댓글 내용", example = "댓글 내용")
    private String content;


    public Comment toEntity(Member member, Post post) {
        return Comment.builder()
                .content(content)
                .member(member)
                .post(post)
                .build();
    }


}
