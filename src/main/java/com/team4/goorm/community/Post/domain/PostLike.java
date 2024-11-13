package com.team4.goorm.community.Post.domain;

import java.lang.reflect.Member;

import com.team4.goorm.community.global.common.domain.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PostLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postLikeId;   
    
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    
}
