package com.team4.goorm.community.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team4.goorm.community.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
