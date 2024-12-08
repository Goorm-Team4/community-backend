package com.team4.goorm.community.Comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team4.goorm.community.Comment.domain.Comment;
import com.team4.goorm.community.Comment.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByCommentOrderByCreatedAtDesc(Comment comment);

}
