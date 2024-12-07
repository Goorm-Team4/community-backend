package com.team4.goorm.community.Comment.domain;

import com.team4.goorm.community.global.common.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private Long likeCount;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Builder
    public Reply(Long replyId, String content, Long likeCount, Comment comment) {
        this.replyId = replyId;
        this.content = content;
        this.likeCount = likeCount;
        this.comment = comment;
    }

    public void update(String content) {
        this.content = content;
    }
}
