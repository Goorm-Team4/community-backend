package com.team4.goorm.community.Comment.domain;

import com.team4.goorm.community.Post.domain.Post;
import com.team4.goorm.community.global.common.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long commentId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private Long likeCount;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
