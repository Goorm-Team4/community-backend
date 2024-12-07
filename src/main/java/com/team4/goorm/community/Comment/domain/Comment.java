package com.team4.goorm.community.Comment.domain;

import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Post.domain.Post;
import com.team4.goorm.community.global.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String content;

    @Column(name = "like_count")
    private Long likeCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    public Comment(Long commentId, String content, Long likeCount, Member member, Post post) {
        this.commentId = commentId;
        this.content = content;
        this.likeCount = likeCount;
        this.member = member;
        this.post = post;
    }

    public void update(String content) {
        this.content = content;
    }
}
