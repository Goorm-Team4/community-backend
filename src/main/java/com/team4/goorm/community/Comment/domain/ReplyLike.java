package com.team4.goorm.community.Comment.domain;

import java.lang.reflect.Member;

import com.team4.goorm.community.global.common.domain.BaseEntity;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ReplyLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyLikeId;

    @ManyToOne
    @JoinColumn(name = "reply_id", nullable = false)
    private Reply reply;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private  Member member;
}
