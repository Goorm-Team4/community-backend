package com.team4.goorm.community.Comment.repository;

import com.team4.goorm.community.Comment.domain.Comment;
import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Post.domain.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByMemberOrderByCreatedAtDesc(Member member);
    @EntityGraph(attributePaths = {"member"})
    List<Comment> findAllByPost(Post post);
}
