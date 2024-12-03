package com.team4.goorm.community.Comment.application;

import com.team4.goorm.community.Comment.domain.Comment;
import com.team4.goorm.community.Comment.repository.CommentRepository;
import com.team4.goorm.community.Member.domain.Member;
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
}