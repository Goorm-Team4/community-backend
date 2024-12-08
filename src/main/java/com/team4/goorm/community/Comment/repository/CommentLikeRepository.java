package com.team4.goorm.community.Comment.repository;

import com.team4.goorm.community.Comment.domain.Comment;
import com.team4.goorm.community.Comment.domain.CommentLike;
import com.team4.goorm.community.Member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentAndMember(Comment comment, Member member);
}
