package com.team4.goorm.community.Post.domain;

import com.team4.goorm.community.global.common.domain.BaseEntity;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PostImage extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postImageId;

    @ManyToOne
    @JoinColumn(name="post_id", nullable = false)
    private Post post;

    @Column(name="post_image_url", length = 512)
    private String postImageUrl;
}
