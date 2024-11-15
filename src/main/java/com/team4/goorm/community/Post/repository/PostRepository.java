package com.team4.goorm.community.Post.repository;

import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
     List<Post> findAllByMemberOrderByCreatedAtDesc(Member member);
}
