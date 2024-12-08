package com.team4.goorm.community.Comment.application;

import com.team4.goorm.community.Comment.domain.Comment;
import com.team4.goorm.community.Comment.domain.CommentLike;
import com.team4.goorm.community.Comment.repository.CommentLikeRepository;
import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Post.domain.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CommentLikeQueryService {

    private final CommentLikeRepository commentLikeRepository;

    public CommentLike findByCommentAndMember(Comment comment, Member member) {
        return commentLikeRepository.findByCommentAndMember(comment, member).orElse(null);
    }
}
