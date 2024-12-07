package com.team4.goorm.community.Comment.application;

import com.team4.goorm.community.Comment.domain.Comment;
import com.team4.goorm.community.Comment.repository.CommentRepository;
import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CommentQueryService {

    private final CommentRepository commentRepository;

    public List<Comment> findAllByMemberOrderByCreatedAtDesc(Member member) {
        return commentRepository.findAllByMemberOrderByCreatedAtDesc(member);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
    }

    public List<Comment> findAllByPost(Post post) {
        return commentRepository.findAllByPost(post);
    }
}
