package com.team4.goorm.community.Post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team4.goorm.community.Post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
