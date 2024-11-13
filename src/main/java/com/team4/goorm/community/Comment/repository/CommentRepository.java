package com.team4.goorm.community.Comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team4.goorm.community.Comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
